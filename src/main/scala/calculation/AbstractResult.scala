package calculation

sealed trait AbstractResult

case class LongResult(result: Long) extends AbstractResult {
  override def toString = result.toString
}

case class Partitions(result: Seq[Seq[Long]]) extends AbstractResult {
  override def toString = result.toString
}