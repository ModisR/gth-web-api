package v1.gth.parse

final case class ShermanWord(chars: Seq[ShermanChar]) {
  def asString: String = chars.mkString
}