package it.mighe.ssbi

class ProgramExecutor(private val output: java.io.OutputStream, private val input: java.io.InputStream) {

  def execute(program: Array[Instruction]) {

    val tape = new Tape
    var programCounter = 0

    while (programCounter < program.length) {
      programCounter = program(programCounter).execute(tape, programCounter)
    }
  }

}
