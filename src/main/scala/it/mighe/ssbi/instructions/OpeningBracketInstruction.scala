package it.mighe.ssbi.instructions

import it.mighe.ssbi.Instruction
import it.mighe.ssbi.Tape

class OpeningBracketInstruction(private val matchingClosing: Int) extends Instruction {

  var matching: ClosingBracketInstruction = null

  def execute(tape: Tape, programCounter: Int): Int = {
    if (tape.current == 0) matchingClosing else programCounter + 1
  }

  override def execute(tape: Tape): Instruction = {
    if (tape.current == 0) matching else next
  }
}