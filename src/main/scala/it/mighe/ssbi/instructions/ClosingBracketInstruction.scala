package it.mighe.ssbi.instructions

import it.mighe.ssbi.Instruction
import it.mighe.ssbi.Tape

class ClosingBracketInstruction(private val matchingOpening: Int) extends Instruction {
  def execute(tape: Tape, programCounter: Int): Int = {
    if (tape.current != 0) matchingOpening else programCounter + 1
  }
}