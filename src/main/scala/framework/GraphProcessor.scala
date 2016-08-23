package framework

import akka.actor.{ActorSystem, Props}
import calculations.{OutgoingDegreePerVertexCalculation, RandomPartitionsCalculation, VertexBMatrixCalculation}
import framework.master.Master
import framework.master.job.JobDefinition

object GraphProcessor extends App with ConfigLoader {
  run

  def jobs(setup: Map[String, String]) = {
    List(
      new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/email-EuAll.txt.gz",
          "calculation_executors_per_worker" -> "1"),
        RandomPartitionsCalculation(250),
        OutgoingDegreePerVertexCalculation,
        "Job_4_1_of_1",
        "Job_4_1_of_1"
      ),
      new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/email-EuAll.txt.gz", "calculation_executors_per_worker" -> "1"),
        RandomPartitionsCalculation(250),
        OutgoingDegreePerVertexCalculation,
        "Job_3_23_of_29",
        "Job_3_23_of_29"
      )
      , new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/cit-HepTh.txt.gz", "calculation_executors_per_worker" -> "1"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_3_24_of_29",
        "Job_3_24_of_29"
      )
      , new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/cit-HepTh.txt.gz", "calculation_executors_per_worker" -> "2"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_3_25_of_29",
        "Job_3_25_of_29"
      )
      , new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/cit-HepTh.txt.gz", "calculation_executors_per_worker" -> "4"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_3_26_of_29",
        "Job_3_26_of_29"
      )
      , new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/email-EuAll.txt.gz", "calculation_executors_per_worker" -> "1"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_3_27_of_29",
        "Job_3_27_of_29"
      )
      , new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/email-EuAll.txt.gz", "calculation_executors_per_worker" -> "2"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_3_28_of_29",
        "Job_3_28_of_29"
      )
      , new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/email-EuAll.txt.gz", "calculation_executors_per_worker" -> "4"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_3_29_of_29",
        "Job_3_29_of_29"
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
