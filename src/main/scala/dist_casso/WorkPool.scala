package dist_casso

import calculation._

sealed trait WorkStatus

case object New extends WorkStatus

case object Running extends WorkStatus

case object Finished extends WorkStatus

class WorkPool(val calculation: AbstractCalculation, val partitioning: AbstractCalculation) {
  var partitions: Iterator[Seq[Long]] = List().toIterator
  var partitioningWorkStatus: WorkStatus = New
  var workDone = 0
  var workSize = -1

  def markAsDone = {
    workDone += 1
  }

  def setPartitions(partitions: Seq[Seq[Long]]) = {
    this.partitions = partitions.toIterator
    workSize = partitions.size
    partitioningWorkStatus = Finished
  }

  def getWork(): (AbstractCalculation, AbstractInput) = {
    partitioningWorkStatus match {
      case New => {
        partitioningWork
      }
      case Running => {
        throw new IllegalStateException("partitioning is running")
      }
      case Finished => {
        assignWork
      }
    }
  }

  def partitioningWork: (AbstractCalculation, AbstractInput) = {
    partitioningWorkStatus = Running
    (partitioning, EmptyInput)
  }

  def assignWork: (AbstractCalculation, AbstractInput) = {
    (calculation, getPartition)
  }

  def getPartition: AbstractInput = {
    new MultipleVertexInput(partitions.next())
  }

  def isWorkAvailable: Boolean = {
    partitioningWorkStatus == New ||
      partitions.hasNext
  }

  def isWorkFinished: Boolean = {
    partitioningWorkStatus == Finished &&
      !partitions.hasNext &&
      workDone == workSize
  }
}
