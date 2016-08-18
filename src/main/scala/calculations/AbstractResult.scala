package calculations

sealed trait Result

trait NonEmptyResult[T] extends Result {
  val result: T

  override def toString = result.toString
}

case class Partitions(override val result: Array[Seq[Int]]) extends NonEmptyResult[Array[Seq[Int]]]

case class Distances(result: Seq[Seq[(Int, Int)]]) extends NonEmptyResult[Seq[Seq[(Int, Int)]]]

trait BMatrix {
  val result: Map[(Int, Int), Int]

  def add(other: BMatrix) = {
    (result.keySet ++ other.result.keySet).map(key =>
      (key, result.getOrElse(key, 0) + other.result.getOrElse(key, 0))
    ).toMap
  }

  def toOutputFormat = {
    result.toList.sortBy(_._1).map { case ((x, y), z) => (x, y, z) }
  }
}

trait PerVertexValue[T] {
  val result: Seq[(Int, T)]

  def toOutputFormat = {
    result.toList.sortBy(_._1)
  }
}

case class VertexBMatrix(override val result: Map[(Int, Int), Int]) extends NonEmptyResult[Map[(Int, Int), Int]] with BMatrix {
  def +(other: VertexBMatrix) = {
    VertexBMatrix(this.add(other))
  }
}

case class EdgeBMatrix(override val result: Map[(Int, Int), Int]) extends NonEmptyResult[Map[(Int, Int), Int]] with BMatrix {
  def +(other: EdgeBMatrix) = {
    EdgeBMatrix(this.add(other))
  }
}

case class OutgoingDegreePerVertex(override val result: Seq[(Int, Int)]) extends NonEmptyResult[Seq[(Int, Int)]] with PerVertexValue[Int] {
  def +(other: OutgoingDegreePerVertex) = {
    OutgoingDegreePerVertex(result ++ other.result)
  }
}
