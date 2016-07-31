package dist_casso

import akka.actor.Actor.Receive
import akka.actor.{Props, ActorIdentity, Actor, SupervisorStrategy}

class WorkerSupervisor(val masterPath: String)  extends Actor{
  override val supervisorStrategy =
    SupervisorStrategy.stoppingStrategy

  val worker = context.actorOf(Props(new Worker(masterPath)), name = "worker")

  override def receive =  {
    case _ => {

    }
  }
}
