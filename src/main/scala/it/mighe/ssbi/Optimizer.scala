package it.mighe.ssbi

import scala.collection.mutable.ArrayBuffer
import it.mighe.ssbi.instructions._
import scala.collection.mutable
import it.mighe.ssbi.optimizer.{LinearLoopOptimizer, LinearSequenceOptimizer}

class Optimizer {
  type InstructionSequenceOptimizer = (Array[Instruction], Int) => (Seq[Instruction], Int)

  def optimize(program: Array[Instruction]): Array[Instruction] = {
    optimizeLoops( optimizeLinearSequences(program) )
  }

  private def optimizeProgram(program: Array[Instruction], f: InstructionSequenceOptimizer ): Array[Instruction] = {
    if(program.isEmpty) return program

    val optimized = new ArrayBuffer[Instruction]()

    var index = 0

    while(index < program.length) {
      val result = f(program, index)

      optimized ++= result._1
      index += result._2
    }


    optimized.toArray
  }

  private def optimizeLinearSequences(program: Array[Instruction]): Array[Instruction] = {
    optimizeProgram(program, new LinearSequenceOptimizer().linearSequenceOptimizer)
  }

  private def optimizeLoops(program: Array[Instruction]): Array[Instruction] = {
    optimizeProgram(program, new LinearLoopOptimizer().loopOptimizer)
  }

}
