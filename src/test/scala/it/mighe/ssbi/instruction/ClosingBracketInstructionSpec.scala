package it.mighe.ssbi.instruction

import org.scalatest.{Matchers, FlatSpec}
import it.mighe.ssbi.Tape
import it.mighe.ssbi.instructions.{OpeningBracketInstruction, ClosingBracketInstruction}

class ClosingBracketInstructionSpec extends FlatSpec with Matchers {

  def fixture = new {
    val tape = new Tape
    val instruction = new ClosingBracketInstruction(10)
    val matching = new OpeningBracketInstruction(0)
    instruction.matching = matching
  }

  it should "increment program counter if current value is zero with program counter" in {
    val f = fixture

    val newProgramCounter = f.instruction.execute(f.tape, 0)
    newProgramCounter should be(1)
  }

  it should "jump to matching opening if current value is not zero with program counter" in {
    val f = fixture
    f.tape.increment()

    val newProgramCounter = f.instruction.execute(f.tape, 0)
    newProgramCounter should be(10)
  }

  it should "increment program counter if current value is zero" in {
    val f = fixture
    val next = new ClosingBracketInstruction(0)
    f.instruction.next = next

    val newProgramCounter = f.instruction.execute(f.tape)
    newProgramCounter should be(next)
  }

  it should "jump to matching opening if current value is not zero" in {
    val f = fixture
    f.tape.increment()

    val newProgramCounter = f.instruction.execute(f.tape)
    newProgramCounter should be(f.matching)
  }

}
