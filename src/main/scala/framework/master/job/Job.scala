package framework.master.job

import akka.actor.{Actor, ActorRef}
import calculations._
import framework._
import framework.master.WorkPool

import scala.collection.mutable

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

  def receive = {
    case AddWorker(worker) =>
      if (!workers.contains(worker)) {
        workers += worker
        worker ! SetupWorker(jobDefinition.getSetup)
      }
    case ExecutorAvailable =>
      println("EXECUTOR AVAILABLE")
      availableExecutors.enqueue(sender)
      self ! ScheduleWork
    case ScheduleWork =>
      println("SCHEDULE WORK")
      status match {
        case New => schedulePartitioning
        case Partitioning =>
        case Ready => scheduleWork
        case Finished => notifyMaster
      }
    case Result(result: AbstractResult) =>
      println("RESULT")
      println(result)
      result match {
        case Partitions(newPartitions) =>
          println("PARITIONS")
          setPartitions(newPartitions)
        case MapResult(map) =>
          println("MAP")
          println(map)
        case CompoundResult(stuff) =>
          println("Compound")
          println(stuff)
          workPool.markAsDone
      }
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
            executor ! Execute(TaskOnPartition(calculation, VertexInput(partition), partitionId, self))
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
    masterRef ! JobFinished
  }
}

/*
  package framework.master
 
 import akka.actor._
 import calculations._
 import framework._
 
 import scala.collection.mutable
 import scala.collection.mutable.ListBuffer
 
 class Master(listener: ActorRef,
                            logger: ActorRef,
              val setup: Map[String, String],
              val calculation: AbstractCalculation,
              val partitioningMethod: AbstractCalculation
             ) extends Actor {
   val start: Long = System.currentTimeMillis
   val work = new mutable.Queue[Int]
 
   val workPool = new WorkPool(calculation, partitioningMethod)
   val workers = ListBuffer[ActorRef]()
   val availableExecutors = new mutable.Queue[ActorRef]
 
   def handleResult(result: AbstractResult): Any = result match {
     case Partitions(partitions) =>
       workPool.setPartitions(partitions)
     case LongResult(_) =>
       workPool.markAsDone
     case MapResult(map) =>
       println(map)
       workPool.markAsDone
     case CompoundResult(results) =>
       println(results.head)
       workPool.markAsDone
   }
 
   def receive = {
     case Connected =>
       logger ! Info("CONNECTED")
       workers  = sender
       sender ! SetupWorker(setup)
 
     case Info(text) =>
       logger ! Info(text)
 
     case ExecutorAvailable =>
       logger ! Info("Worker ready!")
       availableExecutors.enqueue(sender)
 
       if (workPool.isWorkFinished) {
         listener ! Result(LongResult(1))
         context.stop(self)
       } else {
         while (workPool.isWorkAvailable && availableExecutors.nonEmpty) {
           val worker = availableExecutors.dequeue()
           workPool.getWork() match {
             case (calc, input) =>
               worker ! Execute(calc, input)
           }
         }
       }
 
     case Result(result: AbstractResult) =>
       handleResult(result)
       logger ! Info(result.toString)
   }
 }
*/
