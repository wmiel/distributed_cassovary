package util

import com.twitter.cassovary.graph._

object TestGraph {
  def lineGraph = ArrayBasedDirectedGraph(Seq(
    NodeIdEdgesMaxId(1, Array(2)),
    NodeIdEdgesMaxId(2, Array(3)),
    NodeIdEdgesMaxId(3, Array(4)),
    NodeIdEdgesMaxId(4, Array(5)),
    NodeIdEdgesMaxId(5, Array(6))
  ), StoredGraphDir.BothInOut, NeighborsSortingStrategy.LeaveUnsorted)

  def starGraph = ArrayBasedDirectedGraph(Seq(
    NodeIdEdgesMaxId(0, Array(1, 2, 3, 4, 5)),
    NodeIdEdgesMaxId(1, Array(11)),
    NodeIdEdgesMaxId(2, Array(22)),
    NodeIdEdgesMaxId(3, Array(33)),
    NodeIdEdgesMaxId(4, Array(44)),
    NodeIdEdgesMaxId(5, Array(55))
  ), StoredGraphDir.BothInOut, NeighborsSortingStrategy.LeaveUnsorted)

  def cycleGraph = SharedArrayBasedDirectedGraph(Seq(
    NodeIdEdgesMaxId(1, Array(2)),
    NodeIdEdgesMaxId(2, Array(1, 3)),
    NodeIdEdgesMaxId(3, Array(1))
  ), StoredGraphDir.BothInOut)

  def duplicatedEdgesGraph = SharedArrayBasedDirectedGraph(Seq(
    NodeIdEdgesMaxId(1, Array(1, 2, 2, 2)),
    NodeIdEdgesMaxId(2, Array(1, 1))
  ), StoredGraphDir.BothInOut)

  def disconnectedGraph = SharedArrayBasedDirectedGraph(Seq(
    NodeIdEdgesMaxId(1, Array(2)),
    NodeIdEdgesMaxId(5, Array(99))
  ), StoredGraphDir.BothInOut)
}
