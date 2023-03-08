package v1.gth.parse

import v1.gth.init.ShermanVowel

import scala.language.postfixOps

case class VowelStack(head: ShermanVowel, decorations: Seq[ShermanVowel.Decoration]) {
  override def toString: String = {
    val tail = decorations map ShermanVowel.table(head.base)
    head.toString +: tail mkString
  }
}

object VowelStack {
  implicit val parse: Parse[VowelStack] =
    Parse[ShermanVowel] flatMap { head =>
      Parse
        .many(Parse oneOf ShermanVowel.table(head.base).map(_.swap))
        .map(VowelStack(head, _))
    }
}
