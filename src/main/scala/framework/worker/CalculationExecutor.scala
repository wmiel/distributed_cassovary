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
          println("PARTITIONING")
          sender ! Result(partitioning.calculate(graph, EmptyInput))
        case TaskOnPartition(calculation, input, partitionId, resultHandler) =>
          println("CALCULATING")
          resultHandler ! Result(calculation.calculate(graph, input))

      }
      jobRef ! ExecutorAvailable
    }

    //
    //      calculation: AbstractCalculation, input: AbstractInput) =>
    //    //      sender ! Info("Starting calculation")
    //    sender ! Result(calculation.calculate(graph, input))
    //    //      sender ! Info("Finished calculation")
    //    //      println(self.path.name + "CALCULATED STUFF")
    //    sender ! ExecutorAvailable
  }
}