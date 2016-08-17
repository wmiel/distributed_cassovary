package util

import com.twitter.cassovary.graph.StoredGraphDir.StoredGraphDir
import com.twitter.cassovary.graph._
import com.twitter.cassovary.util.io.{AdjacencyListGraphReader, GraphReaderFromDirectory, ListOfEdgesGraphReader}

/*
 * Copyright 2014 Twitter, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * taken from: https://github.com/twitter/cassovary/blob/master/cassovary-benchmarks/src/main/scala/com/twitter/cassovary/PerformanceBenchmark.scala
 * modified
 */

import com.twitter.cassovary.graph.NeighborsSortingStrategy._


trait GraphLoader {
  var separatorInt: Int

  def readGraph(path: String, filename: String, adjacencyList: Boolean,
                graphDir: StoredGraphDir = StoredGraphDir.BothInOut,
                removeDuplicates: Boolean = true): GraphReaderFromDirectory[Int] = {
    if (adjacencyList) {
      AdjacencyListGraphReader.forIntIds(path, filename) // .toArrayBasedDirectedGraph()
    } else {
      val sep = separatorInt.toChar
      printf("Using Character (%d in Int) as separator\n", sep.toInt)
      printf("Reading %s from %s\n", filename, path)
      ListOfEdgesGraphReader.forIntIds(
        path,
        filename,
        graphDir = graphDir,
        separator = sep,
        removeDuplicates = removeDuplicates
      )
    }
  }

  def readGraphAsSharedArrayBasedGraph(path: String, filename: String, adjacencyList: Boolean,
                                       graphDir: StoredGraphDir = StoredGraphDir.BothInOut): DirectedGraph[Node] = {
    val rawGraph = readGraph(path, filename, adjacencyList, graphDir = graphDir)
    rawGraph.toSharedArrayBasedDirectedGraph(forceSparseRepr = None)
  }

  def readGraphAsArrayBasedGraph(path: String, filename: String, adjacencyList: Boolean,
                                 graphDir: StoredGraphDir = StoredGraphDir.BothInOut,
                                 neighborsSortingStrategy: NeighborsSortingStrategy = LeaveUnsorted): DirectedGraph[Node] = {
    val rawGraph = readGraph(path, filename, adjacencyList, graphDir = graphDir)
    rawGraph.toArrayBasedDirectedGraph(neighborsSortingStrategy, forceSparseRepr = None)
  }
}
