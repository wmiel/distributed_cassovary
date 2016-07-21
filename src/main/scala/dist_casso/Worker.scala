package dist_casso

import akka.actor._
import calculation.{AbstractInput, AbstractCalculation}

class Worker extends Actor {
  var x = 0
  var setup_str = ""
  val r = scala.util.Random

  def receive = {
    case SetupWorker(workerSetup) => {
      setup(workerSetup)
      sender ! Info(setup_str)
      sender ! WorkerReady
    }

    case Calc(calculation: AbstractCalculation, input: AbstractInput) => {
      sender ! Result(calculation.calculate(1, input))
      sender ! WorkerReady
    }
  }

  def setup(workerSetup: Map[String, String]) = {
    val y = r.nextInt(100)

    for(z <- 1 to y ) {
      for(v <- 1 to y) {
        x += 1
      }
    }

    setup_str = workerSetup.getOrElse("graph_name", "not-found") + "_" + x
  }
}