package scala.calculations

import calculations.VertexBMatrix
import org.scalatest.{FunSpec, Matchers}

class VertexBMatrixSpec extends FunSpec with Matchers {
  describe("VertexBMatrix") {
    it("Adds two vertices correctly") {
      assert(
        VertexBMatrix(Map((0, 1) -> 1, (1, 1) -> 2, (2, 1) -> 3, (1, 2) -> 4)) +
          VertexBMatrix(Map((0, 1) -> 3, (2, 2) -> 5, (2, 1) -> 3, (1, 2) -> 1))
          == VertexBMatrix(Map((0, 1) -> 4, (1, 1) -> 2, (2, 2) -> 5, (2, 1) -> 6, (1, 2) -> 5))
      )
    }
  }
}
