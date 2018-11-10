package similarity

import scala.collection.mutable._
import java.io._
import java.nio.charset.CodingErrorAction
import util.control.Breaks._


class Shingling(k: Int = 9) {
  var hashedShingles: ListBuffer[Set[Int]] = ListBuffer()
  var filesCount: Int = 0
  val shingles = LinkedHashMap.empty[Int, Int]
  private[this] var shingleIndex: Int = 0

  def run(dir: String = "./dataset"): Unit = {

    val d = new File(dir)
    if (!d.exists || !d.isDirectory) {
      return // no dataset available
    }

    val files = d.listFiles.filter(file => !file.isHidden && file.isFile).toList.sortWith(_.getName.replaceAll("\\.[^.]*$", "").toInt < _.getName.replaceAll("\\.[^.]*$", "").toInt)
    implicit val codec = io.Codec("UTF-8")
    codec.onMalformedInput(CodingErrorAction.IGNORE)
    codec.onUnmappableCharacter(CodingErrorAction.IGNORE)

    files.foreach { file =>
      val source = scala.io.Source.fromFile(file)
      val text = try source.mkString finally source.close()
      hashedShingles += getShingles(text)
      filesCount += 1
    }


    println("Hashed shingling done")
    //hashedShingles.foreach(println)
    //shingles foreach { case (key, value) => println(key + "-->" + value) }

  }

  private def getShingles(text: String): Set[Int] = {
    var hashedShingle: Set[Int] = TreeSet()
    for (i <- 0 to text.length - k) {
      var shingle = text.substring(i, i + k)
      hashedShingle += shingle.hashCode
      shingles.getOrElseUpdate(shingle.hashCode, shingleIndex)
      shingleIndex += 1
      //println(s"loop1: $shingle is $i")
    }
    hashedShingle
  }
}
