package framework.worker

import akka.actor._
import calculations.{EmptyInput, Partitions}
import com.twitter.cassovary.graph.{DirectedGraph, Node}
import framework._


class CalculationExecutor(val jobRef: ActorRef, val graph: DirectedGraph[Node]) extends Actor {
  jobRef ! ExecutorAvailable

  def receive = {
    case Execute(task) => {
      task match {
        case PartitioningTask(partitioning) =>
          val startTime = System.nanoTime()
          val partitionedGraph = partitioning.calculate(graph, EmptyInput)
          sender ! CalculationResult(partitionedGraph)
          partitionedGraph match {
            case Partitions(partitions) =>
              jobRef ! ParititioningFinishedInfo(partitions.length, System.nanoTime() - startTime)
            case _ =>
              jobRef ! ParititioningFinishedInfo(-1, System.nanoTime() - startTime)
          }
        case TaskOnPartition(calculation, input, partitionId, resultHandler) =>
          val startTime = System.nanoTime()
          val result = CalculationResult(calculation.calculate(graph, input))
          val endTime = System.nanoTime()

          println(s"Task finished $partitionId")
          jobRef ! TaskFinishedInfo(partitionId, endTime - startTime)
          resultHandler ! result
      }
      jobRef ! ExecutorAvailable
    }
    case _ =>
      println("MESSAGE COULD NOT BE HANDLED BY EXECUTOR")
  }
}
