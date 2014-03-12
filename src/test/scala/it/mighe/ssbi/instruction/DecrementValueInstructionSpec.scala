package it.mighe.ssbi.instruction

import org.scalatest.{Matchers, FlatSpec}
import it.mighe.ssbi.Tape
import it.mighe.ssbi.instructions.DecrementValueInstruction

class DecrementValueInstructionSpec extends FlatSpec with Matchers {

  def fixture = new {
    val tape = new Tape
    val instruction = new DecrementValueInstruction
  }

  it should "increment program counter" in {
    val f = fixture

    val newProgramCounter = f.instruction.execute(f.tape, 5)
    newProgramCounter should be(6)
  }

  it should "decrement tape at current position" in {
    val f = fixture

    f.instruction.execute(f.tape, 0)
    f.tape.current should be(255.toByte)
  }

}
