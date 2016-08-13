package calculation

import algorithms.BreadthFirstTraverser
import com.twitter.cassovary.graph.{Node, DirectedGraph}
import org.scalatest.{Matchers, FunSpec}
import util.TestGraph

class BMatrixCalculationSpec extends FunSpec with Matchers {
  def calculate(graph: DirectedGraph[Node], sourceId: Int) = {
    BMatrixCalculation.calculate(graph, SingleVertexInput(sourceId))
  }

  def calculate(graph: DirectedGraph[Node], sourceIds: Seq[Int]) = {
    BMatrixCalculation.calculate(graph, MultipleVertexInput(sourceIds))
  }

  describe("BMatrixCalculation") {
    describe("when fed with single vertex input") {
      describe("when fed with line graph") {
        val graph = TestGraph.lineGraph

        it("calculates the histogram for specified vertex") {
          assert(calculate(graph, 1) == MapResult(Map(0 -> 1, 1 -> 1, 2 -> 1, 3 -> 1, 4 -> 1, 5 -> 1)))
          assert(calculate(graph, 5) == MapResult(Map(0 -> 1, 1 -> 1)))
        }
      }

      describe("when fed with star graph") {
        val graph = TestGraph.starGraph

        it("calculates the histogram for specified vertex") {
          assert(calculate(graph, 0) == MapResult(Map(0 -> 1, 1 -> 5, 2 -> 5)))
          assert(calculate(graph, 1) == MapResult(Map(0 -> 1, 1 -> 1)))
          assert(calculate(graph, 11) == MapResult(Map(0 -> 1)))
        }
      }

      describe("when fed with cycle graph") {
        val graph = TestGraph.cycleGraph

        it("calculates the histogram for specified vertex") {
          assert(calculate(graph, 1) == MapResult(Map(0 -> 1, 1 -> 1, 2 -> 1)))
          assert(calculate(graph, 2) == MapResult(Map(0 -> 1, 1 -> 2)))
          assert(calculate(graph, 3) == MapResult(Map(0 -> 1, 1 -> 1, 2 -> 1)))
        }
      }

      describe("when fed with duplicated edges graph") {
        val graph = TestGraph.duplicatedEdgesGraph

        it("calculates the histogram for specified vertex") {
          assert(calculate(graph, 1) == MapResult(Map(0 -> 1, 1 -> 1)))
          assert(calculate(graph, 2) == MapResult(Map(0 -> 1, 1 -> 1)))
        }
      }

      describe("when fed with disconnected graph") {
        val graph = TestGraph.disconnectedGraph

        it("calculates the histogram for specified vertex") {
          assert(calculate(graph, 1) == MapResult(Map(0 -> 1, 1 -> 1)))
          assert(calculate(graph, 2) == MapResult(Map(0 -> 1)))
        }
      }
    }

    describe("when fed with multiple vertex input") {
      describe("when fed with line graph") {
        val graph = TestGraph.lineGraph

        it("calculates the histogram for specified vertex") {
          assert(calculate(graph, List(1, 5)) == MapResult(Map(0 -> 2, 1 -> 2, 2 -> 1, 3 -> 1, 4 -> 1, 5 -> 1)))
        }
      }

      describe("when fed with star graph") {
        val graph = TestGraph.starGraph

        it("calculates the histogram for specified vertex") {
          assert(calculate(graph, List(0, 1, 11)) == MapResult(Map(0 -> 3, 1 -> 6, 2 -> 5)))
        }
      }

      describe("when fed with cycle graph") {
        val graph = TestGraph.cycleGraph

        it("calculates the histogram for specified vertex") {
          assert(calculate(graph, List(1, 2, 3)) == MapResult(Map(0 -> 3, 1 -> 4, 2 -> 2)))
        }
      }

      describe("when fed with duplicated edges graph") {
        val graph = TestGraph.duplicatedEdgesGraph

        it("calculates the histogram for specified vertex") {
          assert(calculate(graph, List(1, 2)) == MapResult(Map(0 -> 2, 1 -> 2)))
        }
      }

      describe("when fed with disconnected graph") {
        val graph = TestGraph.disconnectedGraph

        it("calculates the histogram for specified vertex") {
          assert(calculate(graph, List(1, 2, 5, 99)) == MapResult(Map(0 -> 4, 1 -> 2)))
        }
      }
    }
  }
}
