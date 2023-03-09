package v1.gth.parse

import play.api.libs.json.{Json, OWrites}
import v1.gth.init.Stringifiable

import scala.language.postfixOps

sealed abstract class ShermanChar(override val asString: String) extends Stringifiable

object ShermanChar {
  final case class LetterStack(
                                consonants: Option[ConsonantStack],
                                vowels: Option[VowelStack]
                              ) extends ShermanChar(
    consonants.fold("")(_.asString) ++ vowels.fold("")(_.asString)
  )

  case object Apostrophe extends ShermanChar("'")

  case object Hyphen extends ShermanChar("-")

  private val parseLetterStack: Parse[LetterStack] =
    Parse[Option[ConsonantStack]] flatMap {
      consonants =>
        Parse[Option[VowelStack]] map {
          vowels =>
            LetterStack(consonants, vowels)
        }
    } andThen {
      case Parse.Success(_, LetterStack(None, None)) => Parse.Failure(Seq("Unable to parse LetterStack."))
      case other => other
    } apply _

  private val parsePunctuation = Parse oneOf Seq(Apostrophe, Hyphen)

  implicit val parse: Parse[ShermanChar] = parseLetterStack orElse parsePunctuation

  implicit val writes: OWrites[ShermanChar] = {
    case ls: LetterStack => Json.writes[LetterStack] writes ls
    case Hyphen => Json.obj("punctuation" -> "Hyphen")
    case Apostrophe => Json.obj("punctuation" -> "Apostrophe")
  }
}