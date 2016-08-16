package framework.master.job

import calculations.AbstractCalculation

class JobDefinition(setup: Map[String, String],
                    partitioning: AbstractCalculation,
                    calculation: AbstractCalculation,
                    name: String) {
  def getName = name
  def getSetup = setup
  def getPartitioning = partitioning
  def getCalculation = calculation
}
