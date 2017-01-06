import java.io.Serializable

import scala.language.postfixOps

trait Arg extends App {
  private lazy val argsbool0 = args filter (x => x.endsWith("=y") || x.endsWith("=n"))
  private lazy val argsnumb0 = args filter (x => x.split('=').last.filter(x => x != '.' && x != '-').forall(x => x.isDigit))
  private lazy val argslist0 = args filter (x => x.contains(","))
  private lazy val argstext0 = args //diff argsbool0 diff argsnumb0 diff argslist0

  lazy val argsall = args map parse toMap
  lazy val argsbool1 = argsbool0 map parse toMap
  lazy val argsnumb1 = argsnumb0 map parse toMap
  lazy val argslist1 = argsall.toList map { case (k, v) => k -> v.split(',').toList } toMap
  lazy val argstext1 = argstext0 map parse toMap

  lazy val consistent = args.length == (args map parse).map(_._1).distinct.length

  def catchError[T](argsmap: Map[String, T], k: String): T = if (consistent) argsmap.getOrElse(k, {
    if (argsall.contains(k)) println(s"Invalid value '${argsall(k)}' for argument '$k'.")
    else println(s"Missing argument '$k'.")
    sys.exit(0)
  }) else {
    println(s"Inconsistent list of arguments '${args.mkString("\n")}'.")
    sys.exit(0)
  }

  def argsbool(k: String): String = catchError(argsbool1, k)

  def argsnumb(k: String): String = catchError(argsnumb1, k)

  def argslist(k: String): Serializable = catchError(argslist1, k)

  def argstext(k: String): String = catchError(argstext1, k)

  def parse(s: String): (String, String) = {
    val Seq(a, b) = s.split('=').toSeq
    a -> b
  }
}
