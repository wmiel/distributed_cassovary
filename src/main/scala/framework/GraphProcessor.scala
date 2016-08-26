package framework

import akka.actor.{ActorSystem, Props}
import calculations.{EdgeBMatrixCalculation, OutgoingDegreePerVertexCalculation, RandomPartitionsCalculation, VertexBMatrixCalculation}
import framework.master.Master
import framework.master.job.JobDefinition

object GraphProcessor extends App with ConfigLoader {
  run

  def jobs(setup: Map[String, String]) = {
    List(
      new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/ca-GrQc.txt.gz", "calculation_executors_per_worker" -> "4"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_1_of_114",
        "Job_7_2_1_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/ca-GrQc.txt.gz", "calculation_executors_per_worker" -> "2"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_2_of_114",
        "Job_7_2_2_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/ca-GrQc.txt.gz", "calculation_executors_per_worker" -> "1"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_3_of_114",
        "Job_7_2_3_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella08.txt.gz", "calculation_executors_per_worker" -> "4"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_4_of_114",
        "Job_7_2_4_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella08.txt.gz", "calculation_executors_per_worker" -> "2"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_5_of_114",
        "Job_7_2_5_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella08.txt.gz", "calculation_executors_per_worker" -> "1"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_6_of_114",
        "Job_7_2_6_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella09.txt.gz", "calculation_executors_per_worker" -> "4"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_7_of_114",
        "Job_7_2_7_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella09.txt.gz", "calculation_executors_per_worker" -> "2"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_8_of_114",
        "Job_7_2_8_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella09.txt.gz", "calculation_executors_per_worker" -> "1"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_9_of_114",
        "Job_7_2_9_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella06.txt.gz", "calculation_executors_per_worker" -> "4"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_10_of_114",
        "Job_7_2_10_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella06.txt.gz", "calculation_executors_per_worker" -> "2"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_11_of_114",
        "Job_7_2_11_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella06.txt.gz", "calculation_executors_per_worker" -> "1"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_12_of_114",
        "Job_7_2_12_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella05.txt.gz", "calculation_executors_per_worker" -> "4"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_13_of_114",
        "Job_7_2_13_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella05.txt.gz", "calculation_executors_per_worker" -> "2"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_14_of_114",
        "Job_7_2_14_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella05.txt.gz", "calculation_executors_per_worker" -> "1"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_15_of_114",
        "Job_7_2_15_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/facebook_combined.txt.gz", "calculation_executors_per_worker" -> "4"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_16_of_114",
        "Job_7_2_16_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/facebook_combined.txt.gz", "calculation_executors_per_worker" -> "2"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_17_of_114",
        "Job_7_2_17_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/facebook_combined.txt.gz", "calculation_executors_per_worker" -> "1"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_18_of_114",
        "Job_7_2_18_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/ca-HepTh.txt.gz", "calculation_executors_per_worker" -> "4"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_19_of_114",
        "Job_7_2_19_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/ca-HepTh.txt.gz", "calculation_executors_per_worker" -> "2"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_20_of_114",
        "Job_7_2_20_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/ca-HepTh.txt.gz", "calculation_executors_per_worker" -> "1"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_21_of_114",
        "Job_7_2_21_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella04.txt.gz", "calculation_executors_per_worker" -> "4"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_22_of_114",
        "Job_7_2_22_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella04.txt.gz", "calculation_executors_per_worker" -> "2"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_23_of_114",
        "Job_7_2_23_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella04.txt.gz", "calculation_executors_per_worker" -> "1"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_24_of_114",
        "Job_7_2_24_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/wiki-Vote.txt.gz", "calculation_executors_per_worker" -> "4"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_25_of_114",
        "Job_7_2_25_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/wiki-Vote.txt.gz", "calculation_executors_per_worker" -> "2"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_26_of_114",
        "Job_7_2_26_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/wiki-Vote.txt.gz", "calculation_executors_per_worker" -> "1"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_27_of_114",
        "Job_7_2_27_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/ca-HepPh.txt.gz", "calculation_executors_per_worker" -> "4"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_28_of_114",
        "Job_7_2_28_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/ca-HepPh.txt.gz", "calculation_executors_per_worker" -> "2"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_29_of_114",
        "Job_7_2_29_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/ca-HepPh.txt.gz", "calculation_executors_per_worker" -> "1"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_30_of_114",
        "Job_7_2_30_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/cit-HepTh.txt.gz", "calculation_executors_per_worker" -> "4"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_31_of_114",
        "Job_7_2_31_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/cit-HepTh.txt.gz", "calculation_executors_per_worker" -> "2"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_32_of_114",
        "Job_7_2_32_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/cit-HepTh.txt.gz", "calculation_executors_per_worker" -> "1"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_33_of_114",
        "Job_7_2_33_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella25.txt.gz", "calculation_executors_per_worker" -> "4"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_34_of_114",
        "Job_7_2_34_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella25.txt.gz", "calculation_executors_per_worker" -> "2"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_35_of_114",
        "Job_7_2_35_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella25.txt.gz", "calculation_executors_per_worker" -> "1"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_36_of_114",
        "Job_7_2_36_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella24.txt.gz", "calculation_executors_per_worker" -> "4"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_37_of_114",
        "Job_7_2_37_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella24.txt.gz", "calculation_executors_per_worker" -> "2"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_38_of_114",
        "Job_7_2_38_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella24.txt.gz", "calculation_executors_per_worker" -> "1"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_39_of_114",
        "Job_7_2_39_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/ca-CondMat.txt.gz", "calculation_executors_per_worker" -> "4"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_40_of_114",
        "Job_7_2_40_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/ca-CondMat.txt.gz", "calculation_executors_per_worker" -> "2"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_41_of_114",
        "Job_7_2_41_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/ca-CondMat.txt.gz", "calculation_executors_per_worker" -> "1"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_42_of_114",
        "Job_7_2_42_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella30.txt.gz", "calculation_executors_per_worker" -> "4"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_43_of_114",
        "Job_7_2_43_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella30.txt.gz", "calculation_executors_per_worker" -> "2"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_44_of_114",
        "Job_7_2_44_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella30.txt.gz", "calculation_executors_per_worker" -> "1"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_45_of_114",
        "Job_7_2_45_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/ca-AstroPh.txt.gz", "calculation_executors_per_worker" -> "4"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_46_of_114",
        "Job_7_2_46_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/ca-AstroPh.txt.gz", "calculation_executors_per_worker" -> "2"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_47_of_114",
        "Job_7_2_47_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/ca-AstroPh.txt.gz", "calculation_executors_per_worker" -> "1"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_48_of_114",
        "Job_7_2_48_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/email-Enron.txt.gz", "calculation_executors_per_worker" -> "4"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_49_of_114",
        "Job_7_2_49_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/email-Enron.txt.gz", "calculation_executors_per_worker" -> "2"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_50_of_114",
        "Job_7_2_50_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/email-Enron.txt.gz", "calculation_executors_per_worker" -> "1"),
        RandomPartitionsCalculation(250),
        VertexBMatrixCalculation,
        "Job_7_2_51_of_114",
        "Job_7_2_51_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella31.txt.gz", "calculation_executors_per_worker" -> "4"),
        RandomPartitionsCalculation(100),
        VertexBMatrixCalculation,
        "Job_7_2_52_of_114",
        "Job_7_2_52_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella31.txt.gz", "calculation_executors_per_worker" -> "2"),
        RandomPartitionsCalculation(100),
        VertexBMatrixCalculation,
        "Job_7_2_53_of_114",
        "Job_7_2_53_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella31.txt.gz", "calculation_executors_per_worker" -> "1"),
        RandomPartitionsCalculation(100),
        VertexBMatrixCalculation,
        "Job_7_2_54_of_114",
        "Job_7_2_54_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/loc-brightkite_edges.txt.gz", "calculation_executors_per_worker" -> "4"),
        RandomPartitionsCalculation(100),
        VertexBMatrixCalculation,
        "Job_7_2_55_of_114",
        "Job_7_2_55_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/loc-brightkite_edges.txt.gz", "calculation_executors_per_worker" -> "2"),
        RandomPartitionsCalculation(100),
        VertexBMatrixCalculation,
        "Job_7_2_56_of_114",
        "Job_7_2_56_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/loc-brightkite_edges.txt.gz", "calculation_executors_per_worker" -> "1"),
        RandomPartitionsCalculation(100),
        VertexBMatrixCalculation,
        "Job_7_2_57_of_114",
        "Job_7_2_57_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/ca-GrQc.txt.gz", "calculation_executors_per_worker" -> "4"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_58_of_114",
        "Job_7_2_58_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/ca-GrQc.txt.gz", "calculation_executors_per_worker" -> "2"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_59_of_114",
        "Job_7_2_59_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/ca-GrQc.txt.gz", "calculation_executors_per_worker" -> "1"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_60_of_114",
        "Job_7_2_60_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella08.txt.gz", "calculation_executors_per_worker" -> "4"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_61_of_114",
        "Job_7_2_61_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella08.txt.gz", "calculation_executors_per_worker" -> "2"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_62_of_114",
        "Job_7_2_62_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella08.txt.gz", "calculation_executors_per_worker" -> "1"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_63_of_114",
        "Job_7_2_63_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella09.txt.gz", "calculation_executors_per_worker" -> "4"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_64_of_114",
        "Job_7_2_64_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella09.txt.gz", "calculation_executors_per_worker" -> "2"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_65_of_114",
        "Job_7_2_65_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella09.txt.gz", "calculation_executors_per_worker" -> "1"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_66_of_114",
        "Job_7_2_66_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella06.txt.gz", "calculation_executors_per_worker" -> "4"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_67_of_114",
        "Job_7_2_67_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella06.txt.gz", "calculation_executors_per_worker" -> "2"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_68_of_114",
        "Job_7_2_68_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella06.txt.gz", "calculation_executors_per_worker" -> "1"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_69_of_114",
        "Job_7_2_69_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella05.txt.gz", "calculation_executors_per_worker" -> "4"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_70_of_114",
        "Job_7_2_70_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella05.txt.gz", "calculation_executors_per_worker" -> "2"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_71_of_114",
        "Job_7_2_71_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella05.txt.gz", "calculation_executors_per_worker" -> "1"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_72_of_114",
        "Job_7_2_72_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/facebook_combined.txt.gz", "calculation_executors_per_worker" -> "4"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_73_of_114",
        "Job_7_2_73_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/facebook_combined.txt.gz", "calculation_executors_per_worker" -> "2"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_74_of_114",
        "Job_7_2_74_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/facebook_combined.txt.gz", "calculation_executors_per_worker" -> "1"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_75_of_114",
        "Job_7_2_75_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/ca-HepTh.txt.gz", "calculation_executors_per_worker" -> "4"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_76_of_114",
        "Job_7_2_76_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/ca-HepTh.txt.gz", "calculation_executors_per_worker" -> "2"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_77_of_114",
        "Job_7_2_77_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/ca-HepTh.txt.gz", "calculation_executors_per_worker" -> "1"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_78_of_114",
        "Job_7_2_78_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella04.txt.gz", "calculation_executors_per_worker" -> "4"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_79_of_114",
        "Job_7_2_79_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella04.txt.gz", "calculation_executors_per_worker" -> "2"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_80_of_114",
        "Job_7_2_80_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella04.txt.gz", "calculation_executors_per_worker" -> "1"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_81_of_114",
        "Job_7_2_81_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/wiki-Vote.txt.gz", "calculation_executors_per_worker" -> "4"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_82_of_114",
        "Job_7_2_82_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/wiki-Vote.txt.gz", "calculation_executors_per_worker" -> "2"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_83_of_114",
        "Job_7_2_83_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/wiki-Vote.txt.gz", "calculation_executors_per_worker" -> "1"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_84_of_114",
        "Job_7_2_84_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/ca-HepPh.txt.gz", "calculation_executors_per_worker" -> "4"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_85_of_114",
        "Job_7_2_85_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/ca-HepPh.txt.gz", "calculation_executors_per_worker" -> "2"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_86_of_114",
        "Job_7_2_86_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/ca-HepPh.txt.gz", "calculation_executors_per_worker" -> "1"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_87_of_114",
        "Job_7_2_87_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/cit-HepTh.txt.gz", "calculation_executors_per_worker" -> "4"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_88_of_114",
        "Job_7_2_88_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/cit-HepTh.txt.gz", "calculation_executors_per_worker" -> "2"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_89_of_114",
        "Job_7_2_89_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/cit-HepTh.txt.gz", "calculation_executors_per_worker" -> "1"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_90_of_114",
        "Job_7_2_90_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella25.txt.gz", "calculation_executors_per_worker" -> "4"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_91_of_114",
        "Job_7_2_91_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella25.txt.gz", "calculation_executors_per_worker" -> "2"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_92_of_114",
        "Job_7_2_92_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella25.txt.gz", "calculation_executors_per_worker" -> "1"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_93_of_114",
        "Job_7_2_93_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella24.txt.gz", "calculation_executors_per_worker" -> "4"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_94_of_114",
        "Job_7_2_94_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella24.txt.gz", "calculation_executors_per_worker" -> "2"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_95_of_114",
        "Job_7_2_95_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella24.txt.gz", "calculation_executors_per_worker" -> "1"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_96_of_114",
        "Job_7_2_96_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/ca-CondMat.txt.gz", "calculation_executors_per_worker" -> "4"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_97_of_114",
        "Job_7_2_97_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/ca-CondMat.txt.gz", "calculation_executors_per_worker" -> "2"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_98_of_114",
        "Job_7_2_98_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/ca-CondMat.txt.gz", "calculation_executors_per_worker" -> "1"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_99_of_114",
        "Job_7_2_99_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella30.txt.gz", "calculation_executors_per_worker" -> "4"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_100_of_114",
        "Job_7_2_100_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella30.txt.gz", "calculation_executors_per_worker" -> "2"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_101_of_114",
        "Job_7_2_101_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella30.txt.gz", "calculation_executors_per_worker" -> "1"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_102_of_114",
        "Job_7_2_102_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/ca-AstroPh.txt.gz", "calculation_executors_per_worker" -> "4"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_103_of_114",
        "Job_7_2_103_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/ca-AstroPh.txt.gz", "calculation_executors_per_worker" -> "2"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_104_of_114",
        "Job_7_2_104_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/ca-AstroPh.txt.gz", "calculation_executors_per_worker" -> "1"),
        RandomPartitionsCalculation(250),
        EdgeBMatrixCalculation,
        "Job_7_2_105_of_114",
        "Job_7_2_105_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/email-Enron.txt.gz", "calculation_executors_per_worker" -> "4"),
        RandomPartitionsCalculation(100),
        EdgeBMatrixCalculation,
        "Job_7_2_106_of_114",
        "Job_7_2_106_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/email-Enron.txt.gz", "calculation_executors_per_worker" -> "2"),
        RandomPartitionsCalculation(100),
        EdgeBMatrixCalculation,
        "Job_7_2_107_of_114",
        "Job_7_2_107_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/email-Enron.txt.gz", "calculation_executors_per_worker" -> "1"),
        RandomPartitionsCalculation(100),
        EdgeBMatrixCalculation,
        "Job_7_2_108_of_114",
        "Job_7_2_108_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella31.txt.gz", "calculation_executors_per_worker" -> "4"),
        RandomPartitionsCalculation(100),
        EdgeBMatrixCalculation,
        "Job_7_2_109_of_114",
        "Job_7_2_109_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella31.txt.gz", "calculation_executors_per_worker" -> "2"),
        RandomPartitionsCalculation(100),
        EdgeBMatrixCalculation,
        "Job_7_2_110_of_114",
        "Job_7_2_110_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/p2p-Gnutella31.txt.gz", "calculation_executors_per_worker" -> "1"),
        RandomPartitionsCalculation(100),
        EdgeBMatrixCalculation,
        "Job_7_2_111_of_114",
        "Job_7_2_111_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/loc-brightkite_edges.txt.gz", "calculation_executors_per_worker" -> "4"),
        RandomPartitionsCalculation(100),
        EdgeBMatrixCalculation,
        "Job_7_2_112_of_114",
        "Job_7_2_112_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/loc-brightkite_edges.txt.gz", "calculation_executors_per_worker" -> "2"),
        RandomPartitionsCalculation(100),
        EdgeBMatrixCalculation,
        "Job_7_2_113_of_114",
        "Job_7_2_113_of_114"
      )
      ,new JobDefinition(
        setup ++ Map("graph_url" -> "http://snap.stanford.edu/data/loc-brightkite_edges.txt.gz", "calculation_executors_per_worker" -> "1"),
        RandomPartitionsCalculation(100),
        EdgeBMatrixCalculation,
        "Job_7_2_114_of_114",
        "Job_7_2_114_of_114"
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
