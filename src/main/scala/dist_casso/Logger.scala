package dist_casso

import akka.actor.Actor
import akka.event.Logging
import akka.event.LoggingAdapter

class Logger extends Actor {
  var log: LoggingAdapter = Logging.getLogger(context.system, this)

  def receive = {
    case Info(text) => {
      log.info(text)
    }
  }
}
