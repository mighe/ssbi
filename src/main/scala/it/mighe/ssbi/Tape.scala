package it.mighe.ssbi

class Tape {

  private var pointer = 0
  private val memory = new Array[Byte](30000)

  def increment() { adjustValue(1) }
  def decrement() { adjustValue(-1) }
  def adjustValue(valueAdjustment: Int, pointerOffset: Int = 0) {
    memory(pointer + pointerOffset) = (memory(pointer + pointerOffset) + valueAdjustment).toByte
  }

  def shiftRight() { adjustPointer(1) }
  def shiftLeft() { adjustPointer(-1) }
  def adjustPointer(offset: Int) { pointer += offset }

  def current = { memory(pointer) }
  def current_=(value: Int) { memory(pointer) = value.toByte }

  def at(position: Int) = { memory(position) }
  def setAt(value: Int, position: Int) { memory(position) = value.toByte }
  def pointerPosition = { pointer }

}
