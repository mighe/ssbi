package it.mighe.ssbi.instructions

import it.mighe.ssbi.Instruction
import it.mighe.ssbi.Tape

class ClosingBracketInstruction() extends Instruction {

  var matching: OpeningBracketInstruction = null

  override def execute(tape: Tape): Instruction = {
    if (tape.current != 0) matching.next else next
  }
}