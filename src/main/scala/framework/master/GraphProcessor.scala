package framework.master

import aggregations.VertexBMatrixAggregation
import akka.actor.{ActorSystem, Props}
import calculations.{DistanceBasedCalculation, RandomPartitionsCalculation}
import com.typesafe.config.ConfigFactory
import framework.Logger

object GraphProcessor extends App {
  run

  def run: Unit = {
    val system = ActorSystem("GraphProcessing", ConfigFactory.load("master"))

    // Set correct results handler (depending on selected algorithm)
    val listener = system.actorOf(Props[Listener], name = "listener")
    val logger = system.actorOf(Props[Logger], name = "logger")

    val setup: Map[String, String] = Map(
      "graph_url" -> "http://snap.stanford.edu/data/facebook_combined.txt.gz", // "http://snap.stanford.edu/data/cit-HepPh.txt.gz",
      "cache_dir" -> "cache/test",
      "random_cache_dir" -> "true",
      "transform_to_undirected" -> "false",
      "calculation_executors_per_worker" -> "4"
    )
    // Set up master and specify work to do
    val master = system.actorOf(
      Props(
        new Master(
          listener,
          logger,
          setup,
          DistanceBasedCalculation(List(VertexBMatrixAggregation)),
          RandomPartitionsCalculation(300)
        )
      ),
      name = "master"
    )
  }
}
