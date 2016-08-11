package calculation

import com.twitter.cassovary.graph._

import algorithms.BreadthFirstTraverser

import scala.collection.mutable.ArrayBuffer

sealed trait AbstractCalculation {
  def calculate(graph: DirectedGraph[Node], input: AbstractInput): AbstractResult
}

case object ExampleCalculation extends AbstractCalculation {
  override def calculate(graph: DirectedGraph[Node], input: AbstractInput): AbstractResult = input match {
    case SingleVertexInput(u) => new LongResult(graph.nodeCount)
    case MultipleVertexInput(u) => {
      for (i <- 0 to u.size) {
        for (j <- 0 to u.size) {
          for (k <- 0 to u.size) {
            1 + 1
          }
        }
      }
      new LongResult(u.size)
    }
  }
}

case class RandomPartitionsCalculation(partitionSize: Int) extends AbstractCalculation {
  override def calculate(graph: DirectedGraph[Node], input: AbstractInput): AbstractResult = {
    val nodes = new ArrayBuffer[Long]
    graph.foreach(node =>
      nodes += (node.id)
    )
    Partitions(nodes.grouped(partitionSize.toInt).toArray)
  }
}

case object BMatrixCalculation extends AbstractCalculation {
  def calculateDistances(graph: DirectedGraph[Node], sourceNodeId: Int) = {
    val bfs = new BreadthFirstTraverser(graph, sourceNodeId)
    bfs.foreach(_ => {}) // traverse graph to get depths
  }

  override def calculate(graph: DirectedGraph[Node], input: AbstractInput): AbstractResult = input match {
    case SingleVertexInput(u) => new LongResult(graph.nodeCount)
    case MultipleVertexInput(u) => {
      for (i <- 0 to u.size) {
        for (j <- 0 to u.size) {
          for (k <- 0 to u.size) {
            1 + 1
          }
        }
      }
      new LongResult(u.size)
    }
  }
}

