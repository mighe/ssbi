package it.mighe.ssbi.instructions

import it.mighe.ssbi.Instruction
import it.mighe.ssbi.Tape

class IncrementValueInstruction extends Instruction {
  def execute(tape: Tape, programCounter: Int): Int = {
    tape.increment()
    programCounter + 1
  }

  override def execute(tape: Tape): Instruction = {
    tape.increment()
    next
  }
}