### 0.x - simple interpreter

#### 0.1-dumb

dumbest implementation, every time rescan the whole program to find the matching bracket

#### 0.2-map_jump_cache

keeps an internal cache to map bracket pairs, so the program is scanned only the first time a bracket is found

#### 0.3-map_jump_precache

keeps an internal cache to map bracket pairs with entries computed before program execution

#### 0.4-array_jump_precache

keeps an internal array cache to map bracket pairs with entries computed before program execution

#### 0.5-instruction_on_array

parses source code and create an array of instructions. Every instruction has an `execute(tape, programCounter)` method that returns the updated program counter.
This implementation is slower that the previous one, surprisingly.

#### 0.6-linked_instructions
parses source code and create a linked list of instruction. Every instruction has an `execute(tape, programCounter)` method that returns the next instruction.
Since instructions are built in two steps, they are not fully immutable, but the reference to the following instruction is added later.
A matching bracket optimization has been implemented as well

### 1.x - Optimizing interpreter

#### 1.0-fusion_and_zeros
the optimizer takes instructions and tries to minimize their amount.
Consecutive increments and shifts are fused, [-] pattern is transformed into a set zero instruction

#### 1.1-linear_sequences_and_loops
the optimizer tries to simplify contiguous memory operations, leaving the pointer shift operation in tail,
this way many operations can be saved.
When a loop body contains no pointer shift operations, the base cell is decremented every time and
contains only sums, it is called *linear loop* and every operation can be transformed in a multiplication.
For example
`[- > +++ >> -- <<<]`

means

```
if p[base] != 0 {
    p[base + 1] = p[base + 1] + 3 * p[base]
    p[base + 3] = p[base + 3] - 2 * p[base]
    p[base] = 0
}
```