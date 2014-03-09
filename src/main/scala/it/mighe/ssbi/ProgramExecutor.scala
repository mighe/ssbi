package it.mighe.ssbi

class ProgramExecutor(private val output: java.io.OutputStream, private val input: java.io.InputStream) {

  val tape = new Tape

  def execute(program: String) {

    var programCounter = 0

    while (programCounter < program.length) {
      val instruction = program.charAt(programCounter)

      programCounter = instruction match {
        case '+' => tape.increment(); programCounter + 1
        case '-' => tape.decrement(); programCounter + 1
        case '>' => tape.shiftRight(); programCounter + 1
        case '<' => tape.shiftLeft(); programCounter + 1
        case '.' => output.write(tape.current); programCounter + 1
        case ',' => tape.current = input.read(); programCounter + 1
        case '[' => if(tape.current == 0) matchingClosingFor(programCounter) + 1 else programCounter + 1
        case ']' => if(tape.current != 0) matchingOpeningFor(programCounter) + 1 else programCounter + 1
      }

    }

    def matchingClosingFor(programCounter: Int): Int = { program.indexOf(']', programCounter) }
    def matchingOpeningFor(programCounter: Int): Int = { program.lastIndexOf('[', programCounter) }

  }

}
