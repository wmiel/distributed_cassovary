package aggregations

class EdgeBMatrixAggregationDisconnectedSpec extends EdgeBMatrixAggregationConnectedSpec {
  override def testedAggregation: Seq[DistanceAggregation] = List(EdgeBMatrixAggregationDisconnected)
}

