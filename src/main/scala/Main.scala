package similarity

object Main {
  def main(args: Array[String]): Unit = {
    val shingling = new Shingling
    val compareSets = new CompareSets()
    shingling.run()

    (1 until shingling.filesCount) foreach { i =>
      val similarity = compareSets.jaccardSimilarity(shingling.hashedShingles(0), shingling.hashedShingles(i))
      println(s"Similarity between document 1 and document ${i+1} is: ${similarity * 100}%")
    }

    print("\nSelect two sets to compute jaccard similarity\nsetA: ")
    val set1 = shingling.hashedShingles(toInt(scala.io.StdIn.readLine()))
    print("setB: ")
    val set2 = shingling.hashedShingles(toInt(scala.io.StdIn.readLine()))
    println(s"Jaccard similarity between both sets is: ${compareSets.jaccardSimilarity(set1, set2) * 100}%")

    shingling.computeBoleanMatrix()
    shingling.printBooleanMatrix(15)
  }

  def toInt(s: String): Int = {
    try {
      s.toInt
    } catch {
      case e: Exception => 0
    }
  }
}
