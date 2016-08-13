package calculation

sealed trait AbstractInput

case object EmptyInput extends AbstractInput

case class SingleVertexInput(vertex: Int) extends AbstractInput {
  override def toString = vertex.toString
}

case class MultipleVertexInput(vertex: Seq[Int]) extends AbstractInput {
  override def toString = vertex.toString
}