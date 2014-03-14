package it.mighe.ssbi

import org.scalatest.{Matchers, FlatSpec}
import it.mighe.ssbi.instructions._
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream
import java.io.ByteArrayInputStream

class LinkerSpec extends FlatSpec with Matchers {

  it should "link a complete program" in {
    val parser = new Parser(new ByteOutputStream(), new ByteArrayInputStream(Array()))
    val instructions = parser.parse(",++>>[.[-]]<<.")

    new Linker().link(instructions)

    for(i <- 0 until instructions.length - 1) {
      instructions(i).next should be(instructions(i + 1))
    }

    instructions.last.next should be(null)

    instructions(5).asInstanceOf[OpeningBracketInstruction].matching should be(instructions(10))
    instructions(7).asInstanceOf[OpeningBracketInstruction].matching should be(instructions(9))
    instructions(10).asInstanceOf[ClosingBracketInstruction].matching should be(instructions(5))
    instructions(9).asInstanceOf[ClosingBracketInstruction].matching should be(instructions(7))
  }

}
