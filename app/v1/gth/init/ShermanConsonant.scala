package v1.gth.init

import v1.gth.init.ShermanConsonant.{Base, Decoration}
import v1.gth.parse.Parse

final case class ShermanConsonant(base: Base, deco: Decoration, override val toString: String)

object ShermanConsonant {
  val table: Map[Base, Map[Decoration, String]] = Map(
    ArcMajor -> Map(
      Dots(0) -> "B",
      Dots(2) -> "CH",
      Dots(3) -> "D",
      Lines(1) -> "G",
      Lines(2) -> "H",
      Lines(3) -> "F"
    ),
    CircleIn -> Map(
      Dots(0) -> "J",
      Dots(1) -> "PH",
      Dots(2) -> "K",
      Dots(3) -> "L",
      Dots(4) -> "C",
      Lines(1) -> "N",
      Lines(2) -> "P",
      Lines(3) -> "M"
    ),
    ArcMinor -> Map(
      Dots(0) -> "T",
      Dots(1) -> "WH",
      Dots(2) -> "SH",
      Dots(3) -> "R",
      Lines(1) -> "V",
      Lines(2) -> "W",
      Lines(3) -> "S"
    ),
    CircleOut -> Map(
      Dots(0) -> "TH",
      Dots(1) -> "GH",
      Dots(2) -> "Y",
      Dots(3) -> "Z",
      Dots(4) -> "Q",
      Lines(1) -> "QU",
      Lines(2) -> "X",
      Lines(3) -> "NG"
    )
  )

  val values: Iterable[ShermanConsonant] = for {
    (base, row) <- table
    (deco, str) <- row
  } yield new ShermanConsonant(base, deco, str)

  implicit val parse: Parse[ShermanConsonant] = Parse oneOf values

  sealed abstract class Base

  private[init] case object ArcMajor extends Base

  private[init] case object CircleIn extends Base

  private[init] case object ArcMinor extends Base

  private[init] case object CircleOut extends Base

  sealed abstract class Decoration

  private[init] final case class Dots(num: Int) extends Decoration

  private[init] final case class Lines(num: Int) extends Decoration
}