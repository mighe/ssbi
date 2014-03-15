package it.mighe.ssbi

import scala.collection.mutable.ArrayBuffer
import it.mighe.ssbi.instructions._

class Optimizer {
  def optimize(program: Array[Instruction]): Array[Instruction] = {
    if(program.isEmpty) return program

    val optimized = new ArrayBuffer[Instruction]()

    var index = 0

    while(index < program.length) {
      val result = program(index) match {
        case x: AdjustValueInstruction => compactValueSequence(program, index)
        case x: AdjustPointerInstruction => compactShiftSequence(program, index)
        case x: OpeningBracketInstruction => compactLoop(program, index)
        case x => (x, 1)
      }

      optimized += result._1
      index += result._2
    }


    optimized.toArray
  }

  def compactValueSequence(program: Array[Instruction], index: Int) = {
    var sum = 0
    var length = 0

    while( (index + length < program.length) && program(index + length).isInstanceOf[AdjustValueInstruction] ) {
      sum += program(index + length).asInstanceOf[AdjustValueInstruction].offset
      length += 1
    }

    (new AdjustValueInstruction(sum), length)
  }

  def compactShiftSequence(program: Array[Instruction], index: Int) = {
    var sum = 0
    var length = 0

    while( (index + length < program.length) && program(index + length).isInstanceOf[AdjustPointerInstruction] ) {
      sum += program(index + length).asInstanceOf[AdjustPointerInstruction].offset
      length += 1
    }

    (new AdjustPointerInstruction(sum), length)
  }

  def compactLoop(program: Array[Instruction], index: Int): (Instruction, Int) = {

    if( program.length - index < 2) {
      return (program(index), 1)
    }

    if( program(index).isInstanceOf [OpeningBracketInstruction] &&
      program(index + 1).isInstanceOf [AdjustValueInstruction] &&
      program(index + 2).isInstanceOf [ClosingBracketInstruction]) {

      if (program(index + 1).asInstanceOf[AdjustValueInstruction].offset == -1 ) {
        return (new SetValueInstruction(0), 3)
      }

    }


    (program(index), 1)
  }

}
