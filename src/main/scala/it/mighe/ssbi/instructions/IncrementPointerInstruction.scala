package it.mighe.ssbi.instructions

import it.mighe.ssbi.Instruction
import it.mighe.ssbi.Tape

class IncrementPointerInstruction extends Instruction {

  override def execute(tape: Tape): Instruction = {
    tape.shiftRight()
    next
  }
}