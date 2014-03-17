# SSBI

ssbi is a Simple Scala Brainfuck Interpreter, a simple educational project to learn Scala.

### Architecture
ssbi parses Brainfuck code and generates a list of executable `Instruction`s.
The `Instruction`s are passed to a code optimizer that reduces their number, then
the `ProgramExecutor` executes them.

At the moment ssbi does not rely on compiler theory, even if it could be very useful: consider this
as a precise architectural choice, *maybe* one day the project will switch to more formal algorithms.

### Credits

Sample programs from https://github.com/gokselgoktas/brainfuck/tree/master/examples

### Useful links

- https://code.google.com/p/esotope-bfc/wiki/Comparison
- http://cydathria.com/bf/bf_ex3.html
- http://www.bits-quark.org/2012/files/TheBrainfuckProgrammingLanguageTutorial.pdf
- http://esoteric.voxelperfect.net/files/brainfuck/src/
- http://sree.kotay.com/2013/02/implementing-brainfuck-part-2.html
- http://esolangs.org/wiki/Brainfuck_implementations
- http://esolangs.org/wiki/brainfuck
- http://nayuki.eigenstate.org/page/optimizing-brainfuck-compiler