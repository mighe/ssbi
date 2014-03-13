package it.mighe.ssbi.instructions

import it.mighe.ssbi.Instruction
import it.mighe.ssbi.Tape

class WriteInstruction(private val output: java.io.OutputStream) extends Instruction {

  override def execute(tape: Tape): Instruction = {
    output.write( tape.current )
    next
  }
}