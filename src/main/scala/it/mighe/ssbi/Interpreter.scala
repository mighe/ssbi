package it.mighe.ssbi

class Interpreter(private val output: java.io.OutputStream, private val input: java.io.InputStream) {

  def execute(sourceCode: String) {
    val program = new Parser(output, input).parse(sourceCode)
    new ProgramExecutor(output, input).execute(program)
  }
}
