package it.mighe.ssbi.instructions

import it.mighe.ssbi.Instruction
import it.mighe.ssbi.Tape

class SetValueInstruction(val value: Int) extends Instruction {

  override def execute(tape: Tape): Instruction = {
    tape.current = value
    next
  }
}