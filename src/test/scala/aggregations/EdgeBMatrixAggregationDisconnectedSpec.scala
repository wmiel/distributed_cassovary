package aggregations

import calculation._
import org.scalatest.{FunSpec, Matchers}
import util.TestGraph

class EdgeBMatrixAggregationDisconnectedSpec extends EdgeBMatrixAggregationConnectedSpec {
  override def testedAggregation: Seq[DistanceAggregation] = List(EdgeBMatrixAggregationDisconnected)
}

