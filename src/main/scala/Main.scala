package similarity

object Main {
  def main(args: Array[String]): Unit = {
    val reader = new Reader()
    if(reader.filesCount == 0) {
      println("Unable to load the dataset! please check the path.")
      return
    }

    val shingling = new Shingling(reader.textFiles, reader.filesCount)
    val compareSets = new CompareSets()
    shingling.run()

    (1 until reader.filesCount) foreach { i =>
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

    val minHashing = new MinHashing(shingling.booleanMatrix)
    val signaturesList = minHashing.computeSignature()

    println("Signatures List:")
    (0 until signaturesList.size) foreach { i =>
      println(signaturesList(i))
    }

    println("\nCompare Signatures")
    val compareSignatures = new CompareSignatures()
    (1 until reader.filesCount) foreach { i =>
      val similarity = compareSignatures.similarity(signaturesList(0), signaturesList(i))
      println(s"Similarity between document signature 1 and document ${i+1} is: ${similarity * 100}%")
    }
  }

  def toInt(s: String): Int = {
    try {
      s.toInt
    } catch {
      case e: Exception => 0
    }
  }
}
