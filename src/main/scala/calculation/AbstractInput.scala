package calculation

sealed trait AbstractInput

case object EmptyInput extends AbstractInput

case class VertexInput(vertex: Seq[Int]) extends AbstractInput {
  override def toString = vertex.toString
}