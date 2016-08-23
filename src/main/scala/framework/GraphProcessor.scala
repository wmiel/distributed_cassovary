package framework

import akka.actor.{ActorSystem, Props}
import calculations.{EdgeBMatrixCalculation, OutgoingDegreePerVertexCalculation, RandomPartitionsCalculation, VertexBMatrixCalculation}
import framework.master.Master
import framework.master.job.JobDefinition

object GraphProcessor extends App with ConfigLoader {
  run

  def jobs(setup: Map[String, String]) = {
    List(new JobDefinition(
      setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella25.txt.gz", "calculation_executors_per_worker" -> "4"),
      RandomPartitionsCalculation(100),
      EdgeBMatrixCalculation,
      "Job_40_of_49",
      "Job_40_of_49"),
    new JobDefinition(
      setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella30.txt.gz", "calculation_executors_per_worker" -> "4"),
      RandomPartitionsCalculation(100),
      EdgeBMatrixCalculation,
      "Job_43_of_49",
      "Job_43_of_49"
    )
    )
  }

  def run: Unit = {
    val config = parseCustomConfig("master", "config/master.conf")
    val system = ActorSystem("GraphProcessing", config)

    val setup: Map[String, String] = Map(
      // "graph_url" -> "http://snap.stanford.edu/data/email-Enron.txt.gz", // "http://snap.stanford.edu/data/facebook_combined.txt.gz", //, //http://snap.stanford.edu/data/p2p-Gnutella24.txt.gz", // "http://snap.stanford.edu/data/facebook_combined.txt.gz", //"http://snap.stanford.edu/data/cit-HepPh.txt.gz", //
      "cache_dir" -> "cache/test",
      "random_cache_dir" -> "true",
      "transform_to_undirected" -> "true",
      // "calculation_executors_per_worker" -> "2",
      "file_separator" -> "auto",
      "graph_type" -> "array" // or "shared"
    )

    // Set up master and specify work to do
    val master = system.actorOf(
      Props(
        new Master(jobs(setup).toIterator)
      ),
      name = "master"
    )
    master ! Start
  }
}
