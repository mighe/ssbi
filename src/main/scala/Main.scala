import it.mighe.ssbi.ProgramExecutor

object Main extends App {

  def benchmark(f: => Unit): Long = {
    val startTime = System.currentTimeMillis()
    f
    System.currentTimeMillis() - startTime
  }

  if (args.isEmpty) {
    println("usage: scala Main <inputFileName>")
    System.exit(1)
  }

  val fileName = args(0)
  val program = scala.io.Source.fromFile(fileName).mkString

  val executor = new ProgramExecutor(System.out, System.in)

  val elapsedTime = benchmark { executor.execute(program) }
  
  Console.println()
  Console.println("Program correctly executed in " + elapsedTime + " ms")

}
