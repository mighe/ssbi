package it.mighe.ssbi.instruction

import org.scalatest.{Matchers, FlatSpec}
import it.mighe.ssbi.Tape
import it.mighe.ssbi.instructions.{ClosingBracketInstruction, OpeningBracketInstruction}

class OpeningBracketInstructionSpec extends FlatSpec with Matchers {

  def fixture = new {
    val tape = new Tape
    val instruction = new OpeningBracketInstruction(15)
    val matching = new ClosingBracketInstruction(0)
    instruction.matching = matching
  }

  it should "increment program counter if current value is not zero with program counter" in {
    val f = fixture
    f.tape.increment()

    val newProgramCounter = f.instruction.execute(f.tape, 0)
    newProgramCounter should be(1)
  }

  it should "jump to matching closing if current value is zero with program counter" in {
    val f = fixture

    val newProgramCounter = f.instruction.execute(f.tape, 0)
    newProgramCounter should be(15)
  }

  it should "increment program counter if current value is not zero" in {
    val f = fixture
    val next = new OpeningBracketInstruction(25)
    f.instruction.next = next
    f.tape.increment()

    val newProgramCounter = f.instruction.execute(f.tape)
    newProgramCounter should be(next)
  }

  it should "jump to matching closing if current value is zero" in {
    val f = fixture

    val newProgramCounter = f.instruction.execute(f.tape)
    newProgramCounter should be(f.matching)
  }

}
