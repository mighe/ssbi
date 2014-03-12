package it.mighe.ssbi.instructions;

import it.mighe.ssbi.Instruction;
import it.mighe.ssbi.Tape;

public class DecrementValueInstruction extends Instruction {

    public int execute(Tape tape, int programCounter) {
        tape.decrement();
        return programCounter + 1;
    }
}
