package calculations

import algorithms.BreadthFirstTraverser
import com.twitter.cassovary.graph._

import scala.collection.mutable.ArrayBuffer

trait DistanceBasedCalculation {
  def bfs(graph: DirectedGraph[Node], input: AbstractInput): Seq[Iterator[(Int, Int)]] = input match {
    case VertexInput(vertices) =>
      vertices.map(vertex => new BreadthFirstTraverser(graph, vertex))
  }
}

sealed trait AbstractCalculation {
  def calculate(graph: DirectedGraph[Node], input: AbstractInput): Result
}

case class RandomPartitionsCalculation(partitionSize: Int) extends AbstractCalculation {
  //extends Partitioning {
  override def calculate(graph: DirectedGraph[Node], input: AbstractInput): Result = {
    val nodes = new ArrayBuffer[Int]
    graph.foreach(node =>
      nodes += node.id
    )
    Partitions(nodes.grouped(partitionSize).toArray)
  }
}


case object BFSDistances extends AbstractCalculation with DistanceBasedCalculation {
  override def calculate(graph: DirectedGraph[Node], input: AbstractInput): Result = {
    Distances(bfs(graph, input).map { t => t.toSeq })
  }
}

case object VertexBMatrixCalculation extends AbstractCalculation with DistanceBasedCalculation {
  override def calculate(graph: DirectedGraph[Node], input: AbstractInput): VertexBMatrix = {
    val distancesPerVertex = bfs(graph, input)
    val kNeighborhoodSizesPerVertex: Seq[Map[Int, Int]] = distancesPerVertex.map { traverser =>
      traverser
        .toSeq
        .groupBy(_._2) // group by distance
        .map({ case (k, vertexList) => (k, vertexList.size) }) // count frequencies
    }
    val result: Map[(Int, Int), Int] = kNeighborhoodSizesPerVertex
      .flatten
      .groupBy(x => x)
      .map({ case (kAndNumberOfKNeighbors, aggregatedList) => kAndNumberOfKNeighbors -> aggregatedList.size }) // count frequencies

    VertexBMatrix(result)
  }
}