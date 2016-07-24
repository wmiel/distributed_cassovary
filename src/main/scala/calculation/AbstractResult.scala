package calculation

sealed trait AbstractResult

case class LongResult(result: Long) extends AbstractResult {
  override def toString = result.toString
}

case class Partitions(result: Array[Seq[Long]]) extends AbstractResult {
  override def toString = result.toString
}