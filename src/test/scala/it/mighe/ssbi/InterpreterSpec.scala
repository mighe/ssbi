package it.mighe.ssbi

import org.scalatest.{Matchers, FlatSpec}
import java.io.ByteArrayOutputStream

class InterpreterSpec extends FlatSpec with Matchers {

  def verifyProgram(program: String, expectedOutput: String) {
    verifyProgram(program, expectedOutput.toCharArray.map( _.toByte ))
  }

  def verifyProgram(program: String, expectedOutput: Array[Int]) {
    verifyProgram(program, expectedOutput.map( _.toByte ))
  }

  def verifyProgram(program: String, expectedOutput: Array[Byte]) {
    val output = new ByteArrayOutputStream()
    val executor = new ProgramExecutor(output)

    executor.execute(program)
    output.toByteArray should equal(expectedOutput)
  }

  it can "execute an empty program" in {
    verifyProgram("", "")
  }

  it can "execute simple programs" in {
    verifyProgram("+.", Array(1))
//    verifyProgram("+>++.<.", Array(2, 1))
    verifyProgram("+++.-.-.", Array(3, 2, 1))
//    verifyProgram(",+.,-.,.", "blp", "amp")
//    verifyProgram("[.........]", "")
//    verifyProgram("++[.-]", "#{2.chr}#{1.chr}")
//    verifyProgram("++[>++[.-]<-]", "#{2.chr}#{1.chr}#{2.chr}#{1.chr}")
//    verifyProgram(",[-].", "#{0.chr}", "X")
//    verifyProgram("-.+.", "#{255.chr}#{0.chr}")
//    verifyProgram("[...[[]]......]", "")
//    verifyProgram("+++++++++++++++[-].", "#{0.chr}")
//    verifyProgram("++++++++++++++++[>+++++<-].>.", "#{0.chr}#{80.chr}")
  }

}
