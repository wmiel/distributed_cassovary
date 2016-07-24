package calculation

sealed trait AbstractInput

case object EmptyInput extends AbstractInput

case class SingleVertexInput(vertex: Long) extends AbstractInput {
  override def toString = vertex.toString
}

case class MultipleVertexInput(vertex: Seq[Long]) extends AbstractInput {
  override def toString = vertex.toString
}