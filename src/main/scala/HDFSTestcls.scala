//1.Test the spark environment by executing the spark’s HdfsTest.scala example

import org.apache.spark.sql.SparkSession

import java.util.concurrent.TimeUnit

object HDFSTestcls {
  def main(args: Array[String]): Unit = {
    if (args.length < 1) {
      System.err.println("Usage: HdfsTest <file>")
      System.exit(1)
    }
    val spark = SparkSession
      .builder
      .appName("HdfsTest")
      .getOrCreate()
    val file = spark.read.text(args(0)).rdd
    val mapped = file.map(s => s.length).cache()
    for (iter <- 1 to 10) {
      val startTimeNs = System.nanoTime()
      for (x <- mapped) {
        x + 2
      }
      val durationMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTimeNs)
      println(s"Iteration $iter took $durationMs ms")
    }
    println(s"File contents: ${file.map(_.toString).take(1).mkString(",").slice(0, 10)}")
    spark.stop()
  }
}


HDFSTestcls.main(Array("/user/linkinbarb10edu/sqoop_sql2"))



