package graphTransformations

import com.twitter.cassovary.graph._

object UndirectedDedupTransformation {
  def apply(inputGraph: DirectedGraph[Node]): DirectedGraph[Node] = {
    assert(inputGraph.storedGraphDir == StoredGraphDir.BothInOut)

    val nodesWithEdges = inputGraph.map { node: Node =>
      val outboundNodes = node.inboundNodes ++ node.outboundNodes
      val nodes = outboundNodes.sorted.distinct.toArray
      NodeIdEdgesMaxId(node.id, nodes, node.id.max(nodes.last))
    }
    ArrayBasedDirectedGraph(
      nodesWithEdges,
      StoredGraphDir.Mutual,
      NeighborsSortingStrategy.AlreadySorted
    )
  }
}
