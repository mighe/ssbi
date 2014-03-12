package it.mighe.ssbi.instructions

import it.mighe.ssbi.Instruction
import it.mighe.ssbi.Tape

class OpeningBracketInstruction(val matchingClosing: Int) extends Instruction {
  def execute(tape: Tape, programCounter: Int): Int = {
    if (tape.current == 0) matchingClosing else programCounter + 1
  }
}