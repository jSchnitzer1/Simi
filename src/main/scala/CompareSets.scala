package similarity

import scala.collection.mutable._

class CompareSets() {

  /**
    * computes jaccard similarity between two sets
    * @param setA first set
    * @param setB second set
    * @return return similarity percentage
    */
  def jaccardSimilarity(setA: Set[Int], setB: Set[Int]): Double = {
    val intersection: Double = (setA intersect setB).size
    val union: Double = (setA union setB).size
    BigDecimal(intersection / union).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  }
}
