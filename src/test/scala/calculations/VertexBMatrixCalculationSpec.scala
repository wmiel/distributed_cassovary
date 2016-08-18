package scala.calculations

import calculations.{VertexBMatrix, VertexBMatrixCalculation, VertexInput}
import com.twitter.cassovary.graph.{DirectedGraph, Node}
import org.scalatest.{FunSpec, Matchers}
import util.TestGraph

class VertexBMatrixCalculationSpec extends FunSpec with Matchers {
  def subject(graph: DirectedGraph[Node], vertex: Seq[Int]) = {
    VertexBMatrixCalculation.calculate(graph, VertexInput(vertex))
  }

  describe("VertexBMatrixAggregation") {
    describe("when fed with line graph") {
      val graph = TestGraph.lineGraph

      it("calculates partial Vertex BMatrix for given vertices") {
        assert(
          subject(graph, List(1)) ==
            VertexBMatrix(Map((0, 1) -> 1, (1, 1) -> 1, (2, 1) -> 1, (3, 1) -> 1, (4, 1) -> 1, (5, 1) -> 1))
        )

        assert(
          subject(graph, List(5)) ==
            VertexBMatrix(Map((0, 1) -> 1, (1, 1) -> 1))
        )

        assert(
          subject(graph, List(1, 2, 3, 4, 5, 6)) ==
            VertexBMatrix(
              Map((0, 1) -> 6, (1, 1) -> 5, (2, 1) -> 4, (3, 1) -> 3, (4, 1) -> 2, (5, 1) -> 1)
            )
        )
      }
    }

    describe("when fed with star graph") {
      val graph = TestGraph.starGraph

      it("calculates partial Vertex BMatrix for given vertices") {
        assert(subject(graph, List(0)) == VertexBMatrix(
          Map((0, 1) -> 1, (1, 5) -> 1, (2, 5) -> 1))
        )
        assert(subject(graph, List(1)) == VertexBMatrix(
          Map((0, 1) -> 1, (1, 1) -> 1))
        )
        assert(subject(graph, List(11)) == VertexBMatrix(
          Map((0, 1) -> 1))
        )
        assert(subject(graph, List(0, 11, 1)) == VertexBMatrix(
          Map((0, 1) -> 3, (1, 1) -> 1, (1, 5) -> 1, (2, 5) -> 1))
        )
      }
    }

    describe("when fed with cycle graph") {
      val graph = TestGraph.cycleGraph

      it("calculates partial Vertex BMatrix for given vertices") {
        assert(subject(graph, List(1)) == VertexBMatrix(Map((0, 1) -> 1, (1, 1) -> 1, (2, 1) -> 1)))
        assert(subject(graph, List(2)) == VertexBMatrix(Map((0, 1) -> 1, (1, 2) -> 1)))
        assert(subject(graph, List(3)) == VertexBMatrix(Map((0, 1) -> 1, (1, 1) -> 1, (2, 1) -> 1)))
      }
    }

    describe("when fed with duplicated edges graph") {
      val graph = TestGraph.duplicatedEdgesGraph

      it("calculates partial Vertex BMatrix for given vertices") {
        assert(subject(graph, List(1)) == VertexBMatrix(Map((0, 1) -> 1, (1, 1) -> 1)))
        assert(subject(graph, List(2)) == VertexBMatrix(Map((0, 1) -> 1, (1, 1) -> 1)))
      }
    }

    describe("when fed with disconnected graph") {
      val graph = TestGraph.disconnectedGraph

      it("calculates partial Vertex BMatrix for given vertices") {
        assert(subject(graph, List(1, 2, 5, 99)) == VertexBMatrix(Map((0, 1) -> 4, (1, 1) -> 2)))
      }
    }
  }
}
