package it.mighe.ssbi.instructions

import it.mighe.ssbi.Instruction
import it.mighe.ssbi.Tape

class AdjustValueInstruction(val valueAdjustment: Int, val pointerOffset: Int = 0) extends Instruction {

  override def execute(tape: Tape): Instruction = {
    tape.adjustValue(valueAdjustment, pointerOffset)
    next
  }

  override def toString = {
    s"AdjustValueInstruction(valueAdjustment: $valueAdjustment, pointerOffset: $pointerOffset)"
  }
}