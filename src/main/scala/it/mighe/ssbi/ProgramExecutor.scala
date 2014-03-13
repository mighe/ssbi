package it.mighe.ssbi

class ProgramExecutor(private val output: java.io.OutputStream, private val input: java.io.InputStream) {

  def execute(program: Array[Instruction]) {

    if(program.isEmpty) { return }

    val tape = new Tape

    var instruction = program.head

    while (instruction != null) {
      instruction = instruction.execute(tape)
    }
  }

}
