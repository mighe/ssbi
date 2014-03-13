package it.mighe.ssbi.instructions

import it.mighe.ssbi.Instruction
import it.mighe.ssbi.Tape

class OpeningBracketInstruction() extends Instruction {

  var matching: ClosingBracketInstruction = null

  override def execute(tape: Tape): Instruction = {
    if (tape.current == 0) matching else next
  }
}