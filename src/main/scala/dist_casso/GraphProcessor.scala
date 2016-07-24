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

    val setup: Map[String, String] = Map("graph_name" -> "http://snap.stanford.edu/data/facebook_combined.txt.gz", "graph_size" -> "199")
    // Set up master and run selected algorithm
    val master = system.actorOf(Props(new Master(listener, logger, setup, ExampleCalculation, new RandomPartitionsCalculation(1000))), name = "master")
    master ! Calculate
  }
}