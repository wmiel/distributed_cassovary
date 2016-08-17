package framework

import akka.actor.{ActorSystem, Props}
import calculations.{RandomPartitionsCalculation, VertexBMatrixCalculation}
import com.typesafe.config.ConfigFactory
import framework.master.Master
import framework.master.job.JobDefinition

object GraphProcessor extends App {
  run

  def run: Unit = {
    val system = ActorSystem("GraphProcessing", ConfigFactory.load("master"))

    val setup: Map[String, String] = Map(
      "graph_url" -> "http://snap.stanford.edu/data/facebook_combined.txt.gz", //http://snap.stanford.edu/data/p2p-Gnutella24.txt.gz", // "http://snap.stanford.edu/data/facebook_combined.txt.gz", //"http://snap.stanford.edu/data/cit-HepPh.txt.gz", //
      "cache_dir" -> "cache/test",
      "random_cache_dir" -> "true",
      "transform_to_undirected" -> "true",
      "calculation_executors_per_worker" -> "4",
      "file_separator" -> "auto"
    )

    val jobs = List(
      new JobDefinition(
        setup,
        RandomPartitionsCalculation(1000),
        VertexBMatrixCalculation,
        "Job1"
      ),
      new JobDefinition(
        setup,
        RandomPartitionsCalculation(1000),
        VertexBMatrixCalculation,
        "Job2"
      )
    )
    // Set up master and specify work to do
    val master = system.actorOf(
      Props(
        new Master(jobs.toIterator)
      ),
      name = "master"
    )
    master ! Start
  }
}
