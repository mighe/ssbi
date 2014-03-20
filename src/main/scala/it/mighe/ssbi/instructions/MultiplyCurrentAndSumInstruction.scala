package it.mighe.ssbi.instructions

import it.mighe.ssbi.{Tape, Instruction}


class MultiplyCurrentAndSumInstruction(val multiplier: Int, val pointerOffset: Int) extends Instruction {

  override def execute(tape: Tape): Instruction = {
    tape.adjustValue( tape.current * multiplier , pointerOffset)
    next
  }

  override def toString = {
    s"MultiplyCurrentAndSumInstruction(multiplier: $multiplier, pointerOffset: $pointerOffset)"
  }
}