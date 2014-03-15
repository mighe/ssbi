package it.mighe.ssbi

import org.scalatest.{Matchers, FlatSpec}
import it.mighe.ssbi.instructions._
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream
import java.io.ByteArrayInputStream
import org.scalatest.matchers.{MatchResult, Matcher}

class ParserSpec extends FlatSpec with Matchers {

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

  it can "parse an empty program" in {
    val parser = new Parser(new ByteOutputStream(), new ByteArrayInputStream(Array()))
    val instructions = parser.parse("")
    instructions should equal(Array[Instruction]())
  }


  it should "parse a complete program" in {
    val parser = new Parser(new ByteOutputStream(), new ByteArrayInputStream(Array()))
    val instructions = parser.parse(",++>>[.[-]]<<.")

    instructions(0) shouldBe a [ReadInstruction]
    instructions(1) should beAValueInstructionWithOffset(1)
    instructions(2) should beAValueInstructionWithOffset(1)
    instructions(3) should beAPointerInstructionWithOffset(1)
    instructions(4) should beAPointerInstructionWithOffset(1)
    instructions(5) shouldBe an [OpeningBracketInstruction]
    instructions(6) shouldBe a [WriteInstruction]
    instructions(7) shouldBe an [OpeningBracketInstruction]
    instructions(8) should beAValueInstructionWithOffset(-1)
    instructions(9) shouldBe a [ClosingBracketInstruction]
    instructions(10) shouldBe a [ClosingBracketInstruction]
    instructions(11) should beAPointerInstructionWithOffset(-1)
    instructions(12) should beAPointerInstructionWithOffset(-1)
    instructions(13) shouldBe a [WriteInstruction]
  }

  it should "ignores invalid chars" in {
    val parser = new Parser(new ByteOutputStream(), new ByteArrayInputStream(Array()))
    val instructions = parser.parse("+XX-[.]")
    instructions(0) should beAValueInstructionWithOffset(1)
    instructions(1) should beAValueInstructionWithOffset(-1)
    instructions(2) shouldBe a [OpeningBracketInstruction]
    instructions(3) shouldBe a [WriteInstruction]
    instructions(4) shouldBe a [ClosingBracketInstruction]
  }

}
