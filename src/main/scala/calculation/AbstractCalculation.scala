package calculation

import aggregations.DistanceAggregation
import algorithms.BreadthFirstTraverser
import com.twitter.cassovary.graph._

import scala.collection.mutable.ArrayBuffer

sealed trait AbstractCalculation {
  def calculate(graph: DirectedGraph[Node], input: AbstractInput): AbstractResult
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
