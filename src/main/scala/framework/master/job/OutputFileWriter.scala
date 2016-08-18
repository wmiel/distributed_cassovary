package framework.master.job

import java.io.{BufferedWriter, File, FileWriter}

trait OutputFileWriter {
  val fileNamePrefix: String
  val fileNameSuffix: String

  def writeContent(bw: BufferedWriter)

  def save = {
    val bw = openFile
    writeContent(bw)
    closeFile(bw)
  }

  private

  def openFile = {
    val dir = new File("output")
    val file = new File(dir, fileNamePrefix + fileNameSuffix + ".out")
    dir.mkdirs()
    new BufferedWriter(new FileWriter(file))
  }

  def closeFile(bw: BufferedWriter) = {
    bw.close()
  }
}
