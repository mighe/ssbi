package it.mighe.ssbi

class Tape {

  private var pointer = 0
  private val memory = new Array[Byte](30000)

  def increment() { memory(pointer) = (memory(pointer) + 1).toByte }
  def decrement() { memory(pointer) = (memory(pointer) - 1).toByte }
  def shiftRight() { pointer += 1 }
  def shiftLeft() { pointer -= 1 }
  def current = { memory(pointer) }
  def current_=(value: Int) { memory(pointer) = value.toByte }

  def at(position: Int) = { memory(position) }
  def pointerPosition = { pointer }

}
