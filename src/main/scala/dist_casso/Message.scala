package dist_casso

import calculation.{AbstractResult, AbstractInput, AbstractCalculation}

sealed trait Message

case object Calculate extends Message

case class Calc(calculation: AbstractCalculation, input: AbstractInput)

case class Result(result: AbstractResult)

case class Info(text: String) extends Message

case class SetupWorker(setup: Map[String, String]) extends Message

case object WorkerReady extends Message