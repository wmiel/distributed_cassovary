package calculation

trait AbstractInput
trait AbstractResult

trait AbstractCalculation {
  def calculate(x: Int, input: AbstractInput): AbstractResult
}

case class SingleVertexInput(u: Int) extends AbstractInput {
  override def toString = u.toString
}

case class LongResult(result: Long) extends AbstractResult {
  override def toString = result.toString
}

object ExampleCalculation extends AbstractCalculation {
  override def calculate(x: Int, input: AbstractInput): AbstractResult = input match {
    case SingleVertexInput(u) => new LongResult(u + x)
  }
}
