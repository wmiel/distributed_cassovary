package algorithms

import com.twitter.cassovary.graph.{GraphDir, Node, DirectedGraph}

import scala.collection.mutable

class BreadthFirstTraverser(val graph: DirectedGraph[Node], val sourceNodeId: Int) extends Iterator[(Int, Int)] {
  val nodesToVisitWithDistance = new scala.collection.mutable.Queue[(Int, Int)]()
  nodesToVisitWithDistance.enqueue((sourceNodeId, 0))

  val visited = new mutable.BitSet(graph.maxNodeId + 1)

  override def hasNext: Boolean = {
    nodesToVisitWithDistance.nonEmpty
  }

  override def next(): (Int, Int) = {
    val (nodeId, distance) = nodesToVisitWithDistance.dequeue()
    val node = graph.getNodeById(nodeId).get

    val neighbors = node.inboundNodes ++ node.outboundNodes
    neighbors.foreach(id => {
      if (!visited.contains(id)) {
        nodesToVisitWithDistance.enqueue((id, distance + 1))
        visited.add(id)
      }
    })
    (nodeId, distance)
  }
}
