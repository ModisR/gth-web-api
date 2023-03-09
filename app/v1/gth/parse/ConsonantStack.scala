package v1.gth.parse

import play.api.libs.json.{Json, OWrites}
import v1.gth.init.{ShermanConsonant, Stringifiable}

import scala.language.postfixOps

final case class ConsonantStack(head: ShermanConsonant, decorations: Seq[ShermanConsonant.Decoration])
  extends Stringifiable {
  override def asString: String = {
    val tail = decorations map ShermanConsonant.table(head.base)
    head.asString +: tail mkString
  }
}

object ConsonantStack {
  implicit val parse: Parse[ConsonantStack] =
    Parse[ShermanConsonant] flatMap { head =>
      Parse
        .seqOf(Parse oneOf ShermanConsonant.table(head.base).map(_.swap))
        .map(ConsonantStack(head, _))
    }

  implicit val writes: OWrites[ConsonantStack] = Json.writes
}
