package v1.gth.parse

sealed abstract class ShermanChar(val asString: String)

object ShermanChar {
  private[parse] final case class LetterStack(
                                               consonants: Option[ConsonantStack],
                                               vowels: Option[VowelStack]
                                             ) extends ShermanChar(
    consonants.fold("")(_.toString) ++ vowels.fold("")(_.toString)
  )

  private[parse] case object Apostrophe extends ShermanChar("'")

  private[parse] case object Hyphen extends ShermanChar("-")

  private val parseLetterStack = new Parse[LetterStack]{ def apply(str: String): Parse.Output[LetterStack] = Parse Failure Seq("Unimplemented.") }
  private val parsePunctuation = Parse oneOf Seq(Apostrophe, Hyphen)

  implicit val parse: Parse[ShermanChar] = parseLetterStack orElse parsePunctuation
}