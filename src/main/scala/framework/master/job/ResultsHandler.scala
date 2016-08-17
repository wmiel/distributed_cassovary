package framework.master.job

import akka.actor.{Actor, ActorRef}
import calculations.{EdgeBMatrix, Result, VertexBMatrix, BMatrix}
import framework._

class ResultsHandler(jobRef: ActorRef) extends Actor {
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
        case Some(matrix) => new BMatrixWriter(matrix).save
        case None =>
      }
    }
  }
}
