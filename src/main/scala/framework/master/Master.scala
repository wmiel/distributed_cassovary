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
      runNextJobOrExit()
  }

  def runNextJobOrExit() = {
    if (jobDefinitions.hasNext) {
      val currentJobDefinition = jobDefinitions.next
      val job = new Job(self, workers, currentJobDefinition)
      currentJob = Some(
        context.actorOf(
          Props(job),
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


  //      sender ! SetupWorker(setup)
  //
  //    case Info(text) =>
  //      logger ! Info(text)
  //
  //    case ExecutorAvailable =>
  //      logger ! Info("Worker ready!")
  //      availableExecutors.enqueue(sender)
  //      self ! ScheduleWork
  //
  //    case ScheduleWork =>
  //      /*
  //
  //        // job = [{
  //        //   graph: xxx
  //        //   setup: {
  //        //      ...
  //        //   }
  //        //   partitioning: Random
  //        //   calculations: [
  //        //      A,
  //        //      B
  //        //    ]
  //        // }]
  //
  //        if(currentJobFinishedOrEmpty) {
  //          if(!nextJobAvailable) {
  //            Shutdown()
  //          } else {
  //            JobsManager.selectNextJob
  //            setupMaster() => {
  //              clearAvailableExecutors()
  //              createResultsListeners()
  //            }
  //            setupWorkers(JobsManager.getSetup)
  //          }
  //        } else {
  //          executeJob() ->
  //            partition
  //            calculate
  //        }
  //
  //
  //
  //
  //        if(workToDo) {
  //          executeWork()
  //         } else {
  //          switchWork()
  //          executeWork()
  //         }
  //
  //
  //       */
  ////
  ////      if (workPool.isWorkFinished) {
  ////        listener ! Result(LongResult(1))
  ////        context.stop(self)
  ////      } else {
  ////        while (workPool.isWorkAvailable && availableExecutors.nonEmpty) {
  ////          val worker = availableExecutors.dequeue()
  ////          workPool.getWork() match {
  ////            case (calc, input) =>
  ////              worker ! Execute(calc, input)
  ////          }
  ////        }
  ////      }
  //
  //    case Result(result: AbstractResult) =>
  //      handleResult(result)
  //      logger ! Info(result.toString)
  //  }
}