package framework.master.job

import java.io.BufferedWriter

import calculations.BMatrix

class BMatrixWriter(bMatrix: BMatrix,
                    override val fileNamePrefix: String,
                    override val fileNameSuffix: String) extends OutputFileWriter {

  override def writeContent(bw: BufferedWriter) = {
    var totalNumberOfNodes = 0
    var totalNumberOfEdges = 0

    bw.write("#l-shell size\tnumber of members in l-shell\tnumber of nodes\n")
    bw.write("#B-Matrix START\n")

    bMatrix.toOutputFormat.foreach { case (shellSize, numberOfMembers, numberOfNodes) =>
      if (shellSize == 0) {
        totalNumberOfNodes = numberOfNodes
      }
      if (shellSize == 1) {
        totalNumberOfEdges += numberOfNodes * numberOfMembers
      }
      bw.write("%d\t%d\t%d\n".format(shellSize, numberOfMembers, numberOfNodes))
    }

    bw.write("#number of nodes: %d\n".format(totalNumberOfNodes))
    bw.write("#number of edges: %d\n".format(totalNumberOfEdges))
    bw.write("#B-Matrix END\n")
  }
}
