package it.mighe.ssbi.instruction

import org.scalatest.{Matchers, FlatSpec}
import it.mighe.ssbi.Tape
import it.mighe.ssbi.instructions.AdjustPointerInstruction

class AdjustPointerInstructionSpec extends FlatSpec with Matchers {

  def fixture = new {
    val tape = new Tape
    val instruction = new AdjustPointerInstruction(4)
  }

  it should "return next instruction" in {
    val f = fixture
    val next = new AdjustPointerInstruction(3)
    f.instruction.next = next

    f.instruction.execute(f.tape) should be(next)
  }

  it should "increment tape pointer" in {
    val f = fixture

    f.instruction.execute(f.tape)
    f.tape.pointerPosition should be(4)

    new AdjustPointerInstruction(-2).execute(f.tape)

    f.tape.pointerPosition should be(2)
  }

}
