package it.mighe.ssbi.instructions;

import it.mighe.ssbi.Instruction;
import it.mighe.ssbi.Tape;

public class DecrementPointerInstruction extends Instruction {

    public int execute(Tape tape, int programCounter) {
        tape.shiftLeft();
        return programCounter + 1;
    }
}
