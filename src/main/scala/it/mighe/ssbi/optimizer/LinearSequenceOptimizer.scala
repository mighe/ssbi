package it.mighe.ssbi.optimizer

import it.mighe.ssbi.Instruction
import it.mighe.ssbi.instructions.{AdjustPointerInstruction, AdjustValueInstruction}
import scala.collection.mutable

class LinearSequenceOptimizer {
  def linearSequenceOptimizer(program: Array[Instruction], index: Int) = {
    program(index) match {
      case x: AdjustValueInstruction => compactLinearSequence(program, index)
      case x: AdjustPointerInstruction => compactLinearSequence(program, index)
      case x => (Seq(x), 1)
    }
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
}
