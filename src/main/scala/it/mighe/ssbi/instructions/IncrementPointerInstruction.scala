package it.mighe.ssbi.instructions

import it.mighe.ssbi.Instruction
import it.mighe.ssbi.Tape

class IncrementPointerInstruction extends Instruction {
  def execute(tape: Tape, programCounter: Int): Int = {
    tape.shiftRight()
    programCounter + 1
  }

  override def execute(tape: Tape): Instruction = {
    tape.shiftRight()
    next
  }
}