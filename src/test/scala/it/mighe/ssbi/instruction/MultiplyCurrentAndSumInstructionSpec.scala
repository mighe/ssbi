package it.mighe.ssbi.instruction

import org.scalatest.{BeforeAndAfter, Matchers, FlatSpec}
import it.mighe.ssbi.Tape
import it.mighe.ssbi.instructions.MultiplyCurrentAndSumInstruction

class MultiplyCurrentAndSumInstructionSpec extends FlatSpec with Matchers with BeforeAndAfter {

  var tape: Tape = _
  var instruction: MultiplyCurrentAndSumInstruction = _

  before {
    tape = new Tape
    instruction = new MultiplyCurrentAndSumInstruction(3, 4)
  }

  it should "return next instruction" in {
    val next = new MultiplyCurrentAndSumInstruction(-1, -2)
    instruction.next = next

    instruction.execute(tape) should be(next)
  }

  it should "sums multiplied current to value at offset" in {
    tape.current = 2
    tape.setAt(10, 4)

    instruction.execute(tape)
    tape.current should be(2)
    tape.at(4) should be(16)
  }

  it should "do nothing if current is zero" in {
    instruction = new MultiplyCurrentAndSumInstruction(-3, -3)

    instruction.execute(tape)
  }

}
