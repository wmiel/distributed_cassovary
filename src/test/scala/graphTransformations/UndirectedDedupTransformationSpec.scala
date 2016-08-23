package graphTransformations

import com.twitter.cassovary.graph.{DirectedGraph, Node}
import org.scalatest.{FunSpec, Matchers}
import util.Env.TEST
import util.{CassovaryLogger, TestGraph}

class UndirectedDedupTransformationSpec extends FunSpec with Matchers {
  CassovaryLogger.setUp(TEST)

  def subject(graph: DirectedGraph[Node]) = UndirectedDedupTransformation(graph)

  def inboundNodes(graph: DirectedGraph[Node], nodeId: Int) = {
    graph.getNodeById(nodeId).get.inboundNodes()
  }

  def outboundNodes(graph: DirectedGraph[Node], nodeId: Int) = {
    graph.getNodeById(nodeId).get.outboundNodes()
  }

  describe("UndirectedDedupTransformation") {
    describe("when fed with line graph") {
      val graph = subject(TestGraph.lineGraph)

      it("transforms graph into undirected") {
        assert(inboundNodes(graph, 1) == List(2))
        assert(outboundNodes(graph, 1) == List(2))

        assert(inboundNodes(graph, 2) == List(1, 3))
        assert(outboundNodes(graph, 2) == List(1, 3))

        assert(inboundNodes(graph, 3) == List(2, 4))
        assert(outboundNodes(graph, 3) == List(2, 4))

        assert(inboundNodes(graph, 4) == List(3, 5))
        assert(outboundNodes(graph, 4) == List(3, 5))

        assert(inboundNodes(graph, 5) == List(4, 6))
        assert(outboundNodes(graph, 5) == List(4, 6))

        assert(inboundNodes(graph, 6) == List(5))
        assert(outboundNodes(graph, 6) == List(5))

        assert(graph.edgeCount == 2 * TestGraph.lineGraph.edgeCount)
      }
    }

    describe("when fed with star graph") {
      val graph = subject(TestGraph.starGraph)

      it("transforms graph into undirected") {
        assert(inboundNodes(graph, 0) == List(1, 2, 3, 4, 5))
        assert(outboundNodes(graph, 0) == List(1, 2, 3, 4, 5))

        assert(inboundNodes(graph, 1) == List(0, 11))
        assert(outboundNodes(graph, 1) == List(0, 11))

        assert(inboundNodes(graph, 2) == List(0, 22))
        assert(outboundNodes(graph, 2) == List(0, 22))

        assert(inboundNodes(graph, 3) == List(0, 33))
        assert(outboundNodes(graph, 3) == List(0, 33))

        assert(inboundNodes(graph, 4) == List(0, 44))
        assert(outboundNodes(graph, 4) == List(0, 44))

        assert(inboundNodes(graph, 5) == List(0, 55))
        assert(outboundNodes(graph, 5) == List(0, 55))

        assert(inboundNodes(graph, 11) == List(1))
        assert(outboundNodes(graph, 11) == List(1))

        assert(inboundNodes(graph, 22) == List(2))
        assert(outboundNodes(graph, 22) == List(2))

        assert(inboundNodes(graph, 33) == List(3))
        assert(outboundNodes(graph, 33) == List(3))

        assert(inboundNodes(graph, 44) == List(4))
        assert(outboundNodes(graph, 44) == List(4))

        assert(inboundNodes(graph, 55) == List(5))
        assert(outboundNodes(graph, 55) == List(5))

        assert(graph.edgeCount == 2 * TestGraph.starGraph.edgeCount)
      }
    }

    describe("when fed with cycle graph") {
      val graph = subject(TestGraph.cycleGraph)

      it("transforms graph into undirected") {
        assert(inboundNodes(graph, 1) == List(2, 3))
        assert(outboundNodes(graph, 1) == List(2, 3))

        assert(inboundNodes(graph, 2) == List(1, 3))
        assert(outboundNodes(graph, 2) == List(1, 3))

        assert(inboundNodes(graph, 3) == List(1, 2))
        assert(outboundNodes(graph, 3) == List(1, 2))
      }
    }

    describe("when fed with duplicated edges graph") {
      val graph = subject(TestGraph.duplicatedEdgesGraph)

      it("transforms graph into undirected") {
        assert(inboundNodes(graph, 1) == List(1, 2))
        assert(outboundNodes(graph, 1) == List(1, 2))

        assert(inboundNodes(graph, 2) == List(1))
        assert(outboundNodes(graph, 2) == List(1))
      }
    }

    describe("when fed with disconnected graph") {
      val graph = subject(TestGraph.disconnectedGraph)

      it("transforms graph into undirected") {
        assert(inboundNodes(graph, 1) == List(2))
        assert(outboundNodes(graph, 1) == List(2))

        assert(inboundNodes(graph, 2) == List(1))
        assert(outboundNodes(graph, 2) == List(1))

        assert(inboundNodes(graph, 5) == List(99))
        assert(outboundNodes(graph, 5) == List(99))

        assert(inboundNodes(graph, 99) == List(5))
        assert(outboundNodes(graph, 99) == List(5))
      }
    }
  }
}
