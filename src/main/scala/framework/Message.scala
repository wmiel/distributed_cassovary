package framework

import akka.actor.ActorRef
import calculations.{AbstractCalculation, Result, VertexInput}

sealed trait Message

case object SaveOutput extends Message

case object Start extends Message

case object Connected extends Message

case object Exit extends Message

case object ScheduleWork extends Message

case object JobFinished extends Message

case class Execute(task: Task) extends Message

case class CalculationResult(result: Result) extends Message

case object WorkDone extends Message

case class AddWorker(workerRef: ActorRef) extends Message

/* LOGGING */
sealed trait Log

case class Info(text: String) extends Log

case object JobStartedInfo extends Log

case class GraphLoadedInfo(time: Long, nodes: Int, edges: Long) extends Log

case class ParititioningFinishedInfo(parititions: Int, time: Long) extends Log

case class TaskFinishedInfo(partitionId:Int, time: Long) extends Log

case object CalculationStartedInfo extends Log

case class CalculationFinishedInfo(time: Long) extends Log

case class JobFinishedInfo(totalTime: Long, calculationTime: Long) extends Log
/* /LOGGING */

case class SetupWorker(setup: Map[String, String]) extends Message

case object ExecutorAvailable extends Message

sealed trait Task

case class PartitioningTask(partitioning: AbstractCalculation) extends Task

case class TaskOnPartition(calculation: AbstractCalculation,
                           input: VertexInput,
                           partitionId: Int,
                           resultHandler: ActorRef) extends Task
