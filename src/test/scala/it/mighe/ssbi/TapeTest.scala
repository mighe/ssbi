package it.mighe.ssbi

import org.scalatest.{Matchers, FlatSpec}

class TapeTest extends FlatSpec with Matchers {

  it should "have cells initialized at zero" in {
    val tape = new Tape
    tape.at(0) should be(0)
    tape.at(29999) should be(0)
  }

}
