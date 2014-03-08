package it.mighe.ssbi

class ProgramExecutor(private val output: java.io.OutputStream, private val input: java.io.InputStream) {

  val tape = new Tape

  def execute(program: String) {
    for(instruction <- program) {
      instruction match {
        case '+' => tape.increment()
        case '-' => tape.decrement()
        case '>' => tape.shiftRight()
        case '<' => tape.shiftLeft()
        case '.' => output.write(tape.current)
        case ',' => tape.current = input.read()
      }
    }

  }

}
