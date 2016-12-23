trait Arg extends App {
  val startsOfList: Seq[String]
  lazy val argsbool = args filter (x => x.endsWith("=y") || x.endsWith("=n"))
  lazy val argsnumb = args filter (x => x.split('=').last.filter(x => x != '.' && x != '-').forall(x => x.isDigit))
  lazy val argslist = args filter (x => x.contains(",") || startsOfList.exists(start => x.startsWith(start + "=")))
  lazy val argstext = args diff argsbool diff argsnumb diff argslist
}
