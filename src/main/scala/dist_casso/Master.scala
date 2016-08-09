package dist_casso

import akka.actor._
import calculation._

import scala.collection.mutable

class Master(listener: ActorRef,
             logger: ActorRef,
             val setup: Map[String, String],
             val calculation: AbstractCalculation,
             val partitioningMethod: AbstractCalculation
            ) extends Actor {
  val start: Long = System.currentTimeMillis
  val work = new mutable.Queue[Int]
  var sum = 0

  val workPool = new WorkPool(calculation, partitioningMethod)
  val readyWorkers = new mutable.Queue[ActorRef]


  //for (i <- 0 to nrOfWorkers - 1) {
  //  workers(i) = context.actorOf(Props[Worker], name = "worker_" + i)
  //}

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
    }

    case Connected => {
      logger ! Info("CONNECTED")
      sender ! SetupWorker(setup)
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
        while (workPool.isWorkAvailable && readyWorkers.nonEmpty) {
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