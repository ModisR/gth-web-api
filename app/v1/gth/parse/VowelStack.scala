package v1.gth.parse

import v1.gth.init.ShermanVowel

case class VowelStack(head: ShermanVowel, decorations: Seq[ShermanVowel.Decoration])

object VowelStack {
  implicit val parse: Parse[VowelStack] = ShermanVowel.parse map (apply(_, Seq.empty))
}
