package framework.master

import akka.actor._
import framework._
import framework.master.job.{Job, JobDefinition}

class Master(jobDefinitions: Iterator[JobDefinition]) extends Actor {
  var workers = Set[ActorRef]()
  var currentJob: Option[ActorRef] = None

  runNextJobOrExit()

  def receive = {
    case Connected =>
      workers += sender
      currentJob match {
        case Some(job) => job ! AddWorker(sender)
        case None =>
      }
    case JobFinished =>
      currentJob match {
        case Some(job) => job ! PoisonPill
        case None =>
      }
      if(sender == currentJob.get) {
        runNextJobOrExit()
      }
  }

  def runNextJobOrExit() = {
    if (jobDefinitions.hasNext) {
      val currentJobDefinition = jobDefinitions.next
      currentJob = Some(
        context.system.actorOf(
          Props(new Job(self, workers, currentJobDefinition)),
          name = currentJobDefinition.getName
        )
      )
    } else {
      shutdown
    }
  }

  def shutdown = {
    workers.foreach { worker =>
      worker ! PoisonPill
    }
    context.system.terminate()
  }
}
