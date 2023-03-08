package v1.gth.parse

final case class ShermanWord(chars: Seq[ShermanChar]) {
  def asString: String = chars.mkString
}

object ShermanWord {
  implicit val parse: Parse[ShermanWord] = Parse.seqOf[ShermanChar] map apply
}