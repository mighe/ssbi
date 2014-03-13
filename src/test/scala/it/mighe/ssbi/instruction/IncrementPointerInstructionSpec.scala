package it.mighe.ssbi.instruction

import org.scalatest.{Matchers, FlatSpec}
import it.mighe.ssbi.Tape
import it.mighe.ssbi.instructions.IncrementPointerInstruction

class IncrementPointerInstructionSpec extends FlatSpec with Matchers {

  def fixture = new {
    val tape = new Tape
    val instruction = new IncrementPointerInstruction
  }

  it should "return next instruction" in {
    val f = fixture
    val next = new IncrementPointerInstruction
    f.instruction.next = next

    f.instruction.execute(f.tape) should be(next)
  }

  it should "increment tape pointer" in {
    val f = fixture

    f.instruction.execute(f.tape)
    f.tape.pointerPosition should be(1)
  }

}
