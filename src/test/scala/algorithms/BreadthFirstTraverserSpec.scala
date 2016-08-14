package algorithms

import com.twitter.cassovary.graph.{DirectedGraph, Node}
import com.twitter.logging.Level.ERROR
import com.twitter.logging.Logger
import org.scalatest.{FunSpec, Matchers}
import util.Env.TEST
import util.{CassovaryLogger, TestGraph}

class BreadthFirstTraverserSpec extends FunSpec with Matchers {
  CassovaryLogger.setUp(TEST)

  def bfs(graph: DirectedGraph[Node], sourceId: Int) = new BreadthFirstTraverser(graph, sourceId)

  describe("BreadthFirstTraverser") {
    describe("when fed with line graph") {
      val graph = TestGraph.lineGraph

      it("traverses in BFS order") {
        assert(bfs(graph, 1).toList == List((1, 0), (2, 1), (3, 2), (4, 3), (5, 4), (6, 5)))
      }
    }

    describe("when fed with star graph") {
      val graph = TestGraph.starGraph

      it("traverses in BFS order") {
        assert(bfs(graph, 0).toList == List((0, 0), (1, 1), (2, 1), (3, 1), (4, 1), (5, 1),
          (11, 2), (22, 2), (33, 2), (44, 2), (55, 2)))
      }
    }

    describe("when fed with cycle graph") {
      val graph = TestGraph.cycleGraph

      it("traverses in BFS order") {
        assert(bfs(graph, 1).toList == List((1, 0), (2, 1), (3, 2)))
      }
    }

    describe("when fed with duplicated edges graph") {
      val graph = TestGraph.duplicatedEdgesGraph

      it("traverses in BFS order") {
        assert(bfs(graph, 1).toList == List((1, 0), (2, 1)))
      }
    }

    describe("when fed with disconnected graph") {
      val graph = TestGraph.disconnectedGraph

      it("traverses in BFS order") {
        assert(bfs(graph, 1).toList == List((1, 0), (2, 1)))
        assert(bfs(graph, 2).toList == List((2, 0)))
        assert(bfs(graph, 5).toList == List((5, 0), (99, 1)))
        assert(bfs(graph, 99).toList == List((99, 0)))
      }
    }
  }
}
