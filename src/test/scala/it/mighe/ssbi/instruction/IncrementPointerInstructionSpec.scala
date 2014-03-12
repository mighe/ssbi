package it.mighe.ssbi.instruction

import org.scalatest.{Matchers, FlatSpec}
import it.mighe.ssbi.Tape
import it.mighe.ssbi.instructions.{IncrementPointerInstruction, DecrementPointerInstruction}

class IncrementPointerInstructionSpec extends FlatSpec with Matchers {

  def fixture = new {
    val tape = new Tape
    val instruction = new IncrementPointerInstruction
  }

  it should "increment program counter" in {
    val f = fixture

    val newProgramCounter = f.instruction.execute(f.tape, 15)
    newProgramCounter should be(16)
  }

  it should "increment tape pointer" in {
    val f = fixture

    f.instruction.execute(f.tape, 14)
    f.tape.pointerPosition should be(1)
  }

}
