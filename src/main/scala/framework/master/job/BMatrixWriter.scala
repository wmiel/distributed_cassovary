package framework.master.job

import calculations.BMatrix

class BMatrixWriter(bMatrix: BMatrix) {
  def save = {
    var totalNumberOfNodes = 0
    var totalNumberOfEdges = 0

    print("#l-shell size\tnumber of members in l-shell\tnumber of nodes\n")
    print("#B-Matrix START\n")

    bMatrix.toOutputFormat.foreach { case (shellSize, numberOfMembers, numberOfNodes) =>
      if(shellSize == 0) {
        totalNumberOfNodes = numberOfNodes
      }
      if(shellSize == 1) {
        totalNumberOfEdges += numberOfNodes * numberOfMembers
      }
      print("%d\t%d\t%d\n".format(shellSize, numberOfMembers, numberOfNodes))
    }

    print("#number of nodes: %d\n".format(totalNumberOfNodes))
    print("#number of edges: %d\n".format(totalNumberOfEdges / 2))
    print("#B-Matrix END\n")
  }
}
