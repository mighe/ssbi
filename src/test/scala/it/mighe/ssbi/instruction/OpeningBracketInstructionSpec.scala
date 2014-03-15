package it.mighe.ssbi.instruction

import org.scalatest.{Matchers, FlatSpec}
import it.mighe.ssbi.Tape
import it.mighe.ssbi.instructions.{ClosingBracketInstruction, OpeningBracketInstruction}

class OpeningBracketInstructionSpec extends FlatSpec with Matchers {

  def fixture = new {
    val tape = new Tape
    val instruction = new OpeningBracketInstruction()
    val matching = new ClosingBracketInstruction()

    val matching_next = new ClosingBracketInstruction()
    matching.next = matching_next
    instruction.matching = matching
  }

  it should "return next instruction if current value is not zero" in {
    val f = fixture
    val next = new OpeningBracketInstruction()
    f.instruction.next = next
    f.tape.increment()

    f.instruction.execute(f.tape) should be(next)
  }

  it should "jump to matching closing next if current value is zero" in {
    val f = fixture

    f.instruction.execute(f.tape) should be(f.matching_next)
  }

}
