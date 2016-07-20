package dist_casso

import akka.actor.Actor

class Logger extends Actor {
  def receive = {
    case Info(text) => {
      println(text)
    }
  }
}
