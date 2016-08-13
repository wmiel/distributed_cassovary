package calculation

import com.twitter.cassovary.graph._

import algorithms.{Histogram, BreadthFirstTraverser}

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
    val nodes = new ArrayBuffer[Int]
    graph.foreach(node =>
      nodes += (node.id)
    )
    Partitions(nodes.grouped(partitionSize).toArray)
  }
}

case object BMatrixCalculation extends AbstractCalculation {
  def bfs(graph: DirectedGraph[Node], sourceNodeId: Int): Iterator[(Int, Int)] = new BreadthFirstTraverser(graph, sourceNodeId)
  def bfs(graph: DirectedGraph[Node], sourceNodeIds: Seq[Int]): Iterator[(Int, Int)] = {
    sourceNodeIds.foldLeft(Iterator[(Int, Int)]())((iterator, vertex) => iterator ++ bfs(graph, vertex))
  }

  override def calculate(graph: DirectedGraph[Node], input: AbstractInput): AbstractResult = {
    val distances: Iterator[(Int, Int)] = input match {
      case SingleVertexInput(vertex) => bfs(graph, vertex)
      case MultipleVertexInput(vertices) => bfs(graph, vertices)
    }
    val histogram = Histogram[(Int, Int)](_._2)(distances.toSeq)
    MapResult(histogram)
  }
}
