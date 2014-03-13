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
  }

  it should "ignores invalid chars" in {
    val parser = new Parser(new ByteOutputStream(), new ByteArrayInputStream(Array()))
    val instructions = parser.parse("+XX-[.]")
    instructions(0) shouldBe an [IncrementValueInstruction]
    instructions(1) shouldBe a [DecrementValueInstruction]
    instructions(2) shouldBe a [WriteInstruction]
  }

}
