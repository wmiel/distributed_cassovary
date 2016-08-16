package framework

import akka.actor.ActorRef
import calculations.{AbstractCalculation, AbstractInput, AbstractResult, VertexInput}

sealed trait Message

case object Calculate extends Message

case object Connected extends Message

case object ScheduleWork extends Message

case object JobFinished extends Message

case class Execute(task: Task)

case class Result(result: AbstractResult)

case class AddWorker(workerRef: ActorRef)

case class Info(text: String) extends Message

case class SetupWorker(setup: Map[String, String]) extends Message

case object ExecutorAvailable extends Message


sealed trait Task

case class PartitioningTask(partitioning: AbstractCalculation) extends Task
case class TaskOnPartition(calculation: AbstractCalculation,
                           input: VertexInput,
                           partitionId: Int,
                           resultHandler: ActorRef) extends Task