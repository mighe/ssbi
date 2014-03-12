package it.mighe.ssbi

abstract class Instruction {
  def execute(tape: Tape, programCounter: Int): Int
}
