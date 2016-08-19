package framework.worker

import akka.actor._
import com.twitter.cassovary.graph.{DirectedGraph, Node}
import framework._
import graphTransformations.UndirectedDedupTransformation
import util.{GraphLoader, GzipGraphDownloader}

import scala.collection.mutable.ListBuffer
import scala.concurrent.duration._
import scala.language.postfixOps


class Worker(val masterPath: String) extends Actor with GzipGraphDownloader with GraphLoader {
  var graphUrl = ""
  var graph: DirectedGraph[Node] = _
  var connected = false
  var cacheDirectoryPath = "cache"
  val calculationActors = ListBuffer[ActorRef]()
  var masterRef: ActorRef = ActorRef.noSender
  var separatorInt = '	'.toInt

  context.actorSelection(masterPath) ! Identify(masterPath)

  context.system.scheduler.schedule(30 seconds, 2 minutes)(logMemoryUsage)(context.system.dispatcher)

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
        ).
        //withDispatcher("akka.actor.my-thread-pool-dispatcher")
          withDispatcher("akka.actor.my-dispatcher")

      )
      calculationActors += calculationActor
    }
  }

  def logMemoryUsage = {
    val mb = 1024 * 1024
    val runtime = Runtime.getRuntime()

    val memoryInfo = " Used Memory:%d MB, Free Memory:%d MB, Total Memory:%d MB, Max Memory:%d MB".format(
      (runtime.totalMemory() - runtime.freeMemory()) / mb,
      runtime.freeMemory() / mb,
      runtime.totalMemory() / mb,
      runtime.maxMemory() / mb
    )

    println(memoryInfo)
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
    val graphImpl = workerSetup.getOrElse("graph_type", "shared")
    graph = if(graphImpl == "shared") {
      readGraphAsSharedArrayBasedGraph(directory, filename, adjacencyList, autoSeparator = autoSeparator)
    } else {
      readGraphAsArrayBasedGraph(directory, filename, adjacencyList, autoSeparator = autoSeparator)
    }

    if (workerSetup.getOrElse("transform_to_undirected", "false").toBoolean) {
      graph = UndirectedDedupTransformation(graph)
    }

    val endTime = System.nanoTime()
    jobRef ! GraphLoadedInfo(endTime - startTime, graph.nodeCount, graph.edgeCount)

    stopExecutors
    val numberOfExecutors = workerSetup.getOrElse("calculation_executors_per_worker", "1").toInt
    spawnExecutors(numberOfExecutors, jobRef)
  }
}