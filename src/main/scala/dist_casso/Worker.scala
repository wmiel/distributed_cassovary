package dist_casso

import java.io.File

import akka.actor._
import calculation.{AbstractCalculation, AbstractInput}
import com.twitter.cassovary.graph.{DirectedGraph, Node}
import util.{GraphLoader, GzipGraphDownloader}


class Worker(val masterPath: String) extends Actor with GzipGraphDownloader with GraphLoader {
  var graphUrl = ""
  var graph: DirectedGraph[Node] = null
  var connected = false
  var cacheDirectoryPath = "cache"

  context.actorSelection(masterPath) ! Identify(masterPath)

  def cacheDirectory = {
    cacheDirectoryPath
  }

  def generateCacheDirectory(): String = {
    val r = scala.util.Random
    val s = r.nextString(50)
    val m = java.security.MessageDigest.getInstance("MD5")
    val b = s.getBytes("UTF-8")
    m.update(b, 0, b.length)
    val md5 = new java.math.BigInteger(1, m.digest()).toString(16)
    new File("cache", md5).getPath
  }

  def receive = {
    case ActorIdentity(path, Some(actor)) => {
      connected = true
      actor ! Connected
    }

    case ActorIdentity(path, None) => {
      println(s"Remote actor not available: $path")
      context.stop(self)
    }

    case SetupWorker(workerSetup) => {
      setup(workerSetup)
      sender ! Info(graphUrl)
      sender ! WorkerReady
    }

    case Calc(calculation: AbstractCalculation, input: AbstractInput) => {
      sender ! Info("Starting calculation")
      sender ! Result(calculation.calculate(graph, input))
      sender ! Info("Finished calculation")
      sender ! WorkerReady
    }
  }

  def setup(workerSetup: Map[String, String]) = {
    if (workerSetup.getOrElse("random_cache_dir", "false").toBoolean) {
      cacheDirectoryPath = generateCacheDirectory()
    }
    else {
      cacheDirectoryPath = workerSetup.getOrElse("cache_dir", "cache")
    }

    graphUrl = workerSetup.getOrElse("graph_url", "http://snap.stanford.edu/data/cit-HepTh.txt.gz")
    val adjacencyList = workerSetup.getOrElse("adjacency_list", "false").toBoolean

    val (directory: String, filename: String) = cacheRemoteFile(graphUrl)
    graph = readGraphAsArrayBasedGraph(directory, filename, adjacencyList)
  }
}