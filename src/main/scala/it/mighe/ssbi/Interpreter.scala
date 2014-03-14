package it.mighe.ssbi

class Interpreter(private val output: java.io.OutputStream, private val input: java.io.InputStream) {

  def execute(sourceCode: String) {
    val rawProgram = new Parser(output, input).parse(sourceCode)
    val optimizedProgram = new Optimizer().optimize(rawProgram)
    new Linker().link(optimizedProgram)

    val programHead = if(optimizedProgram.isEmpty) null else optimizedProgram.head

    new ProgramExecutor(output, input).execute(programHead)
  }
}
