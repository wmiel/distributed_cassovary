package aggregations

import algorithms.Histogram
import calculations.{AbstractResult, MapResult}
import com.twitter.cassovary.graph.{DirectedGraph, Node}

import scala.collection.mutable

sealed trait DistanceAggregation {
  def aggregate(graph: DirectedGraph[Node], distances: Seq[Iterator[(Int, Int)]]): AbstractResult
}

case object VertexBMatrixAggregation extends DistanceAggregation {
  override def aggregate(graph: DirectedGraph[Node], distances: Seq[Iterator[(Int, Int)]]): MapResult = {
    val allDistances = distances.foldLeft(Iterator[(Int, Int)]()) {
      (iterator, perVertexDistances) => iterator ++ perVertexDistances
    }

    MapResult(Histogram[(Int, Int)](_._2)(allDistances.toSeq))
  }
}

case object EdgeBMatrixAggregationConnected extends DistanceAggregation {
  override def aggregate(graph: DirectedGraph[Node], distances: Seq[Iterator[(Int, Int)]]): MapResult = {
    val distanceMaps = distances.map { perNodeDistances: Iterator[(Int, Int)] => perNodeDistances.toMap }
    val results = mutable.Map[Int, Int]().withDefaultValue(0)

    graph.foreach { node: Node =>
      val nodeId = node.id
      node.outboundNodes().foreach { neighborId: Int =>
        distanceMaps.foreach { distances =>
          val nodeDistance = distances.getOrElse(nodeId, -1)
          val neighborDistance = distances.getOrElse(neighborId, -1)

          if (nodeDistance >= 0 && neighborDistance >= 0) {
            val distance = nodeDistance + neighborDistance
            results.update(distance, results(distance) + 1)
          }
        }
      }
    }

    MapResult(results.toMap)
  }
}

case object EdgeBMatrixAggregationDisconnected extends DistanceAggregation {
  override def aggregate(graph: DirectedGraph[Node], distances: Seq[Iterator[(Int, Int)]]): MapResult = {
    val results = mutable.Map[Int, Int]().withDefaultValue(0)

    distances.foreach { perNodeDistances: Iterator[(Int, Int)] =>
      val distancesMap = perNodeDistances.toMap
      distancesMap.foreach { case (nodeId, nodeDistance) => {
        val node = graph.getNodeById(nodeId).get
        node.outboundNodes().foreach { neighborId: Int =>
          val neighborDistance = distancesMap.getOrElse(neighborId, -1)
          if(neighborDistance >= 0) {
            val distance = nodeDistance + neighborDistance
            results.update(distance, results(distance) + 1)
          }
        }
      }}
    }

    MapResult(results.toMap)
  }
}
