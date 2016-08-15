package framework

import calculations.{AbstractResult, AbstractInput, AbstractCalculation}

sealed trait Message

case object Calculate extends Message

case object Connected extends Message

case class Execute(calculation: AbstractCalculation, input: AbstractInput)

case class Result(result: AbstractResult)

case class Info(text: String) extends Message

case class SetupWorker(setup: Map[String, String]) extends Message

case object ExecutorAvailable extends Message