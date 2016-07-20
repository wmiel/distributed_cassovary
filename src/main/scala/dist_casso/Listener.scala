package dist_casso

import akka.actor.Actor

class Listener extends Actor {
  def receive = {
    case Result(result) => {
      println("RESULT:")
      println(result.toString)
      context.system.terminate()
    }
  }
}
