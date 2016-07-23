package calculation

sealed trait AbstractCalculation {
  def calculate(x: Int, input: AbstractInput): AbstractResult
}

object ExampleCalculation extends AbstractCalculation {
  override def calculate(x: Int, input: AbstractInput): AbstractResult = input match {
    case SingleVertexInput(u) => new LongResult(u + x)
    case MultipleVertexInput(u) => new LongResult(u.sum)
  }
}

class RandomPartitionsCalculation(val partitionsNumber: Int) extends AbstractCalculation {
  override def calculate(graph: Int, input: AbstractInput): AbstractResult = {
    return Partitions(List(List(1,2,3), List(4,5,6), List(partitionsNumber, partitionsNumber, partitionsNumber), List(4,5,6), List(4,5,6), List(4,5,6), List(4,5,6), List(4,5,6), List(4,5,6)))
  }
}

