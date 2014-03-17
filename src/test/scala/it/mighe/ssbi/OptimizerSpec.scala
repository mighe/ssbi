package it.mighe.ssbi

import org.scalatest.{Matchers, FlatSpec}
import it.mighe.ssbi.instructions._
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream
import java.io.ByteArrayInputStream
import CustomMatchers._

class OptimizerSpec extends FlatSpec with Matchers {

  def fixture = new {
    val parser = new Parser(new ByteOutputStream(), new ByteArrayInputStream(Array()))
    val optimizer = new Optimizer
  }

  def parseAndOptimize(source: String) = {
    val instructions = fixture.parser.parse(source)
    fixture.optimizer.optimize(instructions)
  }

  it can "optimize an empty program" in {
    val instructions = fixture.optimizer.optimize(Array())
    instructions should equal(Array[Instruction]())
  }

  it should "not modify input array" in {
    val source = "+++ +++ +++ , [-] >> . "
    val instructions = fixture.parser.parse(source)

    fixture.optimizer.optimize(instructions)

    instructions should have length 16
  }

  it should "optimize consecutive increments" in {
    val optimized = parseAndOptimize("+++ +++ +++")

    optimized should have length 1
    optimized(0) should beAValueInstructionWithOffset(9)
  }

  it should "optimize consecutive decrements" in {
    val optimized = parseAndOptimize("- . ---")

    optimized should have length 3
    optimized(0) should beAValueInstructionWithOffset(-1)
    optimized(1) shouldBe a [WriteInstruction]
    optimized(2) should beAValueInstructionWithOffset(-3)
  }

  it should "optimize consecutive right shifts" in {
    val optimized = parseAndOptimize(">>> >>>")

    optimized should have length 1
    optimized(0) should beAPointerInstructionWithOffset(6)
  }

  it should "optimize consecutive left shifts" in {
    val optimized = parseAndOptimize(">>> , <<")

    optimized should have length 3
    optimized(0) should beAPointerInstructionWithOffset(3)
    optimized(1) shouldBe a [ReadInstruction]
    optimized(2) should beAPointerInstructionWithOffset(-2)
  }

  it should "optimize zeroing" in {
    val optimized = parseAndOptimize(", [-] , [-] .")

    optimized should have length 5
    optimized(0) shouldBe a [ReadInstruction]
    optimized(1) should beAValueInstructionWithValue(0)
    optimized(2) shouldBe a [ReadInstruction]
    optimized(3) should beAValueInstructionWithValue(0)
    optimized(4) shouldBe a [WriteInstruction]
  }

  it should "not optimize empty loops" in {
    val optimized = parseAndOptimize("[]")

    optimized should have length 2
    optimized(0) shouldBe a [OpeningBracketInstruction]
    optimized(1) shouldBe a [ClosingBracketInstruction]
  }

}
