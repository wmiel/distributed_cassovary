package graphTransformations

import com.twitter.cassovary.graph.StoredGraphDir.StoredGraphDir
import com.twitter.cassovary.graph._
import com.twitter.cassovary.util.NodeNumberer
import com.twitter.cassovary.util.io.GraphReader

// workaround for https://github.com/twitter/cassovary/pull/220/files
object MaxIdBugWorkaround {

  class FixedGraphReader(originalReader: GraphReader[Int]) extends GraphReader[Int] {
    override def iterableSeq: Seq[Iterable[NodeIdEdgesMaxId]] = originalReader.iterableSeq.map { iterable =>
      iterable.map { nodeIdEdgeMax =>
        NodeIdEdgesMaxId(nodeIdEdgeMax.id, nodeIdEdgeMax.edges, nodeIdEdgeMax.id.max(nodeIdEdgeMax.maxId))
      }
    }

    override def storedGraphDir: StoredGraphDir = originalReader.storedGraphDir

    override def nodeNumberer: NodeNumberer[Int] = originalReader.nodeNumberer

    override def reverseParseNode(n: NodeIdEdgesMaxId): String = originalReader.reverseParseNode(n)
  }

  def apply(graphReader: GraphReader[Int]): GraphReader[Int] = {
    new FixedGraphReader(graphReader)
  }
}
