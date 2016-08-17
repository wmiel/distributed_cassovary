package framework.worker

import akka.actor.SupervisorStrategy.Restart
import akka.actor.{Actor, OneForOneStrategy, Props}

import scala.concurrent.duration._
import scala.language.postfixOps

class WorkerSupervisor(val masterPath: String) extends Actor {
  override val supervisorStrategy =
    OneForOneStrategy(
      maxNrOfRetries = 10,
      withinTimeRange = 1 minute
    ) {
      case _ => Restart
    }
  //SupervisorStrategy.stoppingStrategy

  val worker = context.actorOf(Props(new Worker(masterPath)), name = "worker")

  override def receive = {
    case _ => {

    }
  }
}
