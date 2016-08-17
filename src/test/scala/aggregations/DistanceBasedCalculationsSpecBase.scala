package aggregations

import calculations.VertexInput
import com.twitter.cassovary.graph.{DirectedGraph, Node}
import util.CassovaryLogger
import util.Env.TEST

trait DistanceBasedCalculationsSpecBase {
  CassovaryLogger.setUp(TEST)

  def testedAggregation: Seq[DistanceAggregation]

  def subject(graph: DirectedGraph[Node], sourceIds: List[Int]) = {
    val calculation = DistanceBasedCalculation(testedAggregation)
    calculation.calculate(graph, VertexInput(sourceIds)).result.head
  }
}
