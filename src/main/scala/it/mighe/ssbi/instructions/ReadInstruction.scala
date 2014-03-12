package it.mighe.ssbi.instructions

import it.mighe.ssbi.Instruction
import it.mighe.ssbi.Tape

class ReadInstruction(private val input: java.io.InputStream) extends Instruction {
  def execute(tape: Tape, programCounter: Int): Int = {
    tape.current = input.read()
    programCounter + 1
  }
}