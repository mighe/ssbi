package it.mighe.ssbi.instruction

import org.scalatest.{Matchers, FlatSpec}
import it.mighe.ssbi.Tape
import it.mighe.ssbi.instructions.{SetValueInstruction, AdjustValueInstruction}

class SetValueInstructionSpec extends FlatSpec with Matchers {

  def fixture = new {
    val tape = new Tape
    val instruction = new SetValueInstruction(7)
  }


  it should "return next instruction" in {
    val f = fixture
    val next = new SetValueInstruction(-4)
    f.instruction.next = next

    f.instruction.execute(f.tape) should be(next)
  }

  it should "increment tape at current position" in {
    val f = fixture

    f.instruction.execute(f.tape)
    f.tape.current should be(7)

    new SetValueInstruction(-3).execute(f.tape)

    f.tape.current should be(-3)
  }

}
