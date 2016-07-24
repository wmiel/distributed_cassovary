package calculation

import com.twitter.cassovary.graph.{Node, DirectedGraph}

import scala.collection.mutable.ArrayBuffer

sealed trait AbstractCalculation {
  def calculate(graph: DirectedGraph[Node], input: AbstractInput): AbstractResult
}

case object ExampleCalculation extends AbstractCalculation {
  override def calculate(graph: DirectedGraph[Node], input: AbstractInput): AbstractResult = input match {
    case SingleVertexInput(u) => new LongResult(graph.nodeCount)
    case MultipleVertexInput(u) => new LongResult(u.size)
  }
}

case class RandomPartitionsCalculation(partitionSize: Int) extends AbstractCalculation {
  override def calculate(graph: DirectedGraph[Node], input: AbstractInput): AbstractResult = {
    val nodes = new ArrayBuffer[Long]
    graph.foreach(node =>
      nodes += (node.id)
    )
    Partitions(nodes.grouped(partitionSize.toInt).toArray)
  }
}

