package framework.master

import akka.actor.SupervisorStrategy.Stop
import akka.actor._
import framework._
import framework.master.job.{Job, JobDefinition}

import scala.concurrent.duration._
import scala.language.postfixOps

class Master(jobDefinitions: Iterator[JobDefinition]) extends Actor {
  var workers = Set[ActorRef]()
  var currentJob: Option[ActorRef] = None

  override val supervisorStrategy =
    AllForOneStrategy(
      maxNrOfRetries = 10,
      withinTimeRange = 1 minute
    ) {
      case something: Any =>
        runNextJobOrExit()
        println(something)
        Stop
    }

  def receive = {
    case Start =>
      currentJob match {
        case None => runNextJobOrExit()
        case _ =>
      }
    case Terminated =>
      println("TERMINATED")
      println(sender)
      println(currentJob)
    case Connected =>
      workers += sender
      currentJob match {
        case Some(job) => job ! AddWorker(sender)
        case None =>
      }
    case JobFinished => {
      sender ! "OK"
      runNextJobOrExit()
    }
  }

  def runNextJobOrExit() = {
    println("Run next job or exit.")
    if (jobDefinitions.hasNext) {
      val currentJobDefinition = jobDefinitions.next
      currentJob = Some(
        context.actorOf(
          Props(new Job(self, workers, currentJobDefinition)),
          name = currentJobDefinition.getJobName
        )
      )
    } else {
      shutdown
    }
  }

  def shutdown = {
    workers.foreach { worker =>
      worker ! Exit
    }
    context.system.terminate()
  }
}
