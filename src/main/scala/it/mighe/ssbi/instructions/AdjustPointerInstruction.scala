package it.mighe.ssbi.instructions

import it.mighe.ssbi.Instruction
import it.mighe.ssbi.Tape

class AdjustPointerInstruction(val offset: Int) extends Instruction {

  override def execute(tape: Tape): Instruction = {
    tape.adjustPointer(offset)
    next
  }
}