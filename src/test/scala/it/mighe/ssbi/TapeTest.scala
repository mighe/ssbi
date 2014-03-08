package it.mighe.ssbi

import org.scalatest.{Matchers, FlatSpec}

class TapeTest extends FlatSpec with Matchers {

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

}
