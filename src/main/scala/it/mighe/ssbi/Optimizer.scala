package it.mighe.ssbi

import scala.collection.mutable.ArrayBuffer
import it.mighe.ssbi.instructions.{AdjustPointerInstruction, AdjustValueInstruction}

class Optimizer {
  def optimize(program: Array[Instruction]): Array[Instruction] = {
    if(program.isEmpty) return program

    val optimized = new ArrayBuffer[Instruction]()

    var index = 0

    while(index < program.length) {
      program(index) match {
        case x: AdjustValueInstruction =>
          val result = compactValueSequence(program, index)
          optimized += result._1
          index += result._2
        case x: AdjustPointerInstruction =>
          val result = compactShiftSequence(program, index)
          optimized += result._1
          index += result._2
        case x =>
          optimized += x
          index += 1
      }
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

}
