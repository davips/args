package traits

import java.io.{File, FileWriter}

import scala.io.Source

trait Util {
  def replaceInFile(arq: String, before: String, after: String, content: String): Unit = {
    val fw = new FileWriter(s"$arq.tmp")
    Source.fromFile(arq).getLines.map {
      case x if x.contains(before) && x.contains(after) => x.replaceAll(s"$before.*$after", s"$before$content$after")
      case x => x
    }.foreach(x => fw.write(s"$x\n"))
    fw.close()
    new File(s"$arq.tmp").renameTo(new File(arq))
  }

  def replaceInFile(arq: String, old: String, neww: String): Unit = {
    val fw = new FileWriter(s"$arq.tmp")
    Source.fromFile(arq).getLines.mkString("\n").replace(old, neww).split("\n").foreach(x => fw.write(s"$x\n"))
    fw.close()
    new File(s"$arq.tmp").renameTo(new File(arq))
  }
}
