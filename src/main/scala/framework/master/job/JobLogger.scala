package framework.master.job

import akka.actor.{Actor, ActorRef}
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
      logFrameworkFormatted(sender, "Graph loaded in %d [ns]".format(time))
      logPerformanceFormatted("graph_loading(%s)".format(sender), time.toString)
      logPerformanceFormatted("edges", edges.toString)
      logPerformanceFormatted("nodes", nodes.toString)
    case ParititioningFinishedInfo(partitions: Int, time: Long) =>
      logFrameworkFormatted(sender, "Graph partitioned in %d partitions in %d [ns]".format(partitions, time))
      logPerformanceFormatted("number_of_partitions", partitions.toString)
      logPerformanceFormatted("partitioning_time", time.toString)
    case TaskFinishedInfo(partitionId: Int, time: Long) =>
      logPerformanceFormatted("finished_task_for_partition_%d".format(partitionId), time.toString)
    case CalculationStartedInfo =>
      logFrameworkFormatted(sender, "Started calculation")
    case CalculationFinishedInfo(time: Long) =>
      logFrameworkFormatted(sender, "Finished calculation in %d [ns]".format(time))
      logPerformanceFormatted("calculation_finished", time.toString)
    case JobFinishedInfo(totalTime: Long, calculationTime: Long) =>
      logFrameworkFormatted(sender, "Job finished in %d [ns] (total) / %d [ns] (calculation)".format(totalTime, calculationTime))
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
          rollPolicy = Policy.SigHup
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
          rollPolicy = Policy.SigHup
        )
      ),
      useParents = false
    )()
  }
}
