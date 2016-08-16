package framework.master

import calculations._

sealed trait WorkStatus

case object New extends WorkStatus

case object Running extends WorkStatus

case object Finished extends WorkStatus

class WorkPool(val calculation: AbstractCalculation) {
  var partitions: Iterator[Seq[Int]] = List().toIterator
  var workDone = 0
  var workSize = -1
  var assignedPartitions = 0

  def markAsDone = {
    workDone += 1
  }

  def setPartitions(partitions: Seq[Seq[Int]]) = {
    this.partitions = partitions.toIterator
    workSize = partitions.size
  }

  def getWork() = {
    assignWork
  }

  def assignWork: (AbstractCalculation, Seq[Int], Int) = {
    (calculation, getPartition, assignedPartitions)
  }

  def getPartition: Seq[Int] = {
    assignedPartitions += 1
    partitions.next()
  }

  def isWorkAvailable: Boolean = {
    partitions.hasNext
  }

  def isWorkFinished: Boolean = {
    !partitions.hasNext &&
      workDone == workSize
  }
}
