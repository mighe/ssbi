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

  it should "increment program counter" in {
    val f = fixture

    val newProgramCounter = f.instruction.execute(f.tape, 42)
    newProgramCounter should be(43)
  }

  it should "write current value" in {
    val f = fixture

    f.tape.current = 80
    f.instruction.execute(f.tape, 43)

    f.output.toByteArray should equal(Array[Byte](80))
  }

}
