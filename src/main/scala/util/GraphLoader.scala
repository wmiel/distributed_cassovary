package util

import com.twitter.cassovary.graph.{StoredGraphDir, DirectedGraph, Node}
import com.twitter.cassovary.util.io.{ListOfEdgesGraphReader, AdjacencyListGraphReader}

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


trait GraphLoader {
  def separatorInt = {
    ' '.toInt
  }

  def readGraph(path: String, filename: String, adjacencyList: Boolean): DirectedGraph[Node] = {
    if (adjacencyList) {
      AdjacencyListGraphReader.forIntIds(path, filename).toArrayBasedDirectedGraph()
    } else {
      val sep = separatorInt.toChar
      printf("Using Character (%d in Int) as separator\n", sep.toInt)
      ListOfEdgesGraphReader.forIntIds(path, filename, graphDir = StoredGraphDir.BothInOut,
        separator = sep).toSharedArrayBasedDirectedGraph(forceSparseRepr = None)
      //separator = sep).toArrayBasedDirectedGraph(neighborsSortingStrategy = LeaveUnsorted,
      //      forceSparseRepr = None)
    }
  }
}
