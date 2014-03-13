package it.mighe.ssbi.instructions

import it.mighe.ssbi.Instruction
import it.mighe.ssbi.Tape

class WriteInstruction(private val output: java.io.OutputStream) extends Instruction {
  def execute(tape: Tape, programCounter: Int): Int = {
    output.write( tape.current )
    programCounter + 1
  }

  override def execute(tape: Tape): Instruction = {
    output.write( tape.current )
    next
  }
}