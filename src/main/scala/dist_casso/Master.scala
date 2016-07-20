package dist_casso

import akka.actor._
import akka.routing.{RoundRobinPool}
import calculation._

import scala.concurrent.duration.Duration
import scala.concurrent.duration._

import scala.collection.mutable.Queue

sealed trait Message

case object Calculate extends Message

case class Calc(calculation: AbstractCalculation, input: AbstractInput)

case class Result(result: AbstractResult)

case class Info(text: String) extends Message

case class SetupWorker(setup: Map[String, String]) extends Message

case object WorkerReady extends Message


class Master(listener: ActorRef, logger: ActorRef, val calculation: AbstractCalculation) extends Actor {

  val start: Long = System.currentTimeMillis
  val nrOfWorkers = 4
  var workers: Array[ActorRef] = new Array[ActorRef](nrOfWorkers)
  val work = new Queue[Int]
  var sum = 0

  work ++= List(5, 4, 3, 2, 1, 0)


  for (i <- 0 to nrOfWorkers - 1) {
    workers(i) = context.actorOf(Props[Worker], name = "worker_" + i)
  }

  //  val workerRouter = context.actorOf(
  //    Props[Worker].withRouter(RoundRobinPool(nrOfWorkers)), name = "workerRouter")

  def handleResult(x: AbstractResult): Any = x match {
    case LongResult(x) => {
      sum += x.toInt
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
      // Input = partitioner.getInput();
      if (work.nonEmpty) {
        sender ! Calc(calculation, SingleVertexInput(work.dequeue()))
      } else {
        listener ! Result(LongResult(sum))
        context.stop(self)
      }
    }

    case Result(result: AbstractResult) => {
      handleResult(result)
      logger ! Info(result.toString)
    }
  }
}