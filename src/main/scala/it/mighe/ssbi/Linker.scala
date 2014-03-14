package it.mighe.ssbi

import it.mighe.ssbi.instructions.{ClosingBracketInstruction, OpeningBracketInstruction}

class Linker {

  def link(program: Array[Instruction]) {
    setFollowingLink(program)
    setBracketReferences(program)
  }

  private def setFollowingLink(buffer: Array[Instruction]) {
    for (index <- 0 until buffer.length - 1) {
      buffer(index).next = buffer(index + 1)
    }
  }

  private def setBracketReferences(buffer: Array[Instruction]) {

    val matchingBrackets = findMatchingBrackets(buffer)

    for (index <- 0 until buffer.length) {
      val instruction = buffer(index)
      instruction match {
        case opening: OpeningBracketInstruction =>
          val closing = buffer(matchingBrackets(index)).asInstanceOf[ClosingBracketInstruction]
          opening.matching = closing
          closing.matching = opening
        case _ =>
      }
    }
  }

  private def findMatchingBrackets(buffer: Array[Instruction]): Array[Int] = {
    val matchingBrackets = new Array[Int](buffer.length)

    val stack = new scala.collection.mutable.Stack[Int]

    for (index <- 0 until buffer.length) {
      val instruction = buffer(index)

      instruction match {
        case _: OpeningBracketInstruction => stack.push(index)

        case _: ClosingBracketInstruction =>
          val openingIndex = stack.pop()
          matchingBrackets(index) = openingIndex
          matchingBrackets(openingIndex) = index

        case _ => ()
      }
    }
    matchingBrackets
  }


}
