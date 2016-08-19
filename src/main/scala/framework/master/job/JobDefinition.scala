package framework.master.job

import calculations.AbstractCalculation

class JobDefinition(setup: Map[String, String],
                    partitioning: AbstractCalculation,
                    calculation: AbstractCalculation,
                    name: String,
                    outputFileNamePrefix: String
                   ) {
  val id = java.util.UUID.randomUUID.toString

  def getJobName = name + "_" + id

  def getName = name

  def getSetup = setup

  def getPartitioning = partitioning

  def getCalculation = calculation

  def getOutputFileNamePrefix = outputFileNamePrefix

  override def toString = {
    """|Job Description:
      | id: %s
      | name: %s
      | parititioning: %s
      | calculation: %s
      | output file name prefix: %s
      | setup:
%s
    """.format(
      id,
      name,
      partitioning.getClass.getSimpleName,
      calculation.getClass.getSimpleName,
      outputFileNamePrefix,
      formattedSetup
    ).stripMargin
  }

  private

  def formattedSetup = {
    setup.map { case (key, value) => "|  %s: %s".format(key, value) }.mkString("\n")
  }
}
