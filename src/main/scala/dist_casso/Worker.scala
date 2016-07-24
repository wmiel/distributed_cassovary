package dist_casso

import java.io.File

import akka.actor._
import calculation.{AbstractInput, AbstractCalculation}
import com.twitter.cassovary.graph.{DirectedGraph, Node, TestGraphs}
import util.{GzipGraphDownloader, GraphLoader}

class Worker extends Actor with GzipGraphDownloader with GraphLoader {
  var remoteGraphName = ""
  var graph: DirectedGraph[Node] = null

  def cacheDirectory = {
    "cache/"
  }

  def receive = {
    case SetupWorker(workerSetup) => {
      setup(workerSetup)
      sender ! Info(remoteGraphName)
      sender ! WorkerReady
    }

    case Calc(calculation: AbstractCalculation, input: AbstractInput) => {
      sender ! Result(calculation.calculate(graph, input))
      sender ! WorkerReady
    }
  }

  def setup(workerSetup: Map[String, String]) = {
    remoteGraphName = workerSetup.getOrElse("graph_name", "http://snap.stanford.edu/data/cit-HepTh.txt.gz")
    val adjacencyList = workerSetup.getOrElse("adjacency_list", "false").toBoolean

    val (directory:String, filename:String) = cacheRemoteFile(remoteGraphName)
    graph = readGraph(directory, filename, adjacencyList)
  }
}