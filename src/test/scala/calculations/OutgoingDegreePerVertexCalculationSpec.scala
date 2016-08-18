package scala.calculations

import calculations.{OutgoingDegreePerVertex, OutgoingDegreePerVertexCalculation, VertexInput}
import com.twitter.cassovary.graph.{DirectedGraph, Node}
import org.scalatest.{FunSpec, Matchers}
import util.TestGraph

class OutgoingDegreePerVertexCalculationSpec extends FunSpec with Matchers {
  def subject(graph: DirectedGraph[Node], vertex: Seq[Int]) = {
    OutgoingDegreePerVertexCalculation.calculate(graph, VertexInput(vertex))
  }

  describe("VertexBMatrixAggregation") {
    describe("when fed with line graph") {
      val graph = TestGraph.lineGraph

      it("calculates partial Vertex BMatrix for given vertices") {
        assert(
          subject(graph, List(1)) ==
            OutgoingDegreePerVertex(List((1, 1)))
        )

        assert(
          subject(graph, List(5)) ==
            OutgoingDegreePerVertex(List((5, 1)))
        )

        assert(
          subject(graph, List(1, 2, 3, 4, 5)) == OutgoingDegreePerVertex(
            List((1, 1), (2, 1), (3, 1), (4, 1), (5, 1))
          )
        )
      }
    }

    describe("when fed with star graph") {
      val graph = TestGraph.starGraph

      it("calculates partial Vertex BMatrix for given vertices") {
        assert(subject(graph, List(0)) == OutgoingDegreePerVertex(
          List((0, 5))
        ))
        assert(subject(graph, List(1)) == OutgoingDegreePerVertex(
          List((1, 1))
        ))
        assert(subject(graph, List(11)) == OutgoingDegreePerVertex(
          List((11, 0))
        ))
        assert(subject(graph, List(0, 11, 1)) == OutgoingDegreePerVertex(
          List((0, 5), (11, 0), (1, 1))
        ))
      }
    }

    describe("when fed with cycle graph") {
      val graph = TestGraph.cycleGraph

      it("calculates partial Vertex BMatrix for given vertices") {
        assert(subject(graph, List(1)) ==
          OutgoingDegreePerVertex(
            List((1, 1))
          ))
        assert(subject(graph, List(2)) ==
          OutgoingDegreePerVertex(
            List((2, 2))
          ))
        assert(subject(graph, List(3)) ==
          OutgoingDegreePerVertex(
            List((3, 1))
          ))
      }
    }

    describe("when fed with duplicated edges graph") {
      val graph = TestGraph.duplicatedEdgesGraph

      it("calculates partial Vertex BMatrix for given vertices") {
        assert(subject(graph, List(1)) == OutgoingDegreePerVertex(
          List((1, 4))
        ))
        assert(subject(graph, List(2)) == OutgoingDegreePerVertex(
          List((2, 2))
        ))
      }
    }

    describe("when fed with disconnected graph") {
      val graph = TestGraph.disconnectedGraph

      it("calculates partial Vertex BMatrix for given vertices") {
        assert(subject(graph, List(1, 2, 5, 99)) == OutgoingDegreePerVertex(
          List((1, 1), (2, 0), (5, 1), (99, 0)))
        )
      }
    }
  }
}
