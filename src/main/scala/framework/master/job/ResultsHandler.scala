package framework.master.job

import akka.actor.{Actor, ActorRef}
import calculations.{BMatrix, EdgeBMatrix, Result, VertexBMatrix}
import framework.{SaveOutput, _}

class ResultsHandler(jobRef: ActorRef, outputNamePrefix: String) extends Actor {
  var resultVertexBMatrix: Option[VertexBMatrix] = None
  var resultEdgeBMatrix: Option[EdgeBMatrix] = None

  def receive = {
    case CalculationResult(result: Result) =>
      result match {
        case vertexBM: VertexBMatrix =>
          resultVertexBMatrix match {
            case Some(matrix) => resultVertexBMatrix = Some(matrix + vertexBM)
            case None => resultVertexBMatrix = Some(vertexBM)
          }
      }
      jobRef ! WorkDone
    case SaveOutput =>
      saveOutputs
      sender ! "DONE"
  }

  def saveOutputs = {
    List(resultVertexBMatrix, resultEdgeBMatrix).foreach { bmatrix:Option[BMatrix] =>
      bmatrix match {
        case Some(matrix) => new BMatrixWriter(matrix, outputNamePrefix, matrix.getClass.getSimpleName).save
        case None =>
      }
    }
  }
}
