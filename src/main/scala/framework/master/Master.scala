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
      workers += sender
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