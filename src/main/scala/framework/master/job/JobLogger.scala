package framework.master.job

import akka.actor.{Actor, ActorRef}
import com.twitter.logging.Policy.Never
import com.twitter.logging._
import framework._

class JobLogger(val masterRef: ActorRef, val jobDefinition: JobDefinition) extends Actor {
  configurePerformanceLog
  configureFrameworkLog

  val performanceLogger = Logger.get("performance")
  val frameworkLogger = Logger.get("framework")

  frameworkLogger.info(jobDefinition.toString)
  performanceLogger.info(jobDefinition.toString)

  def receive = {
    case JobStartedInfo =>
      frameworkLogger.info(jobDefinition.toString)
      performanceLogger.info(jobDefinition.toString)
    case GraphLoadedInfo(time: Long, nodes: Int, edges: Long) =>
      logFrameworkFormatted(sender, "Graph loaded in %s".format(nanoSecondToHuman(time)))
      logPerformanceFormatted("graph_loading(%s)".format(sender), time.toString)
      logPerformanceFormatted("edges", edges.toString)
      logPerformanceFormatted("nodes", nodes.toString)
    case ParititioningFinishedInfo(partitions: Int, time: Long) =>
      logFrameworkFormatted(sender, "Graph partitioned in %d partitions in %s".
        format(partitions, nanoSecondToHuman(time)))
      logPerformanceFormatted("number_of_partitions", partitions.toString)
      logPerformanceFormatted("partitioning_time", time.toString)
    case TaskFinishedInfo(partitionId: Int, time: Long) =>
      if(partitionId % 10 == 0) {
        logFrameworkFormatted(sender, "Finished task for partitions %d in %s".
          format(partitionId, nanoSecondToHuman(time)))
      }
      logPerformanceFormatted("finished_task_for_partition_%d".format(partitionId), time.toString)
    case CalculationStartedInfo =>
      logFrameworkFormatted(sender, "Started calculation")
    case CalculationFinishedInfo(time: Long) =>
      logFrameworkFormatted(sender, "Finished calculation in %s".format(nanoSecondToHuman(time)))
      logPerformanceFormatted("calculation_finished", time.toString)
    case JobFinishedInfo(totalTime: Long, calculationTime: Long) =>
      logFrameworkFormatted(sender, "Job finished in %s (total) / %s(calculation)".
        format(nanoSecondToHuman(totalTime),
          nanoSecondToHuman(calculationTime)))
      logPerformanceFormatted("job_finished_total", totalTime.toString)
      logPerformanceFormatted("job_finished_calculation", calculationTime.toString)
      sender ! "OK"
    case Info(text) => logFrameworkFormatted(sender, text)
  }

  def logFrameworkFormatted(sender: ActorRef, text: String) = {
    frameworkLogger.info("(%s) %s".format(sender.path, text))
  }

  def logPerformanceFormatted(key: String, value: String) = {
    performanceLogger.info("%s: %s".format(key, value))
  }

  def configurePerformanceLog = {
    LoggerFactory(
      node = "performance",
      level = Some(Level.INFO),
      handlers = List(
        FileHandler(
          formatter = new Formatter(prefix = "%.3s [<yyyyMMdd-HH:mm:ss.SSS>] %s (" + jobDefinition.getId + ")| "),
          filename = "performance.log",
          rollPolicy = Never
        )
      ),
      useParents = false
    )()
  }

  def configureFrameworkLog = {
    LoggerFactory(
      node = "framework",
      level = Some(Level.INFO),
      handlers = List(
        FileHandler(
          filename = "framework.log",
          rollPolicy = Never
        )
      ),
      useParents = false
    )()
  }

  def nanoSecondToHuman(nano: Long) = {
    val ms = nano / 1000000
    val miliseconds = ms % 1000
    val seconds = (ms / 1000) % 60
    val minutes = (ms / (1000 * 60)) % 60
    val hours = (ms / (1000 * 60 * 60)) % 24
    "%d hours %d minutes %d seconds %s miliseconds".format(hours, minutes, seconds, miliseconds)
  }
}
