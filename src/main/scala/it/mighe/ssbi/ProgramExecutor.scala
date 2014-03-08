package it.mighe.ssbi

class ProgramExecutor(private val output: java.io.OutputStream) {

  var cell: Byte = 0

  def execute(program: String) {
    for(instruction <- program) {
      instruction match {
        case '+' => cell = (cell + 1).toByte
        case '-' => cell = (cell - 1).toByte
        case '.' => output.write(cell)
      }
    }

  }

}
