package similarity

object Main {
  def main(args: Array[String]): Unit = {
    val shingling = new Shingling()
    val compareSets = new CompareSets()
    shingling.run()

    (1 until shingling.filesCount) foreach { i =>
      val similarity = compareSets.jacardSimilarity(shingling.hashedShingles(0), shingling.hashedShingles(i))
      println(s"Similarity between document 1 and document ${i+1} is: ${similarity * 100}%")
    }
  }
}
