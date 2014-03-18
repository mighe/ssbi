package it.mighe.ssbi

import org.scalatest.{BeforeAndAfter, Matchers, FlatSpec}

class TapeSpec extends FlatSpec with Matchers with BeforeAndAfter {

  var tape: Tape = _

  before {
    tape = new Tape
  }

  it should "have cells initialized at zero" in {
    tape.at(0) should be(0)
    tape.at(29999) should be(0)
  }

  it should "have pointer initialized to zero" in {
    tape.pointerPosition should be(0)
  }

  it should "increment pointer" in {
    tape.shiftRight()
    tape.pointerPosition should be(1)
  }

  it should "decrement pointer" in {
    tape.shiftRight()
    tape.shiftLeft()
    tape.pointerPosition should be(0)
  }

  it should "increment current cell" in {
    tape.shiftRight()
    tape.increment()

    tape.at(1) should be(1)
  }

  it should "decrement current cell" in {
    tape.increment()
    tape.decrement()

    tape.at(0) should be(0)
  }

  it should "wrap value" in {
    tape.decrement()

    tape.current should be(255.toByte)

    tape.increment()
    tape.current should be(0)
  }

  it should "assign current value" in {
    tape.current = 5
    tape.current should be(5)
  }

  it should "wrap current value" in {
    tape.current = 257
    tape.current should be(1)
  }

  it should "adjust current value without offset" in {
    tape.adjustValue(50)

    tape.current should be(50)

    tape.adjustValue(-10)
    tape.current should be(40)
  }

  it should "adjust current value with offset" in {
    tape.adjustPointer(10)

    tape.adjustValue(-10, -3)
    tape.adjustValue(50, 2)

    tape.at(7) should be(-10)
    tape.current should be(0)
    tape.at(12) should be(50)


  }

  it should "adjust pointer position" in {
    tape.shiftRight()
    tape.adjustPointer(27)


    tape.pointerPosition should be(28)

    tape.adjustPointer(-5)

    tape.pointerPosition should be(23)
  }

}
