package v1.gth.parse

sealed abstract class ShermanChar(val asString: String)

object ShermanChar {
  private[parse] final case class LetterStack(
                                               consonants: Option[ConsonantStack],
                                               vowels: Option[VowelStack]
                                             ) extends ShermanChar(???)

  private[parse] case object Apostrophe extends ShermanChar("'")

  private[parse] case object Hyphen extends ShermanChar("-")

  private val parseLetterStack = ??? : Parse[LetterStack]
  private val parsePunctuation = Parse oneOf Seq(Apostrophe, Hyphen)

  implicit val parse: Parse[ShermanChar] = parseLetterStack orElse parsePunctuation
}