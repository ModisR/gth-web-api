package v1.gth.parse

import v1.gth.init.ShermanConsonant

import scala.language.postfixOps

case class ConsonantStack(head: ShermanConsonant, decorations: Seq[ShermanConsonant.Decoration]) {
  override def toString: String = {
    val tail = decorations map ShermanConsonant.table(head.base)
    head.toString +: tail mkString
  }
}

object ConsonantStack {
  implicit val parse: Parse[ConsonantStack] =
    Parse[ShermanConsonant] flatMap { head =>
      Parse
        .many(Parse oneOf ShermanConsonant.table(head.base).map(_.swap))
        .map(ConsonantStack(head, _))
    }
}
