package it.mighe.ssbi

import scala.annotation.tailrec

class ProgramExecutor(private val output: java.io.OutputStream, private val input: java.io.InputStream) {

  def execute(programHead: Instruction) {
    execute(programHead, new Tape)
  }

  @tailrec private def execute(instruction: Instruction, tape: Tape) {
    if(instruction == null) { return }
    execute(instruction.execute(tape), tape)
  }

}
