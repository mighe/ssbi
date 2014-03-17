package it.mighe.ssbi

import org.scalatest.matchers.{MatchResult, Matcher}
import it.mighe.ssbi.instructions.{SetValueInstruction, AdjustPointerInstruction, AdjustValueInstruction}

trait CustomMatchers {

  class InstructionWithValue(value: Int) extends Matcher[Instruction] {
    override def apply(left: Instruction): MatchResult = {
      left match {
        case x: SetValueInstruction =>
          val actualValue = x.value
          MatchResult(actualValue == value,
            s"instruction value is $actualValue but $value was expected",
            s"instruction value is $actualValue is $value"
          )
        case y =>
          val actualClass = y.getClass.getName
          MatchResult(matches = false, s"instruction is a $actualClass not an SetValueInstruction", "")
      }
    }
  }

  def beAValueInstructionWithValue(offset: Int) = new InstructionWithValue(offset)

  class InstructionWithValueOffset(valueAdjustment: Int, pointerOffset: Int = 0) extends Matcher[Instruction] {
    override def apply(left: Instruction): MatchResult = {
      left match {
        case x: AdjustValueInstruction =>
          val actualAdjustment = x.valueAdjustment
          val actualOffset = x.pointerOffset
          MatchResult(actualAdjustment == valueAdjustment && actualOffset == pointerOffset,
            s"instruction is AdjustValueInstruction($actualAdjustment, $actualOffset) but AdjustValueInstruction($valueAdjustment, $pointerOffset) was expected",
            s"instruction is AdjustValueInstruction($valueAdjustment, $pointerOffset)"
          )
        case y =>
          val actualClass = y.getClass.getName
          MatchResult(matches = false, s"instruction is a $actualClass not an AdjustValueInstruction", "")
      }
    }
  }

  def beAValueInstructionWithOffset(offset: Int) = new InstructionWithValueOffset(offset)

  class InstructionWithPointerOffset(offset: Int) extends Matcher[Instruction] {
    override def apply(left: Instruction): MatchResult = {
      left match {
        case x: AdjustPointerInstruction =>
          val actualOffset = x.offset
          MatchResult(actualOffset == offset,
            s"instruction offset is $actualOffset but $offset was expected",
            s"instruction offset is $actualOffset is $offset"
          )
        case y =>
          val actualClass = y.getClass.getName
          MatchResult(matches = false, s"instruction is a $actualClass not an AdjustPointerInstruction", "")
      }
    }
  }

  def beAPointerInstructionWithOffset(offset: Int) = new InstructionWithPointerOffset(offset)

}

object CustomMatchers extends CustomMatchers
