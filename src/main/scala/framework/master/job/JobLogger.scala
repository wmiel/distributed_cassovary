package framework.master.job

import akka.actor.{Actor, ActorRef}
import com.twitter.logging.Logger
import framework.{FinalInfo, Info}
import util.CassovaryLogger
import util.Env.PRODUCTION

class JobLogger(val masterRef: ActorRef, val jobDefinition: JobDefinition) extends Actor {
  CassovaryLogger.setUp(PRODUCTION)

  val logger = Logger.get("Job")
  logger.info(jobDefinition.toString)

  def logFormatted(sender: ActorRef, text: String) = {
    logger.info("%s (%s): %s".format(jobDefinition.getJobName, sender.path, text))
  }

  def receive = {
    case Info(text) => logFormatted(sender, text)
    case FinalInfo(text) =>
      logFormatted(sender, text)
      sender ! "OK"
  }
}
