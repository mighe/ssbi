package it.mighe.ssbi

import it.mighe.ssbi.instructions._

class Parser(private val output: java.io.OutputStream, private val input: java.io.InputStream) {

  def parse(program: String): Array[Instruction] = {

    val cleanedProgram = program.filter( "+-.,<>[]" contains _ )
    val buffer: Array[Instruction] = generateInstructions(cleanedProgram)

    setFollowingLink(buffer)
    setBracketReferences(buffer)

    buffer

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

  private def setBracketReferences(buffer: Array[Instruction]) {

    val matchingBrackets = findMatchingBrackets(buffer)

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

  private def findMatchingBrackets(buffer: Array[Instruction]): Array[Int] = {
    val matchingBrackets = new Array[Int](buffer.length)

    val stack = new scala.collection.mutable.Stack[Int]

    for (index <- 0 until buffer.length) {
      val instruction = buffer(index)

      instruction match {
        case _: OpeningBracketInstruction => stack.push(index)

        case _: ClosingBracketInstruction =>
          val openingIndex = stack.pop()
          matchingBrackets(index) = openingIndex
          matchingBrackets(openingIndex) = index

        case _ => ()
      }
    }
    matchingBrackets
  }

}
