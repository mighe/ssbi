package it.mighe.ssbi.optimizer

import it.mighe.ssbi.Instruction
import it.mighe.ssbi.instructions._
import scala.collection.mutable.ArrayBuffer

class LinearLoopOptimizer {

  def loopOptimizer(program: Array[Instruction], index: Int) = {
    program(index) match {
      case x: OpeningBracketInstruction => compactLoop(program, index)
      case x => (Seq(x), 1)
    }
  }

  private def compactLoop(program: Array[Instruction], index: Int): (Seq[Instruction], Int) = {

    val linearAdjustmentSequenceLength = valueAdjustmentSequenceLength(program, index + 1)

    if( program(index).isInstanceOf [OpeningBracketInstruction] &&
      linearAdjustmentSequenceLength > 0 &&
      program(index + linearAdjustmentSequenceLength + 1).isInstanceOf [ClosingBracketInstruction]) {

      if (hasCurrentValueDecrementInSequence(program, index + 1, linearAdjustmentSequenceLength)) {
        val o = optimizeLinearLoopBody(program, index + 1, linearAdjustmentSequenceLength)
        return (o, linearAdjustmentSequenceLength + 2)
      }

    }

    (Seq(program(index)), 1)
  }

  private def valueAdjustmentSequenceLength(program: Array[Instruction], startingIndex: Int) = {
    var length = 0

    while(program(startingIndex + length).isInstanceOf[AdjustValueInstruction]) {
      length += 1
    }

    length
  }

  private def hasCurrentValueDecrementInSequence(program: Array[Instruction], startIndex: Int, length: Int): Boolean = {
    for( index <- startIndex until (startIndex + length) ) {
      val instruction = program(index).asInstanceOf[AdjustValueInstruction]
      if (instruction.pointerOffset == 0 && instruction.valueAdjustment == -1) {
        return true
      }
    }

    false
  }

  private def optimizeLinearLoopBody(program: Array[Instruction], startIndex: Int, length: Int) = {
    val optimized = new ArrayBuffer[Instruction]()

    for( index <- startIndex until (startIndex + length) ) {
      val instruction = program(index).asInstanceOf[AdjustValueInstruction]
      if (instruction.pointerOffset != 0) {
        optimized += new MultiplyCurrentAndSumInstruction(instruction.valueAdjustment, instruction.pointerOffset)
      }
    }

    optimized += new SetValueInstruction(0)

    optimized.toSeq
  }
}
