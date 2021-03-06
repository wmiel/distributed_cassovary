package framework

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import framework.worker.WorkerSupervisor

object GraphWorker extends App with ConfigLoader {
  run

  def run: Unit = {
    val config = parseCustomConfig("worker", "config/worker.conf")
    val masterEndpoint = config.getString("graph-worker.master-endpoint")
    val masterPort = config.getString("graph-worker.master-port")

    println(s"Using master: ${masterEndpoint}:${masterPort}")

    val system = ActorSystem("GraphWorkerSystem", ConfigFactory.load(config))
    val masterPath = s"akka.tcp://GraphProcessing@${masterEndpoint}:${masterPort}/user/master"
    system.actorOf(Props(new WorkerSupervisor(masterPath)), name = "workerSupervisor")
  }
}
