package it.mighe.ssbi

import org.scalatest.{Matchers, FlatSpec}

class TapeSpec extends FlatSpec with Matchers {

  def fixture = new {
    val tape = new Tape
  }

  it should "have cells initialized at zero" in {
    val f = fixture

    f.tape.at(0) should be(0)
    f.tape.at(29999) should be(0)
  }

  it should "have pointer initialized to zero" in {
    val f = fixture
    f.tape.pointerPosition should be(0)
  }

  it should "increment pointer" in {
    val f = fixture
    f.tape.shiftRight()
    f.tape.pointerPosition should be(1)
  }

  it should "decrement pointer" in {
    val f = fixture
    f.tape.shiftRight()
    f.tape.shiftLeft()
    f.tape.pointerPosition should be(0)
  }

  it should "increment current cell" in {
    val f = fixture
    f.tape.shiftRight()
    f.tape.increment()

    f.tape.at(1) should be(1)
  }

  it should "decrement current cell" in {
    val f = fixture
    f.tape.increment()
    f.tape.decrement()

    f.tape.at(0) should be(0)
  }

  it should "wrap value" in {
    val f = fixture
    f.tape.decrement()

    f.tape.current should be(255.toByte)

    f.tape.increment()
    f.tape.current should be(0)
  }

  it should "assign current value" in {
    val f = fixture

    f.tape.current = 5
    f.tape.current should be(5)
  }

  it should "wrap current value" in {
    val f = fixture

    f.tape.current = 257
    f.tape.current should be(1)
  }

  it should "adjust current value without offset" in {
    val f = fixture
    f.tape.adjustValue(50)

    f.tape.current should be(50)

    f.tape.adjustValue(-10)
    f.tape.current should be(40)
  }

  it should "adjust current value with offset" in {
    val f = fixture
    f.tape.adjustPointer(10)

    f.tape.adjustValue(-10, -3)
    f.tape.adjustValue(50, 2)

    f.tape.at(7) should be(-10)
    f.tape.current should be(0)
    f.tape.at(12) should be(50)


  }

  it should "adjust pointer position" in {
    val f = fixture
    f.tape.shiftRight()
    f.tape.adjustPointer(27)


    f.tape.pointerPosition should be(28)

    f.tape.adjustPointer(-5)

    f.tape.pointerPosition should be(23)
  }

}
