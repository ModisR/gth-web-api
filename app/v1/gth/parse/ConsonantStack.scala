package v1.gth.parse

import v1.gth.init.ShermanConsonant

case class ConsonantStack(head: ShermanConsonant, decorations: Seq[ShermanConsonant.Decoration])

object ConsonantStack {
  implicit val parse: Parse[ConsonantStack] = ShermanConsonant.parse map (apply(_, Seq.empty))
}