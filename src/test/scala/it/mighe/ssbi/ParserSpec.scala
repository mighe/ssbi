package it.mighe.ssbi

import org.scalatest.{Matchers, FlatSpec}
import it.mighe.ssbi.instructions._
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream
import java.io.ByteArrayInputStream
import CustomMatchers._

class ParserSpec extends FlatSpec with Matchers {

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
