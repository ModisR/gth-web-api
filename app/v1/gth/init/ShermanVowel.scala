package v1.gth.init

import v1.gth.init.ShermanVowel.{Base, Decoration}
import v1.gth.parse.Parse

import scala.collection.immutable

final case class ShermanVowel(base: Base, decoration: Decoration, override val toString: String)

object ShermanVowel {
  private val table = Map(
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

  val values: immutable.Iterable[ShermanVowel] = for {
    (base, row) <- table
    (deco, str) <- row
  } yield new ShermanVowel(base, deco, str)

  implicit val parse: Parse[ShermanVowel] = Parse oneOf values

  sealed abstract class Base

  private[init] case object Top extends Base

  private[init] case object Middle extends Base

  private[init] case object Bottom extends Base

  sealed abstract class Decoration

  private[init] case object Plain extends Decoration

  private[init] case object LineUp extends Decoration

  private[init] case object LineDown extends Decoration
}