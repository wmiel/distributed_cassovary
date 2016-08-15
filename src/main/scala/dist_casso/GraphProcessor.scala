package dist_casso

import aggregations.VertexBMatrixAggregation
import akka.actor.{ActorSystem, Props}
import calculation.{DistanceBasedCalculation, RandomPartitionsCalculation}
import com.typesafe.config.ConfigFactory

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
      "transform_to_undirected" -> "false"
    )
    // Set up master and run selected algorithm
    val master = system.actorOf(
      Props(
        new Master(
          listener,
          logger,
          setup,
          DistanceBasedCalculation(List(VertexBMatrixAggregation)),
          RandomPartitionsCalculation(1500)
        )
      ),
      name = "master"
    )
    master ! Calculate
  }
}
