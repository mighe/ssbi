# SSBI

ssbi is a Simple Scala Brainfuck Interpreter, a simple educational project to learn Scala.

### Architecture
ssbi parses Brainfuck code and generates a list of executable `Instruction`s.
The `Instruction`s are passed to a code optimizer that reduces their number, then
the `ProgramExecutor` executes them.

At the moment ssbi does not rely on compiler theory, even if it could be very useful: consider this
as a precise architectural choice, *maybe* one day the project will switch to more formal algorithms.
For example, Optimizer uses a two pass algorithm: the first time it optimizes linear sequences, the second time
it optimizes loops. Using a *real* parser this can be done in one single pass.

### Contribute
I want to improve my Scala proficiency, so feel free to open an issue also to provide style suggestions.

If you want to add features:
- fork this repository
- add specs
- add code
- open a pull request

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

### License
All ssbi code is under MIT license