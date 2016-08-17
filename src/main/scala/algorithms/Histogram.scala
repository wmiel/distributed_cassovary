package algorithms

object Histogram {
  def apply[T](value: (T => Int))(elements: Iterable[T]) = {
    elements.groupBy(value).map({case (c, list) => (c, list.size) })
  }
}
