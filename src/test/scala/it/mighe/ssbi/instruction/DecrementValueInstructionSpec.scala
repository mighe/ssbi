package it.mighe.ssbi.instruction

import org.scalatest.{Matchers, FlatSpec}
import it.mighe.ssbi.Tape
import it.mighe.ssbi.instructions.DecrementValueInstruction

class DecrementValueInstructionSpec extends FlatSpec with Matchers {

  def fixture = new {
    val tape = new Tape
    val instruction = new DecrementValueInstruction
  }

  it should "return next instruction" in {
    val f = fixture
    val next = new DecrementValueInstruction
    f.instruction.next = next

    f.instruction.execute(f.tape) should be(next)
  }

  it should "decrement tape at current position" in {
    val f = fixture

    f.instruction.execute(f.tape)
    f.tape.current should be(255.toByte)
  }

}
