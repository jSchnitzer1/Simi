package similarity

import scala.collection.mutable._
import util.control.Breaks._


class Shingling(filesText: List[String], filesCount: Int, k: Int = 3) {
  var hashedShingles: ListBuffer[Set[Int]] = ListBuffer()
  val shingles = LinkedHashMap.empty[Int, Int]
  var booleanMatrix: Array[Array[Int]] = null
  private[this] var shingleIndex: Int = 0

  /**
    * runs shingling functionality
    * @param dir is the directory of dataset
    */
  def run(): Unit = {
    filesText.foreach { text =>
      hashedShingles += getShingles(text)
    }
    println("Hashed shingling done")

    //hashedShingles.foreach(println)
    //shingles foreach { case (key, value) => println(key + "-->" + value) }
    //booleanMatrix foreach { row => row foreach print; println }
  }

  /**
    * This method gets and hashes shingles of a document
    * and return a set of hashed shingles
    * @param text represents the document
    * @return a TreeSet of hashed shingles
    */
  private def getShingles(text: String): Set[Int] = {
    var hashedShingle: Set[Int] = TreeSet()
    for (i <- 0 to text.length - k) {
      var shingle = text.substring(i, i + k)
      hashedShingle += shingle.hashCode
      shingles.getOrElseUpdate(shingleIndex, shingle.hashCode)
      shingleIndex += 1
      //println(s"loop1: $shingle is $i")
    }
    hashedShingle
  }

  /**
    * computes the boolean matrix of shingles for all documents
    * columns represent documents, rows are shingles
    * cell has value of either:
    * 1 (if shingle found in this document)
    * 0 (if shingle not found in this document)
    */
  def computeBoleanMatrix(): Unit = {
    booleanMatrix = Array.ofDim[Int](shingleIndex, filesCount)
    (0 until hashedShingles.size) foreach { j =>
      (0 until shingles.size) foreach { i =>
        val single: Int = shingles.getOrElse(i, 0)
        if(single != 0) {
          if(hashedShingles(j).contains(single))
            booleanMatrix(i)(j) = 1
          else
            booleanMatrix(i)(j) = 0
        }
      }
    }
  }

  /**
    * This method is for printing the boolean matrix of shingles
    * @param size is the size of matrix to be printed
    */
  def printBooleanMatrix(size:Int = -1): Unit = {
    if(booleanMatrix == null) return
    println("\nBoolean matrix (shingles of documents)")
    breakable {
      var count: Int = 0
      booleanMatrix foreach { row =>
        row foreach print; println
        count += 1
        if(count == size) {
          println("...")
          booleanMatrix(0).size
          println(s"Total documents (columns): ${booleanMatrix(0).size}, total shingles (rows): ${booleanMatrix.size}\n")
          break
        }
      }
    }
  }
}
