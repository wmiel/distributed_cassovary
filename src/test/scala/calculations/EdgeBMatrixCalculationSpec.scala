package scala.calculations

import calculations.{EdgeBMatrix, EdgeBMatrixCalculation, VertexInput}
import com.twitter.cassovary.graph.{DirectedGraph, Node}
import org.scalatest.{FunSpec, Matchers}
import util.TestGraph

class EdgeBMatrixCalculationSpec extends FunSpec with Matchers {
  def subject(graph: DirectedGraph[Node], vertex: Seq[Int]) = {
    EdgeBMatrixCalculation.calculate(graph, VertexInput(vertex))
  }

  describe("EdgeBMatrixCalculation") {
    describe("when fed with line graph") {
      val graph = TestGraph.lineGraph

      it("calculates partial Edge BMatrix for given vertices") {
        assert(
          subject(graph, List(1)) ==
            EdgeBMatrix(Map((1, 1) -> 1, (3, 1) -> 1, (5, 1) -> 1, (7, 1) -> 1, (9, 1) -> 1))
        )

        assert(
          subject(graph, List(5)) ==
            EdgeBMatrix(Map((1, 1) -> 1))
        )

        assert(
          subject(graph, List(1, 2, 3, 4, 5, 6)) ==
            EdgeBMatrix(
              Map((1, 1) -> 5, (3, 1) -> 4, (5, 1) -> 3, (7, 1) -> 2, (9, 1) -> 1)
            )
        )
      }
    }

    describe("when fed with star graph") {
      val graph = TestGraph.starGraph

      it("calculates partial Edge BMatrix for given vertices") {
        assert(subject(graph, List(0)) == EdgeBMatrix(Map((1, 5) -> 1, (3, 5) -> 1)))
        assert(subject(graph, List(1)) == EdgeBMatrix(Map((1, 1) -> 1)))
        assert(subject(graph, List(11)) == EdgeBMatrix(Map()))
        assert(subject(graph, List(0, 11, 1)) == EdgeBMatrix(Map((1, 5) -> 1, (3, 5) -> 1, (1, 1) -> 1)))
      }
    }

    describe("when fed with cycle graph") {
      val graph = TestGraph.cycleGraph

      it("calculates partial Edge BMatrix for given vertices") {
        assert(subject(graph, List(1)) == EdgeBMatrix(Map((1, 2) -> 1, (2, 1) -> 1, (3, 1) -> 1)))
        assert(subject(graph, List(2)) == EdgeBMatrix(Map((1, 3) -> 1, (2, 1) -> 1)))
        assert(subject(graph, List(3)) == EdgeBMatrix(Map((1, 1) -> 1, (3, 2) -> 1, (2, 1) -> 1)))
        assert(subject(graph, List(1, 2, 3)) == EdgeBMatrix(Map((1, 2) -> 1, (2, 1) -> 3, (3, 1) -> 1, (1, 3) -> 1, (1, 1) -> 1, (3, 2) -> 1)))
      }
    }

    describe("when fed with duplicated edges graph") {
      val graph = TestGraph.duplicatedEdgesGraph

      it("calculates partial Edge BMatrix for given vertices") {
        assert(subject(graph, List(1)) == EdgeBMatrix(Map((0, 1) -> 1, (1, 5) -> 1)))
        assert(subject(graph, List(2)) == EdgeBMatrix(Map((1, 5) -> 1, (2, 1) -> 1)))
      }
    }

    describe("when fed with disconnected graph") {
      val graph = TestGraph.disconnectedGraph

      it("calculates partial Edge BMatrix for given vertices") {
        assert(subject(graph, List(1, 2, 5, 99)) == EdgeBMatrix(Map((1, 1) -> 2)))
      }
    }
  }
}
