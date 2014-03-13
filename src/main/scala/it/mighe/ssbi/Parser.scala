package it.mighe.ssbi

import it.mighe.ssbi.instructions._

class Parser(private val output: java.io.OutputStream, private val input: java.io.InputStream) {

  def parse(program: String): Array[Instruction] = {

    val cleanedProgram = program.filter( "+-.,<>[]" contains _ )

    val matchingBrackets = new Array[Int](program.length)

    for(index <- 0 until cleanedProgram.length) {
      val instruction = cleanedProgram.charAt(index)

      if (instruction == '[') {
        matchingBrackets(index) = scanForMatchingClosing(cleanedProgram, index)
        matchingBrackets(matchingBrackets(index)) = index
      }
    }

    val buffer = new Array[Instruction](cleanedProgram.length)

    for(index <- 0 until cleanedProgram.length) {

      val instruction = cleanedProgram.charAt(index)

      val instructionObject = instruction match {
        case '+' => new IncrementValueInstruction
        case '-' => new DecrementValueInstruction
        case '>' => new IncrementPointerInstruction
        case '<' => new DecrementPointerInstruction
        case '.' => new WriteInstruction(output)
        case ',' => new ReadInstruction(input)
        case '[' => new OpeningBracketInstruction(matchingBrackets(index))
        case ']' => new ClosingBracketInstruction(matchingBrackets(index))
      }

      buffer(index) = instructionObject
    }

    buffer

  }

  def scanForMatchingClosing(program: String, index: Int): Int = {
    var openedCount = 0

    for (index <- index to (program.length - 1)) {
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
