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
          jobRef ! Info("Node count: %s, Edge count: %s".format(graph.nodeCount, graph.edgeCount))
          sender ! CalculationResult(partitioning.calculate(graph, EmptyInput))
        case TaskOnPartition(calculation, input, partitionId, resultHandler) =>
          println("EXECUTE")
          val startTime = System.nanoTime()
          val result = CalculationResult(calculation.calculate(graph, input))
          val endTime = System.nanoTime()
          jobRef ! Info("Task for Partition(%d) completed in %d [ns]"
            .format(
              partitionId,
              endTime - startTime
            )
          )
          resultHandler ! result
      }
      jobRef ! ExecutorAvailable
    }
    case _ =>
      println("MESSAGE COULD NOT BE HANDLED BY EXECUTOR")
  }
}
