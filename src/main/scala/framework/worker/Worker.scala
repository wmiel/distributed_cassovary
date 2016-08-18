package framework.worker

import akka.actor._
import com.twitter.cassovary.graph.{DirectedGraph, Node}
import framework.{Connected, Exit, Info, SetupWorker}
import graphTransformations.UndirectedDedupTransformation
import util.{GraphLoader, GzipGraphDownloader}

import scala.collection.mutable.ListBuffer


class Worker(val masterPath: String) extends Actor with GzipGraphDownloader with GraphLoader {
  var graphUrl = ""
  var graph: DirectedGraph[Node] = _
  var connected = false
  var cacheDirectoryPath = "cache"
  val calculationActors = ListBuffer[ActorRef]()
  var masterRef: ActorRef = ActorRef.noSender
  var separatorInt = '	'.toInt

  context.actorSelection(masterPath) ! Identify(masterPath)

  def cacheDirectory = {
    cacheDirectoryPath
  }

  def receive = {
    case ActorIdentity(path, Some(actor)) =>
      if (path == masterPath) {
        connected = true
        masterRef = actor
        actor ! Connected
      }

    case ActorIdentity(path, None) =>
      println(s"Remote actor not available: $path")
      context.stop(self)

    case SetupWorker(workerSetup) =>
      setup(workerSetup, sender)

    case Exit =>
      context.system.terminate()
  }

  def stopExecutors = {
    calculationActors.foreach { actor: ActorRef =>
      actor ! PoisonPill
    }
    calculationActors.clear()
  }

  def spawnExecutors(numOfActors: Int, jobRef: ActorRef) = {
    for (i <- 0 until numOfActors) {
      val calculationActor = context.system.actorOf(
        Props(
          new CalculationExecutor(
            jobRef,
            graph
          )
        )
      )
      calculationActors += calculationActor
    }
  }

  def setup(workerSetup: Map[String, String], jobRef: ActorRef) = {


    val separatorSetting = workerSetup.getOrElse("file_separator", " ")
    var autoSeparator = false
    if (separatorSetting == "auto") {
      autoSeparator = true
    } else {
      separatorInt = workerSetup.getOrElse("file_separator", " ").charAt(0).toInt
    }


    if (workerSetup.getOrElse("random_cache_dir", "false").toBoolean) {
      cacheDirectoryPath = generateCacheDirectoryName()
    }
    else {
      cacheDirectoryPath = workerSetup.getOrElse("cache_dir", "cache")
    }

    graphUrl = workerSetup.getOrElse("graph_url", "http://snap.stanford.edu/data/cit-HepTh.txt.gz")
    val adjacencyList = workerSetup.getOrElse("adjacency_list", "false").toBoolean

    val (directory: String, filename: String) = cacheRemoteFile(graphUrl)

    val startTime = System.nanoTime()
    graph = readGraphAsSharedArrayBasedGraph(directory, filename, adjacencyList, autoSeparator = autoSeparator)

    if (workerSetup.getOrElse("transform_to_undirected", "false").toBoolean) {
      graph = UndirectedDedupTransformation(graph)
    }

    val endTime = System.nanoTime()
    jobRef ! Info("GraphLoading(%s): %d [ns] Nodes: %d Edges: %s"
      .format(
        graphUrl,
        endTime - startTime,
        graph.nodeCount,
        graph.edgeCount
      )
    )

    stopExecutors
    val numberOfExecutors = workerSetup.getOrElse("calculation_executors_per_worker", "1").toInt
    spawnExecutors(numberOfExecutors, jobRef)
  }
}