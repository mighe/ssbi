package it.mighe.ssbi.instruction

import org.scalatest.{Matchers, FlatSpec}
import it.mighe.ssbi.Tape
import it.mighe.ssbi.instructions.IncrementValueInstruction

class IncrementValueInstructionSpec extends FlatSpec with Matchers {

  def fixture = new {
    val tape = new Tape
    val instruction = new IncrementValueInstruction
  }

  it should "increment program counter" in {
    val f = fixture

    val newProgramCounter = f.instruction.execute(f.tape, 0)
    newProgramCounter should be(1)
  }

  it should "increment tape at current position" in {
    val f = fixture

    f.instruction.execute(f.tape, 0)
    f.tape.current should be(1)
  }

}
