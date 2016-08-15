package aggregations

import calculations._
import org.scalatest.{FunSpec, Matchers}
import util.TestGraph

class EdgeBMatrixAggregationConnectedSpec extends FunSpec with Matchers with DistanceBasedCalculationsSpecBase {
  override def testedAggregation: Seq[DistanceAggregation] = List(EdgeBMatrixAggregationConnected)

  describe(testedAggregation.head.getClass.getSimpleName) {
    describe("when fed with line graph") {
      val graph = TestGraph.lineGraph

      it("calculates partial Edge BMatrix for given vertices") {
        assert(
          subject(graph, List(1)) ==
            MapResult(Map(1 -> 1, 3 -> 1, 5 -> 1, 7 -> 1, 9 -> 1))
        )

        assert(
          subject(graph, List(5)) ==
            MapResult(Map(1 -> 1))
        )

        assert(
          subject(graph, List(1, 2, 3, 4, 5, 6)) ==
            MapResult(
              Map(1 -> 5, 3 -> 4, 5 -> 3, 7 -> 2, 9 -> 1)
            )
        )
      }
    }

    describe("when fed with star graph") {
      val graph = TestGraph.starGraph

      it("calculates partial Edge BMatrix for given vertices") {
        assert(subject(graph, List(0)) == MapResult(Map(1 -> 5, 3 -> 5)))
        assert(subject(graph, List(1)) == MapResult(Map(1 -> 1)))
        assert(subject(graph, List(11)) == MapResult(Map()))
        assert(subject(graph, List(0, 11, 1)) == MapResult(Map(1 -> 6, 3 -> 5)))
      }
    }

    describe("when fed with cycle graph") {
      val graph = TestGraph.cycleGraph

      it("calculates partial Edge BMatrix for given vertices") {
        assert(subject(graph, List(1)) == MapResult(Map(1 -> 2, 2 -> 1, 3 -> 1)))
        assert(subject(graph, List(2)) == MapResult(Map(1 -> 3, 2 -> 1)))
        assert(subject(graph, List(3)) == MapResult(Map(1 -> 1, 3 -> 2, 2 -> 1)))
      }
    }

    describe("when fed with duplicated edges graph") {
      val graph = TestGraph.duplicatedEdgesGraph

      it("calculates partial Edge BMatrix for given vertices") {
        assert(subject(graph, List(1)) == MapResult(Map(0 -> 1, 1 -> 5)))
        assert(subject(graph, List(2)) == MapResult(Map(1 -> 5, 2 -> 1)))
      }
    }

    describe("when fed with disconnected graph") {
      val graph = TestGraph.disconnectedGraph

      it("calculates partial Edge BMatrix for given vertices") {
        assert(subject(graph, List(1, 2, 5, 99)) == MapResult(Map(1 -> 2)))
      }
    }
  }
}
