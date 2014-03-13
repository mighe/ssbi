package it.mighe.ssbi

import org.scalatest.{Matchers, FlatSpec}
import it.mighe.ssbi.instructions._
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream
import java.io.ByteArrayInputStream

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
    instructions(1) shouldBe an [IncrementValueInstruction]
    instructions(2) shouldBe an [IncrementValueInstruction]
    instructions(3) shouldBe an [IncrementPointerInstruction]
    instructions(4) shouldBe an [IncrementPointerInstruction]
    instructions(5) shouldBe an [OpeningBracketInstruction]
    instructions(6) shouldBe a [WriteInstruction]
    instructions(7) shouldBe an [OpeningBracketInstruction]
    instructions(8) shouldBe a [DecrementValueInstruction]
    instructions(9) shouldBe a [ClosingBracketInstruction]
    instructions(10) shouldBe a [ClosingBracketInstruction]
    instructions(11) shouldBe a [DecrementPointerInstruction]
    instructions(12) shouldBe a [DecrementPointerInstruction]
    instructions(13) shouldBe a [WriteInstruction]

    for(i <- 0 until instructions.length - 1) {
      instructions(i).next should be(instructions(i + 1))
    }

    instructions.last.next should be(null)

    instructions(5).asInstanceOf[OpeningBracketInstruction].matching should be(instructions(10))
    instructions(7).asInstanceOf[OpeningBracketInstruction].matching should be(instructions(9))
    instructions(10).asInstanceOf[ClosingBracketInstruction].matching should be(instructions(5))
    instructions(9).asInstanceOf[ClosingBracketInstruction].matching should be(instructions(7))
  }

  it should "ignores invalid chars" in {
    val parser = new Parser(new ByteOutputStream(), new ByteArrayInputStream(Array()))
    val instructions = parser.parse("+XX-[.]")
    instructions(0) shouldBe an [IncrementValueInstruction]
    instructions(1) shouldBe a [DecrementValueInstruction]
    instructions(2) shouldBe a [OpeningBracketInstruction]
    instructions(3) shouldBe a [WriteInstruction]
    instructions(4) shouldBe a [ClosingBracketInstruction]
  }

}
