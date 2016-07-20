package dist_casso

import akka.actor.{Props, ActorSystem}
import calculation.ExampleCalculation

object GraphProcessor extends App {
  run

  def run: Unit = {
    val system = ActorSystem("GraphProcessing")

    // Set correct results handler (depending on selected algorithm)
    val listener = system.actorOf(Props[Listener], name = "listener")

    // Set up master and run selected algorithm
    val master = system.actorOf(Props(new Master(listener, ExampleCalculation)), name = "master")
    println("CALCULATE")
    master ! Calculate
  }
}