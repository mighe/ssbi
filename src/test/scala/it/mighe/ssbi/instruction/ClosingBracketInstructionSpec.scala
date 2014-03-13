package it.mighe.ssbi.instruction

import org.scalatest.{Matchers, FlatSpec}
import it.mighe.ssbi.Tape
import it.mighe.ssbi.instructions.{OpeningBracketInstruction, ClosingBracketInstruction}

class ClosingBracketInstructionSpec extends FlatSpec with Matchers {

  def fixture = new {
    val tape = new Tape
    val instruction = new ClosingBracketInstruction()
    val matching = new OpeningBracketInstruction()
    instruction.matching = matching
  }


  it should "increment program counter if current value is zero" in {
    val f = fixture
    val next = new ClosingBracketInstruction()
    f.instruction.next = next

    val newProgramCounter = f.instruction.execute(f.tape)
    newProgramCounter should be(next)
  }

  it should "jump to matching opening if current value is not zero" in {
    val f = fixture
    f.tape.increment()

    f.instruction.execute(f.tape) should be(f.matching)
  }

}
