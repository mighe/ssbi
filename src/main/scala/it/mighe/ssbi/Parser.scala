package it.mighe.ssbi

import it.mighe.ssbi.instructions._

class Parser(private val output: java.io.OutputStream, private val input: java.io.InputStream) {

  def parse(program: String): Array[Instruction] = {

    val cleanedProgram = program.filter( "+-.,<>[]" contains _ )
    val buffer: Array[Instruction] = generateInstructions(cleanedProgram)

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



}
