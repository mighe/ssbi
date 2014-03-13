package it.mighe.ssbi.instruction

import org.scalatest.{Matchers, FlatSpec}
import it.mighe.ssbi.Tape
import it.mighe.ssbi.instructions.{ClosingBracketInstruction, OpeningBracketInstruction}

class OpeningBracketInstructionSpec extends FlatSpec with Matchers {

  def fixture = new {
    val tape = new Tape
    val instruction = new OpeningBracketInstruction()
    val matching = new ClosingBracketInstruction()
    instruction.matching = matching
  }

  it should "increment program counter if current value is not zero" in {
    val f = fixture
    val next = new OpeningBracketInstruction()
    f.instruction.next = next
    f.tape.increment()

    f.instruction.execute(f.tape) should be(next)
  }

  it should "jump to matching closing if current value is zero" in {
    val f = fixture

    val newProgramCounter = f.instruction.execute(f.tape)
    newProgramCounter should be(f.matching)
  }

}
