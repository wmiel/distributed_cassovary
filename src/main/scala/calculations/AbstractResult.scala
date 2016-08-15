package calculations

sealed trait AbstractResult {
}

case object EmptyResult extends AbstractResult {
  override def toString = "Empty"
}

trait NonEmptyResult[T] {
  def result: T

  override def toString = result.toString
}

case class MapResult(override val result: Map[Int, Int]) extends AbstractResult with NonEmptyResult[Map[Int, Int]]

case class ArrayResult(override val result: Array[Int]) extends AbstractResult with NonEmptyResult[Array[Int]]

case class LongResult(override val result: Long) extends AbstractResult with NonEmptyResult[Long]

case class Partitions(override val result: Array[Seq[Int]]) extends AbstractResult with NonEmptyResult[Array[Seq[Int]]]

case class CompoundResult(override val result: Seq[AbstractResult]) extends AbstractResult with NonEmptyResult[Seq[AbstractResult]]