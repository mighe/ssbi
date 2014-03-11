package it.mighe.ssbi

class ProgramExecutor(private val output: java.io.OutputStream, private val input: java.io.InputStream) {

  def execute(program: String) {

    val tape = new Tape
    var programCounter = 0
    val matchingBrackets = new scala.collection.mutable.OpenHashMap[Int, Int]

    for(index <- 0 until program.length) {
      val instruction = program.charAt(index)

      if (instruction == '[') {
        matchingBrackets(index) = scanForMatchingClosing(index)
        matchingBrackets(matchingBrackets(index)) = index
      }
    }

    while (programCounter < program.length) {
      val instruction = program.charAt(programCounter)

      programCounter = instruction match {
        case '+' => tape.increment(); programCounter + 1
        case '-' => tape.decrement(); programCounter + 1
        case '>' => tape.shiftRight(); programCounter + 1
        case '<' => tape.shiftLeft(); programCounter + 1
        case '.' => output.write(tape.current); programCounter + 1
        case ',' => tape.current = input.read(); programCounter + 1
        case '[' => if(tape.current == 0) matchingBrackets(programCounter) else programCounter + 1
        case ']' => if(tape.current != 0) matchingBrackets(programCounter) else programCounter + 1
        case _ => programCounter + 1
      }

    }

    def scanForMatchingClosing(programCounter: Int): Int = {
      var openedCount = 0

      for (index <- programCounter to (program.length - 1)) {
        program.charAt(index) match {
          case '[' => openedCount += 1
          case ']' => openedCount -= 1
          case _ => ()
        }

        if (openedCount == 0) {
          return index
        }
      }

      -1
    }


  }

}
