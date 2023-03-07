package v1.gth.parse

import scala.language.postfixOps

sealed abstract class ShermanChar(override val toString: String)

object ShermanChar {
  private val parseConsonant: Parse[Consonant] = (Parse oneOf LetterTables.consonants2) orElse (Parse oneOf LetterTables.consonants1)
  private val parseVowel: Parse[Vowel] = Parse oneOf LetterTables.vowels
  private val parseNonLetter: Parse[ShermanChar] = Parse oneOf Seq(Apostrophe, Hyphen)

  implicit val parse: Parse[ShermanChar] = parseConsonant orElse parseVowel orElse parseNonLetter

  /** START letters
   * START consonants */
  private[parse] final class Consonant(
                                        val base: ConsonantBase,
                                        val deco: ConsonantDeco,
                                        toString: String
                                      ) extends ShermanChar(toString)

  private[parse] sealed abstract class ConsonantBase

  private[parse] case object ArcMajor extends ConsonantBase

  private[parse] case object CircleIn extends ConsonantBase

  private[parse] case object ArcMinor extends ConsonantBase

  private[parse] case object CircleOut extends ConsonantBase

  abstract class ConsonantDeco

  private[parse] final case class ConsonantDots(num: Int) extends ConsonantDeco

  private[parse] final case class ConsonantLines(num: Int) extends ConsonantDeco

  /** END consonants |
   * START vowels */

  final class Vowel(val base: VowelBase, val deco: VowelDeco, toString: String) extends ShermanChar(toString)

  sealed abstract class VowelBase

  private[parse] case object VowelTop extends VowelBase

  private[parse] case object VowelMiddle extends VowelBase

  private[parse] case object VowelBottom extends VowelBase

  sealed abstract class VowelDeco

  private[parse] case object NoDeco extends VowelDeco

  private[parse] case object LineUp extends VowelDeco

  private[parse] case object LineDown extends VowelDeco

  /** END vowels |
   * END letters */
  private[parse] case object Apostrophe extends ShermanChar("'")

  private[parse] case object Hyphen extends ShermanChar("-")
}