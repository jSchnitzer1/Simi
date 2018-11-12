package similarity

import scala.collection.mutable.ListBuffer

object Main {
  var timingType: String = _
  def main(args: Array[String]): Unit = {
    val reader = new Reader()
    if (reader.filesCount == 0) {
      println("Unable to load the dataset! please check the path.")
      return
    }
    var shingling: Shingling = new Shingling(reader.textFiles, reader.filesCount)
    var compareSets = new CompareSets()
    shingling.run()

    timingType = "comparing sets"
    time {
      (1 until reader.filesCount) foreach { i =>
        val similarity = compareSets.jaccardSimilarity(shingling.hashedShingles(0), shingling.hashedShingles(i))
        println(s"Similarity between document 1 and document ${i + 1} is: ${similarity * 100}%")
      }
    }

    print("\nSelect two sets to compute jaccard similarity\nsetA: ")
    val set1 = shingling.hashedShingles(toInt(scala.io.StdIn.readLine()))
    print("setB: ")
    val set2 = shingling.hashedShingles(toInt(scala.io.StdIn.readLine()))
    println(s"Jaccard similarity between both sets is: ${compareSets.jaccardSimilarity(set1, set2) * 100}%")

    shingling.computeBoleanMatrix()
    shingling.printBooleanMatrix(15)

    var minHashing = new MinHashing(shingling.booleanMatrix)
    var signaturesList = minHashing.computeSignatures()

    println("Signatures List:")
    (0 until signaturesList.size) foreach { i =>
      println(signaturesList(i))
    }

    timingType = "comparing signature matrix"
    time {
      println("\nCompare Signatures")
      val compareSignatures = new CompareSignatures()
      (1 until reader.filesCount) foreach { i =>
        val similarity = compareSignatures.similarity(signaturesList(0), signaturesList(i))
        println(s"Similarity between document signature 1 and document ${i + 1} is: ${similarity * 100}%")
      }
    }
  }

  def toInt(s: String): Int = {
    try {
      s.toInt
    } catch {
      case e: Exception => 0
    }
  }

  def time[R](block: => R): R = {
    // http://biercoff.com/easily-measuring-code-execution-time-in-scala/
    val t0 = System.nanoTime()
    val result = block
    val t1 = System.nanoTime()
    val diff = (t1 - t0)
    println(s"Elapsed time for $timingType: ${diff.toString} ns ≈ ${(diff / 1000000).toString} ms")
    result
  }
}
