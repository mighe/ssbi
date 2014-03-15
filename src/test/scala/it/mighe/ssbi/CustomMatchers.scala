package it.mighe.ssbi

import org.scalatest.matchers.{MatchResult, Matcher}
import it.mighe.ssbi.instructions.{AdjustPointerInstruction, AdjustValueInstruction}

trait CustomMatchers {

  class InstructionWithValueOffset(offset: Int) extends Matcher[Instruction] {
    override def apply(left: Instruction): MatchResult = {
      left match {
        case x: AdjustValueInstruction =>
          val actualOffset = x.offset
          MatchResult(actualOffset == offset,
            s"instruction offset is $actualOffset but $offset was expected",
            s"instruction offset is $actualOffset is $offset"
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
