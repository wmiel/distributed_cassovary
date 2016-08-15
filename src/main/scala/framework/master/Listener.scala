package framework.master

import akka.actor.Actor
import framework.Result

class Listener extends Actor {
  def receive = {
    case Result(result) => {
      println("RESULT:")
      println(result.toString)
      context.system.terminate()
    }
  }
}
