package v1.gth.init

import v1.gth.init.ShermanVowel.{Base, Decoration}
import v1.gth.parse.Parse

final case class ShermanVowel(base: Base, decoration: Decoration, override val toString: String)

object ShermanVowel {
  val table: Map[Base, Map[Decoration, String]] = Map(
    Top -> Map(
      Plain -> "O"
    ),
    Middle -> Map(
      Plain -> "E",
      LineUp -> "I",
      LineDown -> "U"
    ),
    Bottom -> Map(
      Plain -> "A"
    )
  )

  val values: Iterable[ShermanVowel] = for {
    (base, row) <- table
    (deco, str) <- row
  } yield ShermanVowel(base, deco, str)

  implicit val parse: Parse[ShermanVowel] = Parse oneOf values

  sealed abstract class Base

  private[init] case object Top extends Base

  private[init] case object Middle extends Base

  private[init] case object Bottom extends Base

  sealed abstract class Decoration

  private case object Plain extends Decoration

  private[init] case object LineUp extends Decoration

  private[init] case object LineDown extends Decoration
}