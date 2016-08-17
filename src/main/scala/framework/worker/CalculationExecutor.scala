package framework.worker

import akka.actor._
import calculations.EmptyInput
import com.twitter.cassovary.graph.{DirectedGraph, Node}
import framework._


class CalculationExecutor(val jobRef: ActorRef, val graph: DirectedGraph[Node]) extends Actor {
  jobRef ! ExecutorAvailable

  def receive = {
    case Execute(task) => {
      task match {
        case PartitioningTask(partitioning) =>
          sender ! CalculationResult(partitioning.calculate(graph, EmptyInput))
        case TaskOnPartition(calculation, input, partitionId, resultHandler) =>
          resultHandler ! CalculationResult(calculation.calculate(graph, input))
      }
      jobRef ! ExecutorAvailable
    }
  }
}
