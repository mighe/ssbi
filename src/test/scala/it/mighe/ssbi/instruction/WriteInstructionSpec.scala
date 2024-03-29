package it.mighe.ssbi.instruction

import org.scalatest.{Matchers, FlatSpec}
import it.mighe.ssbi.Tape
import it.mighe.ssbi.instructions.WriteInstruction
import java.io.ByteArrayOutputStream

class WriteInstructionSpec extends FlatSpec with Matchers {

  def fixture = new {
    val tape = new Tape
    val output = new ByteArrayOutputStream()
    val instruction = new WriteInstruction(output)
  }

  it should "return next instruction" in {
    val f = fixture
    val next = new WriteInstruction(f.output)
    f.instruction.next = next

    f.instruction.execute(f.tape) should be(next)
  }

  it should "write current value" in {
    val f = fixture

    f.tape.current = 80
    f.instruction.execute(f.tape)

    f.output.toByteArray should equal(Array[Byte](80))
  }

}
