package v1.gth.parse

import scala.language.postfixOps

sealed abstract class ShermanChar(override val toString: String)

/** START letters
  * START consonants */
final class ShermanConsonant(
                              val base: ConsonantBase,
                              val deco: ConsonantDeco,
                              toString: String
                            ) extends ShermanChar(toString)

sealed abstract class ConsonantBase

case object ArcMajor extends ConsonantBase

case object CircleIn extends ConsonantBase

case object ArcMinor extends ConsonantBase

case object CircleOut extends ConsonantBase

sealed abstract class ConsonantDeco

final case class ConsonantDots(num: Int) extends ConsonantDeco

final case class ConsonantLines(num: Int) extends ConsonantDeco

/** END consonants |
  * START vowels */

final class ShermanVowel(val base: VowelBase, val deco: VowelDeco, toString: String) extends ShermanChar(toString)

sealed abstract class VowelBase

case object VowelTop extends VowelBase

case object VowelMiddle extends VowelBase

case object VowelBottom extends VowelBase

sealed abstract class VowelDeco

case object NoDeco extends VowelDeco

case object LineUp extends VowelDeco

case object LineDown extends VowelDeco

/** END vowels |
  * END letters */
case object ShermanApostrophe extends ShermanChar("'")

case object ShermanHyphen extends ShermanChar("-")