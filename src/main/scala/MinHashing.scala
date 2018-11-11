package similarity

import scala.collection.mutable._

class MinHashing(booleanMatrix: Array[Array[Int]], sSize: Int = 500) {
  val r = scala.util.Random
  val a: List[Int] = List.fill(sSize)(r.nextInt(sSize))
  val b: List[Int] = List.fill(sSize)(2*r.nextInt(sSize) + 1)

  def computeSignature(): ListBuffer[ListBuffer[Int]] = {
    val listOfSignatures: ListBuffer[ListBuffer[Int]] = ListBuffer()
    var rowId: Int = 0

    (0 until booleanMatrix(0).size) foreach { i =>
      val signatures = ListBuffer.fill(sSize)(Int.MaxValue)
      val docBooleanList = getCol(i, booleanMatrix)
      (0 to docBooleanList.size) foreach { j =>
        if(j == 1) {
          (0 until signatures.size) foreach { x =>
            val minSignature = Math.min(signatures(x), (a(x) * rowId + b(x)) % booleanMatrix.size) //h(x) = (ax+b)%c
            signatures(x) = minSignature
          }
        }
        rowId += 1
      }
      listOfSignatures += signatures
    }
    listOfSignatures
  }

  /**
    * get boolean list of a document from boolean matrix of shingles
    * @param col # of column
    * @param matrix the boolean matrix of shingles
    * @return the list of boolean, 1 if shingle presents, 0 if shingle does not present
    */
  private def getCol(col: Int, matrix: Array[Array[Int]]) = matrix.map{_(col)}.toList
}
