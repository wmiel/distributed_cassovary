package calculation

sealed trait AbstractResult

case object EmptyResult extends AbstractResult {
  override def toString = "Empty"
}

case class MapResult(result: Map[Int, Int]) extends AbstractResult {
  override def toString = result.toString
}

case class LongResult(result: Long) extends AbstractResult {
  override def toString = result.toString
}

case class Partitions(result: Array[Seq[Int]]) extends AbstractResult {
  override def toString = result.toString
}