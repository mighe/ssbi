import it.mighe.ssbi.ProgramExecutor
import java.io.File

object Main extends App {

  if (args.isEmpty) { printUsage() }

  val programsExecutionTime = new scala.collection.mutable.LinkedHashMap[String, Long]

  for(programPath <- inputFiles) {

    Console.println("executing " + programPath.getName + "...")

    val program = scala.io.Source.fromFile(programPath).mkString
    val executor = new ProgramExecutor(System.out, System.in)
    val elapsedTime = benchmark { executor.execute(program) }

    programsExecutionTime(programPath.getName) = elapsedTime
  }

  printResults(programsExecutionTime)

  // -------

  private def benchmark(f: => Unit): Long = {
    val startTime = System.currentTimeMillis()
    f
    System.currentTimeMillis() - startTime
  }

  private def printUsage() {
    println("usage: scala Main <input file pattern>")
    println("examples:")
    println("  scala Main my_bf_program.b --> executes only my_bf_program.b")
    println("  scala Main my_folder --> executes all *.b programs in my_folder")
    System.exit(1)
  }

  private def inputFiles = {
    val file = new File(args(0))

    if (file.isFile) {
      Array(file)
    }
    else {
      file.listFiles().filter(_.getName.endsWith(".b"))
    }
  }

  private def printResults(results: scala.collection.mutable.Map[String, Long]) {
    Console.println()
    Console.println("Execution times:")
    results.foreach {
      case (program, time) => Console.println(s">>>> $program executed in $time ms")
    }

    val totalExecutionTime = results.valuesIterator.foldLeft(0L)(_ + _)
    Console.println()
    Console.println("-------------------------------------------")
    Console.println(s"Total execution time: $totalExecutionTime ms")
  }


}
