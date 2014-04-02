# BENCHMARKS

## Test machine

MacBook Pro Retina 15" Late 2013
  Processor Name: Intel Core i7
  Processor Speed: 2,3 GHz
  Memory: 16 GB
  Hard Drive: SSD
  OS: OS X 10.9.2

java version 1.7.0_51
scala version 2.10.3

## Test suite

Programs are always executed in same order

- another_bench.b
- bench.b
- bench2.b
- bottles.b
- hanoi.b
- hello.b
- mandel.b
- squares.b

## Total execution times


| ssbi tag                       | total time ms |
|--------------------------------|---------------|
| 0.1-dumb                       |       254762  |
| 0.2-map_jump_cache             |       211838  |
| 0.3-map_jump_precache          |       162411  |
| 0.4-array_jump_precache        |        92521  |
| 0.5-instruction_on_array       |       122431  |
| 0.6-linked_instructions        |        86835  |
| 1.0-fusion_and_zeros           |        18492  |
| 1.1-linear_sequences_and_loops |        13786  |