package framework.master.job

import akka.actor.{Actor, ActorRef}
import calculations._
import framework.{SaveOutput, _}

class ResultsHandler(jobRef: ActorRef, outputNamePrefix: String) extends Actor {
  var resultVertexBMatrix: Option[VertexBMatrix] = None
  var resultEdgeBMatrix: Option[EdgeBMatrix] = None
  var perVertexOutgoingDegrees: Option[OutgoingDegreePerVertex] = None

  def receive = {
    case CalculationResult(result: Result) =>
      result match {
        case vertexBM: VertexBMatrix =>
          resultVertexBMatrix match {
            case Some(matrix) => resultVertexBMatrix = Some(matrix + vertexBM)
            case None => resultVertexBMatrix = Some(vertexBM)
          }
        case edgeBM: EdgeBMatrix =>
          resultEdgeBMatrix match {
            case Some(matrix) => resultEdgeBMatrix = Some(matrix + edgeBM)
            case None => resultEdgeBMatrix = Some(edgeBM)
          }
        case perVertexDegree: OutgoingDegreePerVertex =>
          perVertexOutgoingDegrees match {
            case Some(perVertex) => perVertexOutgoingDegrees = Some(perVertex + perVertexDegree)
            case None => perVertexOutgoingDegrees = Some(perVertexDegree)
          }
      }
      jobRef ! WorkDone
    case SaveOutput =>
      println("SAVING OUTPUT")
      saveOutputs
      println("Saved")
      sender ! "DONE"
  }

  def saveOutputs = {
    List(resultVertexBMatrix, resultEdgeBMatrix).foreach { bmatrix: Option[BMatrix] =>
      bmatrix match {
        case Some(matrix) => new BMatrixWriter(matrix, outputNamePrefix, matrix.getClass.getSimpleName).save
        case None =>
      }
    }
    List(perVertexOutgoingDegrees).foreach { perVertex: Option[PerVertexValue[Int]] =>
      perVertex match {
        case Some(perVertexValues) => new PerVertexWriter(perVertexValues, outputNamePrefix, perVertexValues.getClass.getSimpleName).save
        case None =>
      }
    }
    resultVertexBMatrix = None
    resultEdgeBMatrix = None
    perVertexOutgoingDegrees = None
  }
}
