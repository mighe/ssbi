package it.mighe.ssbi.instruction

import org.scalatest.{Matchers, FlatSpec}
import it.mighe.ssbi.Tape
import it.mighe.ssbi.instructions.DecrementPointerInstruction

class DecrementPointerInstructionSpec extends FlatSpec with Matchers {

  def fixture = new {
    val tape = new Tape
    val instruction = new DecrementPointerInstruction
  }

  it should "increment program counter" in {
    val f = fixture

    val newProgramCounter = f.instruction.execute(f.tape, 10)
    newProgramCounter should be(11)
  }

  it should "decrement tape pointer with program counter" in {
    val f = fixture

    f.tape.shiftRight()

    f.instruction.execute(f.tape, 0)
    f.tape.pointerPosition should be(0)
  }

  it should "return next instruction" in {
    val f = fixture
    val next = new DecrementPointerInstruction
    f.instruction.next = next

    f.instruction.execute(f.tape) should be(next)
  }

  it should "decrement tape pointer" in {
    val f = fixture

    f.tape.shiftRight()

    f.instruction.execute(f.tape)
    f.tape.pointerPosition should be(0)
  }

}
