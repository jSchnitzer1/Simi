package similarity

import scala.collection.mutable._

class CompareSets {
  def jacardSimilarity(setA: Set[Int], setB: Set[Int]): Double = {
    val intersection: Double = (setA intersect setB).size
    val union: Double = (setA union setB).size
    return BigDecimal(intersection / union).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  }
}
