package algorithms

import org.scalatest.{FunSpec, Matchers}

class HistogramSpec extends FunSpec with Matchers {
  describe("Histogram") {
    it("counts occurencies of elements in a list") {
      val list = List((1, 1), (2, 1), (3, 1), (4, 2), (5, 3), (6, 3), (7, 4))
      val result = Histogram[(Int, Int)](_._2)(list)
      result.should(contain.theSameElementsAs(Map(1 -> 3, 2 -> 1, 3 -> 2, 4 -> 1)))
    }
  }
}
