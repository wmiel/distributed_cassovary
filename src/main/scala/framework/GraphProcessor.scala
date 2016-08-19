package framework

import akka.actor.{ActorSystem, Props}
import calculations.{EdgeBMatrixCalculation, OutgoingDegreePerVertexCalculation, RandomPartitionsCalculation, VertexBMatrixCalculation}
import com.typesafe.config.ConfigFactory
import framework.master.Master
import framework.master.job.JobDefinition

object GraphProcessor extends App {
  run

  def run: Unit = {
    val system = ActorSystem("GraphProcessing", ConfigFactory.load("master"))

    val setup: Map[String, String] = Map(
      "graph_url" -> "http://snap.stanford.edu/data/email-Enron.txt.gz", // "http://snap.stanford.edu/data/facebook_combined.txt.gz", //, //http://snap.stanford.edu/data/p2p-Gnutella24.txt.gz", // "http://snap.stanford.edu/data/facebook_combined.txt.gz", //"http://snap.stanford.edu/data/cit-HepPh.txt.gz", //
      "cache_dir" -> "cache/test",
      "random_cache_dir" -> "true",
      "transform_to_undirected" -> "true",
      "calculation_executors_per_worker" -> "2",
      "file_separator" -> "auto"
    )

    val jobs = List(
      new JobDefinition(
        setup ++ Map("graph_type" -> "shared"),
        RandomPartitionsCalculation(1000),
        OutgoingDegreePerVertexCalculation,
        "Job1",
        "Job1"
      ),
      new JobDefinition(
        setup ++ Map("graph_type" -> "array"),
        RandomPartitionsCalculation(1000),
        OutgoingDegreePerVertexCalculation,
        "Job2",
        "Job2"
      ),
      new JobDefinition(
        setup ++ Map("graph_type" -> "shared"),
        RandomPartitionsCalculation(100),
        VertexBMatrixCalculation,
        "Job3",
        "Job3"
      ),
      new JobDefinition(
        setup ++ Map("graph_type" -> "array"),
        RandomPartitionsCalculation(100),
        VertexBMatrixCalculation,
        "Job4",
        "Job4"
      ),
      new JobDefinition(
        setup ++ Map("graph_type" -> "shared"),
        RandomPartitionsCalculation(100),
        EdgeBMatrixCalculation,
        "Job5",
        "Job5"
      ),
      new JobDefinition(
        setup ++ Map("graph_type" -> "array"),
        RandomPartitionsCalculation(100),
        EdgeBMatrixCalculation,
        "Job6",
        "Job6"
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
