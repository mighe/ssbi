package it.mighe.ssbi

import org.scalatest.{Matchers, FlatSpec}
import java.io.{ByteArrayInputStream, InputStream, ByteArrayOutputStream}

class InterpreterSpec extends FlatSpec with Matchers {

  def verifyProgram(program: String, expectedOutput: String, inputBuffer: String = "") {
    val expectedOutputArray = expectedOutput.toCharArray.map( _.toByte )
    val inputBufferArray = new ByteArrayInputStream(inputBuffer.toCharArray.map( _.toByte ))

    verifyProgram(program, expectedOutputArray, inputBufferArray)
  }

  def verifyProgram(program: String, expectedOutput: Array[Int]) {
    verifyProgram(program, expectedOutput, "")
  }

  def verifyProgram(program: String, expectedOutput: Array[Int], inputBuffer: String) {
    val inputBufferArray = new ByteArrayInputStream(inputBuffer.toCharArray.map( _.toByte ))
    verifyProgram(program, expectedOutput.map( _.toByte ), inputBufferArray)
  }

  def verifyProgram(program: String, expectedOutput: Array[Byte]) {
    verifyProgram(program, expectedOutput, new ByteArrayInputStream(Array[Byte](0)))
  }

  def verifyProgram(program: String, expectedOutput: Array[Byte], input: java.io.InputStream) {
    val output = new ByteArrayOutputStream()
    val executor = new ProgramExecutor(output, input)

    executor.execute(program)
    output.toByteArray should equal(expectedOutput)
  }

  it can "execute an empty program" in {
    verifyProgram("", "")
  }

  it can "execute simple programs" in {
    verifyProgram("+.", Array(1))
    verifyProgram("+>++.<.", Array(2, 1))
    verifyProgram("+++.-.-.", Array(3, 2, 1))
    verifyProgram("-.+.", Array(255, 0))
  }

  it can "execute programs with input" in {
    verifyProgram(",+.,-.,.", "blp", "amp")
  }

  it can "execute programs with a single jump" in {
    verifyProgram("[.........]", "")
    verifyProgram(",[-].", Array(0), "X")
    verifyProgram("++[.-]", Array(2, 1))
  }

  it can "execute programs with sequential loops" in {
    verifyProgram("+++[>++++<-]>[>+++++++<-]>.", "T")
  }

  it can "execute programs with nested loops" in {
    verifyProgram("++[>++[.-]<-]", Array(2, 1, 2, 1))
    verifyProgram("[...[[]]......]", "")
  }

}
