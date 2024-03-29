package it.mighe.ssbi.instruction

import org.scalatest.{Matchers, FlatSpec}
import it.mighe.ssbi.Tape
import it.mighe.ssbi.instructions.ReadInstruction
import java.io.ByteArrayInputStream

class ReadInstructionSpec extends FlatSpec with Matchers {

  def fixture = new {
    val tape = new Tape
    val input = new ByteArrayInputStream(Array[Byte](50))
    val instruction = new ReadInstruction(input)
  }

  it should "return next instruction" in {
    val f = fixture
    val next = new ReadInstruction(f.input)
    f.instruction.next = next

    f.instruction.execute(f.tape) should be(next)
  }

  it should "set current value to read value" in {
    val f = fixture

    f.instruction.execute(f.tape)

    f.tape.current should be(50)
  }

}
