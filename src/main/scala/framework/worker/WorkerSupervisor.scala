package framework.worker

import akka.actor.{Actor, Props, SupervisorStrategy}

class WorkerSupervisor(val masterPath: String)  extends Actor{
  override val supervisorStrategy =
    SupervisorStrategy.stoppingStrategy

  val worker = context.actorOf(Props(new Worker(masterPath)), name = "worker")

  override def receive =  {
    case _ => {

    }
  }
}
