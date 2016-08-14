package calculation

import aggregations.DistanceAggregation
import com.twitter.cassovary.graph._
import algorithms.{BreadthFirstTraverser, Histogram}

import scala.collection.mutable.ArrayBuffer

sealed trait AbstractCalculation {
  def calculate(graph: DirectedGraph[Node], input: AbstractInput): AbstractResult
}

case object ExampleCalculation extends AbstractCalculation {
  override def calculate(graph: DirectedGraph[Node], input: AbstractInput): AbstractResult = input match {
    case VertexInput(u) => {
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
      nodes += node.id
    )
    Partitions(nodes.grouped(partitionSize).toArray)
  }
}

case class DistanceBasedCalculation(aggregations: Seq[DistanceAggregation]) extends AbstractCalculation {
  def bfs(graph: DirectedGraph[Node], input: AbstractInput): Seq[Iterator[(Int, Int)]] = input match {
    case VertexInput(vertices) =>
      vertices.map(vertex => new BreadthFirstTraverser(graph, vertex))
  }

  override def calculate(graph: DirectedGraph[Node], input: AbstractInput) = {
    val distances = bfs(graph, input)
    CompoundResult(aggregations.map(_.aggregate(graph, distances)))
  }
}
