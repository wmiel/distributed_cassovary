package framework

import aggregations.VertexBMatrixAggregation
import akka.actor.{ActorSystem, Props}
import calculations.{DistanceBasedCalculation, RandomPartitionsCalculation}
import com.typesafe.config.ConfigFactory
import framework.master.Master
import framework.master.job.JobDefinition

object GraphProcessor extends App {
  run

  def run: Unit = {
    val system = ActorSystem("GraphProcessing", ConfigFactory.load("master"))
    val logger = system.actorOf(Props[Logger], name = "logger")

    val setup: Map[String, String] = Map(
      "graph_url" -> "http://snap.stanford.edu/data/facebook_combined.txt.gz", // "http://snap.stanford.edu/data/cit-HepPh.txt.gz",
      "cache_dir" -> "cache/test",
      "random_cache_dir" -> "true",
      "transform_to_undirected" -> "false",
      "calculation_executors_per_worker" -> "4"
    )

    val jobs = List(
      new JobDefinition(
        setup,
        RandomPartitionsCalculation(1000),
        DistanceBasedCalculation(List(VertexBMatrixAggregation)),
        "Job1"
      )
    )
    // Set up master and specify work to do
    val master = system.actorOf(
      Props(
        new Master(jobs.toIterator)
      ),
      name = "master"
    )
  }
}
