package framework.worker

import akka.actor._
import calculations.{AbstractCalculation, AbstractInput}
import com.twitter.cassovary.graph.{DirectedGraph, Node}
import framework.{Execute, ExecutorAvailable, Result}


class CalculationExecutor(val masterRef: ActorRef, val graph: DirectedGraph[Node]) extends Actor {
  masterRef ! ExecutorAvailable

  def receive = {
    case Execute(calculation: AbstractCalculation, input: AbstractInput) =>
      //      sender ! Info("Starting calculation")
      sender ! Result(calculation.calculate(graph, input))
      //      sender ! Info("Finished calculation")
      //      println(self.path.name + "CALCULATED STUFF")
      sender ! ExecutorAvailable
  }
}