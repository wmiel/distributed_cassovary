package framework

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import framework.worker.WorkerSupervisor

object GraphWorker extends App {
  run

  def run: Unit = {
    val system = ActorSystem("GraphWorkerSystem", ConfigFactory.load("worker"))
    val masterPath = "akka.tcp://GraphProcessing@127.0.0.1:2551/user/master"
    system.actorOf(Props(new WorkerSupervisor(masterPath)), name = "workerSupervisor")
  }
}
