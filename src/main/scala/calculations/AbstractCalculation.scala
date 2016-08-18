package calculations

import algorithms.BreadthFirstTraverser
import com.twitter.cassovary.graph._

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

trait DistanceBasedCalculation {
  def bfs(graph: DirectedGraph[Node], input: AbstractInput): Seq[Iterator[(Int, Int)]] = input match {
    case VertexInput(vertices) =>
      vertices.map(vertex => new BreadthFirstTraverser(graph, vertex))
  }

  def aggregate(perVertexMap: Seq[scala.collection.Map[Int, Int]]) = {
    perVertexMap
      .flatten
      .groupBy(x => x)
      .map({ case (kAndNumberOfKNeighbors, aggregatedList) => kAndNumberOfKNeighbors -> aggregatedList.size })
  }
}

sealed trait AbstractCalculation {
  def calculate(graph: DirectedGraph[Node], input: AbstractInput): Result
}

case class RandomPartitionsCalculation(partitionSize: Int) extends AbstractCalculation {
  override def calculate(graph: DirectedGraph[Node], input: AbstractInput): Result = {
    val nodes = new ArrayBuffer[Int]
    graph.foreach(node =>
      nodes += node.id
    )
    Partitions(nodes.grouped(partitionSize).toArray)
  }
}

case object OutgoingDegreePerVertexCalculation extends AbstractCalculation {
  override def calculate(graph: DirectedGraph[Node], input: AbstractInput): Result = input match {
    case VertexInput(vertices) =>
      val degrees = vertices.map(vertex => (vertex, graph.getNodeById(vertex).get.outboundCount))
      OutgoingDegreePerVertex(degrees)
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
    val distanceFrequenciesPerVertex: Seq[Map[Int, Int]] = distancesPerVertex.map { traverser =>
      traverser
        .toSeq
        .groupBy(_._2) // group by distance
        .map({ case (k, vertexList) => (k, vertexList.size) }) // count frequencies
    }
    val result: Map[(Int, Int), Int] = aggregate(distanceFrequenciesPerVertex)
    VertexBMatrix(result)
  }
}

// Alternative approach:
//case object EdgeBMatrixAggregationDisconnected extends DistanceAggregation {
//  override def aggregate(graph: DirectedGraph[Node], distances: Seq[Iterator[(Int, Int)]]) = {
//    val results = mutable.Map[Int, Int]().withDefaultValue(0)
//
//    distances.foreach { perNodeDistances: Iterator[(Int, Int)] =>
//      val distancesMap = perNodeDistances.toMap
//      distancesMap.foreach { case (nodeId, nodeDistance) => {
//        val node = graph.getNodeById(nodeId).get
//        node.outboundNodes().foreach { neighborId: Int =>
//          val neighborDistance = distancesMap.getOrElse(neighborId, -1)
//          if (neighborDistance >= 0) {
//            val distance = nodeDistance + neighborDistance
//            results.update(distance, results(distance) + 1)
//          }
//        }
//      }
//      }
//    }
//
//    results.toMap
//  }
//}

case object EdgeBMatrixCalculation extends AbstractCalculation with DistanceBasedCalculation {
  override def calculate(graph: DirectedGraph[Node], input: AbstractInput): EdgeBMatrix = {
    val distanceMaps: Seq[Map[Int, Int]] =
      bfs(graph, input).map { distancesForVertex: Iterator[(Int, Int)] => distancesForVertex.toMap }

    val distanceFrequenciesPerVertex: Seq[mutable.HashMap[Int, Int]] =
      distanceMaps.map { _ => mutable.HashMap[Int, Int]() }

    graph.foreach { node: Node =>
      val nodeId = node.id
      node.outboundNodes().foreach { neighborId: Int =>
        distanceMaps.zip(distanceFrequenciesPerVertex).foreach { case (distances, results) =>
          val nodeDistance = distances.getOrElse(nodeId, -1)
          val neighborDistance = distances.getOrElse(neighborId, -1)

          if (nodeDistance >= 0 && neighborDistance >= 0) {
            val distance = nodeDistance + neighborDistance
            results.update(distance, results.getOrElse(distance, 0) + 1)
          }
        }
      }
    }

    val result: Map[(Int, Int), Int] = aggregate(distanceFrequenciesPerVertex)
    EdgeBMatrix(result)
  }
}


