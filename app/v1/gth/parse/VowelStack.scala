package v1.gth.parse

import play.api.libs.json.{Json, OWrites}
import v1.gth.init.{ShermanVowel, Stringifiable}

import scala.language.postfixOps

case class VowelStack(head: ShermanVowel, decorations: Seq[ShermanVowel.Decoration]) extends Stringifiable {
  override def asString: String = {
    val tail = decorations map ShermanVowel.table(head.base)
    head.asString +: tail mkString
  }
}

object VowelStack {
  implicit val parse: Parse[VowelStack] =
    Parse[ShermanVowel] flatMap { head =>
      Parse
        .seqOf(Parse oneOf ShermanVowel.table(head.base).map(_.swap))
        .map(VowelStack(head, _))
    }

  implicit val writes: OWrites[VowelStack] = Json.writes
}
