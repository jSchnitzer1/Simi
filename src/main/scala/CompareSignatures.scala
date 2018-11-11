package similarity

import scala.collection.mutable.ListBuffer

class CompareSignatures {

  /**
    * compare similarity based on signatures of two signature lists
    * @param sA list of signature A
    * @param sB list of signature B
    * @return similarity
    */
  def similarity(sA: ListBuffer[Int], sB: ListBuffer[Int]): Double = {
    val intersection: Double = (sA intersect sB).size
    val union: Double = sA.size
    BigDecimal(intersection / union).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  }
}
