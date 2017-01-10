package traits

import java.io.{File, PrintWriter}

import scala.io.Source

trait Util {
  def replaceInFile(arq: String, before: String, after: String, content: String): Unit = {
    val f2 = new File("/run/shm/lixo.tmp")
    val w = new PrintWriter(f2)
    Source.fromFile(arq).getLines.map {
      case x if x.contains(before) && x.contains(after) => x.replaceAll(s"$before.*$after", s"$before$content$after")
      case x => x
    }.foreach(x => w.println(x))
    w.close()
    f2.renameTo(new File(arq))
  }

  def replaceInFile(arq: String, old: String, neww: String): Unit = {
    val f2 = new File("/run/shm/lixo.tmp")
    val w = new PrintWriter(f2)
    Source.fromFile(arq).getLines.mkString("\n").replace(old, neww).split("\n").foreach(x => w.println(x))
    w.close()
    f2.renameTo(new File(arq))
  }
}
