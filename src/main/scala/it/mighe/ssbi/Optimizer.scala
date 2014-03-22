package it.mighe.ssbi

import scala.collection.mutable.ArrayBuffer
import it.mighe.ssbi.instructions._
import scala.collection.mutable

class Optimizer {
  def optimize(program: Array[Instruction]): Array[Instruction] = {
    optimizeLoops( optimizeLinearSequences(program) )
  }

  private def optimizeLinearSequences(program: Array[Instruction]): Array[Instruction] = {
    if(program.isEmpty) return program

    val optimized = new ArrayBuffer[Instruction]()

    var index = 0

    while(index < program.length) {
      val result = program(index) match {
        case x: AdjustValueInstruction => compactLinearSequence(program, index)
        case x: AdjustPointerInstruction => compactLinearSequence(program, index)
        case x => (Seq(x), 1)
      }

      optimized ++= result._1
      index += result._2
    }


    optimized.toArray
  }

  private def optimizeLoops(program: Array[Instruction]): Array[Instruction] = {
    if(program.isEmpty) return program

    val optimized = new ArrayBuffer[Instruction]()

    var index = 0

    while(index < program.length) {
      val result = program(index) match {
        case x: OpeningBracketInstruction => compactLoop(program, index)
        case x => (Seq(x), 1)
      }

      optimized ++= result._1
      index += result._2
    }


    optimized.toArray
  }

  private def compactLinearSequence(program: Array[Instruction], index: Int) = {
    var pointerOffset = 0
    var length = 0
    val offsetToAdjustment = new mutable.OpenHashMap[Int, Int]

    while( (index + length < program.length) && isALinearInstruction(program(index + length)) ) {
      program(index + length) match {
        case x: AdjustValueInstruction =>
          offsetToAdjustment(pointerOffset + x.pointerOffset) =
            offsetToAdjustment.getOrElse(pointerOffset + x.pointerOffset, 0) + x.valueAdjustment
        case x: AdjustPointerInstruction => pointerOffset += x.offset
      }

      length += 1
    }

    val valueInstructions: Seq[Instruction] =
      offsetToAdjustment.filter( _._2 != 0 ).
        keys.map( offset => new AdjustValueInstruction(offsetToAdjustment(offset), offset)).
        toSeq.sortBy( _.pointerOffset)

    if(pointerOffset == 0) {
      (valueInstructions, length)
    }
    else {
      (valueInstructions :+ new AdjustPointerInstruction(pointerOffset), length)
    }


  }

  private def isALinearInstruction(instruction: Instruction) = {
    instruction match {
      case x: AdjustValueInstruction => true
      case x: AdjustPointerInstruction => true
      case _ => false
    }
  }

  private def compactLoop(program: Array[Instruction], index: Int): (Seq[Instruction], Int) = {

    val linearAdjustmentSequenceLength = valueAdjustmentSequenceLength(program, index + 1)

    if( program(index).isInstanceOf [OpeningBracketInstruction] &&
      linearAdjustmentSequenceLength > 0 &&
      program(index + linearAdjustmentSequenceLength + 1).isInstanceOf [ClosingBracketInstruction]) {

      if (currentDecrementInSequence(program, index + 1, linearAdjustmentSequenceLength)) {
        val o = optimizeLinearLoopBody(program, index + 1, linearAdjustmentSequenceLength)
        return (o, linearAdjustmentSequenceLength + 2)
      }

    }

    (Seq(program(index)), 1)
  }

  def valueAdjustmentSequenceLength(program: Array[Instruction], startingIndex: Int) = {
    var length = 0

    while(program(startingIndex + length).isInstanceOf[AdjustValueInstruction]) {
      length += 1
    }

    length
  }

  def currentDecrementInSequence(program: Array[Instruction], startIndex: Int, length: Int): Boolean = {
    for( index <- startIndex until (startIndex + length) ) {
      val instruction = program(index).asInstanceOf[AdjustValueInstruction]
      if (instruction.pointerOffset == 0 && instruction.valueAdjustment == -1) {
        return true
      }
    }

    false
  }

  def optimizeLinearLoopBody(program: Array[Instruction], startIndex: Int, length: Int) = {
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
