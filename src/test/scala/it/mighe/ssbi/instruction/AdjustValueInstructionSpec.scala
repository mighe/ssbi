package it.mighe.ssbi.instruction

import org.scalatest.{Matchers, FlatSpec}
import it.mighe.ssbi.Tape
import it.mighe.ssbi.instructions.AdjustValueInstruction

class AdjustValueInstructionSpec extends FlatSpec with Matchers {

  def fixture = new {
    val tape = new Tape
    val instruction = new AdjustValueInstruction(7)
  }


  it should "return next instruction" in {
    val f = fixture
    val next = new AdjustValueInstruction(-4)
    f.instruction.next = next

    f.instruction.execute(f.tape) should be(next)
  }

  it should "increment tape at current position" in {
    val f = fixture

    f.instruction.execute(f.tape)
    f.tape.current should be(7)

    new AdjustValueInstruction(-3).execute(f.tape)

    f.tape.current should be(4)
  }

}
