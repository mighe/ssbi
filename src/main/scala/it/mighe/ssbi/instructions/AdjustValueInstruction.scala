package it.mighe.ssbi.instructions

import it.mighe.ssbi.Instruction
import it.mighe.ssbi.Tape

class AdjustValueInstruction(val valueAdjustment: Int, val tapeOffset: Int = 0) extends Instruction {

  override def execute(tape: Tape): Instruction = {
    tape.adjustValue(valueAdjustment, tapeOffset)
    next
  }
}