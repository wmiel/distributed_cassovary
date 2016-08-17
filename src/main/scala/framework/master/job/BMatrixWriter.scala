package framework.master.job

import java.io.{BufferedWriter, File, FileWriter}

import calculations.BMatrix

class BMatrixWriter(bMatrix: BMatrix, fileNamePrefix: String, fileNameSuffix: String) {
  def save = {
    var totalNumberOfNodes = 0
    var totalNumberOfEdges = 0

    val dir = new File("output")
    val file = new File(dir, fileNamePrefix + fileNameSuffix + ".out")
    dir.mkdirs()
    val bw = new BufferedWriter(new FileWriter(file))

    bw.write("#l-shell size\tnumber of members in l-shell\tnumber of nodes\n")
    bw.write("#B-Matrix START\n")

    bMatrix.toOutputFormat.foreach { case (shellSize, numberOfMembers, numberOfNodes) =>
      if(shellSize == 0) {
        totalNumberOfNodes = numberOfNodes
      }
      if(shellSize == 1) {
        totalNumberOfEdges += numberOfNodes * numberOfMembers
      }
      bw.write("%d\t%d\t%d\n".format(shellSize, numberOfMembers, numberOfNodes))
    }

    bw.write("#number of nodes: %d\n".format(totalNumberOfNodes))
    bw.write("#number of edges: %d\n".format(totalNumberOfEdges))
    bw.write("#B-Matrix END\n")

    bw.close()
  }
}
