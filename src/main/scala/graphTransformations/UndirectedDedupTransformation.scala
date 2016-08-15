package graphTransformations

import com.twitter.cassovary.graph._

object UndirectedDedupTransformation {
  def apply(inputGraph: DirectedGraph[Node]): DirectedGraph[Node] = {
    assert(inputGraph.storedGraphDir == StoredGraphDir.BothInOut)

    val nodesWithEdges = inputGraph.map { node: Node =>
      val outboundNodes = node.inboundNodes ++ node.outboundNodes
      NodeIdEdgesMaxId(node.id, outboundNodes.sorted.distinct.toArray)
    }
    ArrayBasedDirectedGraph(
      nodesWithEdges,
      StoredGraphDir.Mutual,
      NeighborsSortingStrategy.AlreadySorted
    )
  }
}
