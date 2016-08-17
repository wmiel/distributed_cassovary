package framework.master.job

import akka.actor.{Actor, ActorRef, Props}
import akka.pattern.ask
import akka.util.Timeout
import calculations._
import framework._
import framework.master.WorkPool

import scala.collection.mutable
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

sealed trait JobStatus

case object New extends JobStatus

case object Partitioning extends JobStatus

case object Ready extends JobStatus

case object Finished extends JobStatus

class Job(masterRef: ActorRef,
          var workers: Set[ActorRef],
          jobDefinition: JobDefinition
         ) extends Actor {

  var status: JobStatus = New
  val availableExecutors = new mutable.Queue[ActorRef]

  var partitions: Option[Array[Seq[Int]]] = None
  val workPool = new WorkPool(jobDefinition.getCalculation)

  workers.foreach { worker =>
    worker ! SetupWorker(jobDefinition.getSetup)
  }

  val resultsHandler = context.system.actorOf(
    Props(new ResultsHandler(self)),
    name = jobDefinition.getJobName + "ResultsHandler"
  )

  val jobLogger = context.system.actorOf(
    Props(new JobLogger(self, jobDefinition)),
    name = jobDefinition.getJobName + "Logger"
  )

  def receive = {
    case AddWorker(worker) =>
      if (!workers.contains(worker)) {
        workers += worker
        worker ! SetupWorker(jobDefinition.getSetup)
      }
    case ExecutorAvailable =>
      availableExecutors.enqueue(sender)
      self ! ScheduleWork
    case ScheduleWork =>
      status match {
        case New => schedulePartitioning
        case Partitioning =>
        case Ready => scheduleWork
        case Finished => notifyMaster
      }
    case CalculationResult(result: Result) =>
      result match {
        case Partitions(newPartitions) =>
          setPartitions(newPartitions)
        case VertexBMatrix(map) =>
          println(map)
      }
    case WorkDone =>
      workPool.markAsDone
      self ! ScheduleWork
    case info: Info =>
      jobLogger forward info
  }

  private

  def setPartitions(newPartitions: Array[Seq[Int]]) = {
    this.partitions = Some(newPartitions)
    workPool.setPartitions(newPartitions)
    status = Ready
  }

  def scheduleWork = {
    if (workPool.isWorkFinished) {
      status = Finished
      notifyMaster
    } else {
      while (workPool.isWorkAvailable && availableExecutors.nonEmpty) {
        val executor = availableExecutors.dequeue()
        workPool.getWork() match {
          case (calculation, partition, partitionId) =>
            executor ! Execute(TaskOnPartition(calculation, VertexInput(partition), partitionId, resultsHandler))
        }
      }
    }
  }

  def schedulePartitioning = {
    availableExecutors.headOption match {
      case Some(executor) =>
        status = Partitioning
        executor ! Execute(PartitioningTask(jobDefinition.getPartitioning))
      case None =>
    }
  }

  def notifyMaster = {
    println("NOTIFY MASTER")
    implicit val timeout = Timeout(5 seconds)
    val future = resultsHandler ? SaveOutput
    val result = Await.result(future, timeout.duration).asInstanceOf[String]
    println(result)

    masterRef ! JobFinished
  }
}
