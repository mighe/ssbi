package it.mighe.ssbi.instructions

import it.mighe.ssbi.Instruction
import it.mighe.ssbi.Tape

class ReadInstruction(private val input: java.io.InputStream) extends Instruction {

  override def execute(tape: Tape): Instruction = {
    tape.current = input.read()
    next
  }
}