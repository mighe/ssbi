package it.mighe.ssbi

import scala.collection.mutable.ArrayBuffer
import it.mighe.ssbi.instructions._
import scala.collection.mutable

class Optimizer {
  def optimize(program: Array[Instruction]): Array[Instruction] = {
    if(program.isEmpty) return program

    val optimized = new ArrayBuffer[Instruction]()

    var index = 0

    while(index < program.length) {
      val result = program(index) match {
        case x: AdjustValueInstruction => compactLinearSequence(program, index)
        case x: AdjustPointerInstruction => compactLinearSequence(program, index)
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

    val allInstructions = offsetToAdjustment.keys.map( offset => new AdjustValueInstruction(offsetToAdjustment(offset), offset))
    val significantInstructions: Seq[Instruction] = allInstructions.filter( _.valueAdjustment != 0).toSeq.sortBy( _.pointerOffset)

    if(pointerOffset == 0) {
      (significantInstructions, length)
    }
    else {
      (significantInstructions :+ new AdjustPointerInstruction(pointerOffset), length)
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

    if( program.length - index < 2) {
      return (Seq(program(index)), 1)
    }

    if( program(index).isInstanceOf [OpeningBracketInstruction] &&
      program(index + 1).isInstanceOf [AdjustValueInstruction] &&
      program(index + 2).isInstanceOf [ClosingBracketInstruction]) {

      if (program(index + 1).asInstanceOf[AdjustValueInstruction].valueAdjustment == -1 ) {
        return (Seq(new SetValueInstruction(0)), 3)
      }

    }

    (Seq(program(index)), 1)
  }

}
