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

    val buffer: Array[Instruction] = generateInstructions(cleanedProgram)

    setFollowingLink(buffer)
    setBracketReferences(buffer, matchingBrackets)

    buffer

  }

  private def scanForMatchingClosing(program: String, index: Int): Int = {
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

  private def generateInstructions(cleanedProgram: String): Array[Instruction] = {
    val buffer = new Array[Instruction](cleanedProgram.length)

    for (index <- 0 until cleanedProgram.length) {

      val instruction = cleanedProgram.charAt(index)

      val instructionObject = instruction match {
        case '+' => new IncrementValueInstruction
        case '-' => new DecrementValueInstruction
        case '>' => new IncrementPointerInstruction
        case '<' => new DecrementPointerInstruction
        case '.' => new WriteInstruction(output)
        case ',' => new ReadInstruction(input)
        case '[' => new OpeningBracketInstruction()
        case ']' => new ClosingBracketInstruction()
      }

      buffer(index) = instructionObject
    }
    buffer
  }

  private def setFollowingLink(buffer: Array[Instruction]) {
    for (index <- 0 until buffer.length - 1) {
      buffer(index).next = buffer(index + 1)
    }
  }

  private def setBracketReferences(buffer: Array[Instruction], matchingBrackets: Array[Int]) {
    for (index <- 0 until buffer.length) {
      val instruction = buffer(index)
      instruction match {
        case opening: OpeningBracketInstruction =>
          val closing = buffer(matchingBrackets(index)).asInstanceOf[ClosingBracketInstruction]
          opening.matching = closing
          closing.matching = opening
        case _ =>
      }
    }
  }
}
