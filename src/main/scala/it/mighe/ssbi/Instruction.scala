package it.mighe.ssbi

abstract class Instruction {
  var next: Instruction = null

  def execute(tape: Tape, programCounter: Int): Int
  def execute(tape: Tape): Instruction
}
