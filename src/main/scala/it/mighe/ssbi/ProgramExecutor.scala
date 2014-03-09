package it.mighe.ssbi

class ProgramExecutor(private val output: java.io.OutputStream, private val input: java.io.InputStream) {

  def execute(program: String) {

    val tape = new Tape
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
        case '[' => if(tape.current == 0) matchingClosingIndexFor(programCounter) else programCounter + 1
        case ']' => if(tape.current != 0) matchingOpeningIndexFor(programCounter) else programCounter + 1
        case _ => programCounter + 1
      }

    }

    def matchingClosingIndexFor(programCounter: Int): Int = {
      var openedCount = 0

      for (index <- programCounter to (program.length - 1)) {
        program.charAt(index) match {
          case '[' => openedCount += 1
          case ']' => openedCount -= 1
          case _ => ()
        }

        if (openedCount == 0) { return index }
      }

      -1
    }

    def matchingOpeningIndexFor(programCounter: Int): Int = {
      var closedCount = 0

      for (index <- programCounter to 0 by -1) {
        program.charAt(index) match {
          case '[' => closedCount -= 1
          case ']' => closedCount += 1
          case _ => ()
        }

        if (closedCount == 0) { return index }
      }

      -1
    }

  }

}
