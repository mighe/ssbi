package it.mighe.ssbi.instruction

import org.scalatest.{Matchers, FlatSpec}
import it.mighe.ssbi.Tape
import it.mighe.ssbi.instructions.ClosingBracketInstruction

class ClosingBracketInstructionSpec extends FlatSpec with Matchers {

  def fixture = new {
    val tape = new Tape
    val instruction = new ClosingBracketInstruction(10)
  }

  it should "increment program counter if current value is zero" in {
    val f = fixture

    val newProgramCounter = f.instruction.execute(f.tape, 0)
    newProgramCounter should be(1)
  }

  it should "jump to matching opening if current value is not zero" in {
    val f = fixture
    f.tape.increment()

    val newProgramCounter = f.instruction.execute(f.tape, 0)
    newProgramCounter should be(10)
  }

}
