package framework.master.job

import java.io.BufferedWriter

import calculations.PerVertexValue

class PerVertexWriter(perVertexOutgoingDegrees: PerVertexValue[Int],
                      override val fileNamePrefix: String,
                      override val fileNameSuffix: String) extends OutputFileWriter {

  override def writeContent(bw: BufferedWriter) = {
    bw.write("#Node id\tvalue\n")
    perVertexOutgoingDegrees.toOutputFormat.foreach { case (id, value) =>
      bw.write("%d\t%d\n".format(id, value))
    }
    bw.write("#FILE END\n")
    bw.close()
  }
}
