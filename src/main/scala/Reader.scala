package similarity

import java.io._
import java.nio.charset.CodingErrorAction

class Reader(dir: String = "./dataset") {
  /**
    * primary constructor in scala:
    * fetches all documents as text strings, as well as the count of files
    */
  var filesCount: Int = 0
  var textFiles: List[String] = List()

  private[this] val d = new File(dir)
  if (d.exists && d.isDirectory) {
    val files = d.listFiles.filter(file => !file.isHidden && file.isFile).toList.sortWith(_.getName.replaceAll("\\.[^.]*$", "").toInt < _.getName.replaceAll("\\.[^.]*$", "").toInt)

    implicit val codec = io.Codec("UTF-8")
    codec.onMalformedInput(CodingErrorAction.IGNORE)
    codec.onUnmappableCharacter(CodingErrorAction.IGNORE)

    files.foreach { file =>
      val source = scala.io.Source.fromFile(file)
      val text = try source.mkString finally source.close()
      textFiles = textFiles :+ text
      filesCount += 1
    }
  }
}
