package dist_casso

import akka.actor._
import calculation._

import scala.collection.mutable
import scala.collection.mutable.Queue

sealed trait Message

case object Calculate extends Message

case class Calc(calculation: AbstractCalculation, input: AbstractInput)

case class Result(result: AbstractResult)

case class Info(text: String) extends Message

case class SetupWorker(setup: Map[String, String]) extends Message

case object WorkerReady extends Message

class Master(listener: ActorRef,
             logger: ActorRef,
             val calculation: AbstractCalculation,
             val partitioningMethod: AbstractCalculation
            ) extends Actor {
  val start: Long = System.currentTimeMillis
  val nrOfWorkers = 4
  var workers: Array[ActorRef] = new Array[ActorRef](nrOfWorkers)
  val work = new mutable.Queue[Int]
  var sum = 0

  val workPool = new WorkPool(calculation, partitioningMethod)
  val readyWorkers = new mutable.Queue[ActorRef]


  for (i <- 0 to nrOfWorkers - 1) {
    workers(i) = context.actorOf(Props[Worker], name = "worker_" + i)
  }

  //  val workerRouter = context.actorOf(
  //    Props[Worker].withRouter(RoundRobinPool(nrOfWorkers)), name = "workerRouter")

  def handleResult(x: AbstractResult): Any = x match {
    case Partitions(partitions) => {
      workPool.setPartitions(partitions)
    }
    case LongResult(x) => {
      sum += x.toInt
      workPool.markAsDone
    }
  }

  def receive = {
    case Calculate => {
      logger ! Info("Calculate!")

      val setup: Map[String, String] = Map("graph_name" -> "test_graph")

      for (i <- 0 to nrOfWorkers - 1) {
        workers(i) ! SetupWorker(setup)
      }
    }

    case Info(text) => {
      logger ! Info(text)
    }

    case WorkerReady => {
      logger ! Info("Worker ready!")
      readyWorkers.enqueue(sender)

      if (workPool.isWorkFinished) {
        listener ! Result(LongResult(sum))
        context.stop(self)
      } else {
        while(workPool.isWorkAvailable && readyWorkers.nonEmpty) {
          val worker = readyWorkers.dequeue()
          val work = workPool.getWork()
          worker ! Calc(work._1, work._2)
        }
      }
    }

    case Result(result: AbstractResult) => {
      handleResult(result)
      logger ! Info(result.toString)
    }
  }
}