package dist_casso

import akka.actor.{Props, ActorSystem}
import calculation.{RandomPartitionsCalculation, ExampleCalculation}

object GraphProcessor extends App {
  run

  def run: Unit = {
    val system = ActorSystem("GraphProcessing")

    // Set correct results handler (depending on selected algorithm)
    val listener = system.actorOf(Props[Listener], name = "listener")
    val logger = system.actorOf(Props[Logger], name = "logger")

    // Set up master and run selected algorithm
    val master = system.actorOf(Props(new Master(listener, logger, ExampleCalculation, new RandomPartitionsCalculation(5))), name = "master")
    master ! Calculate
  }
}