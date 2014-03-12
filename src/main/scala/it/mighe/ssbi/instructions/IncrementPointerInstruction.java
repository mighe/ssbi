package it.mighe.ssbi.instructions;

import it.mighe.ssbi.Instruction;
import it.mighe.ssbi.Tape;

public class IncrementPointerInstruction extends Instruction {

    public int execute(Tape tape, int programCounter) {
        tape.shiftRight();
        return programCounter + 1;
    }
}
