package v1.gth.render

import v1.gth.parse.{ShermanChar, ShermanWord}

import javax.inject.{Inject, Singleton}
import scala.language.postfixOps
import scala.xml.Utility

@Singleton
class ShermanRenderer @Inject()() {

  def render(gallifreyan: Iterable[ShermanWord], params: RenderingParams = RenderingParams()): xml.Node =
    Utility.trim {
      val totalMargin = params.margin + params.lineWidth
      val charRadius = params.fontSize / 2 + totalMargin

      def getContentPos(n: Int, innerRadius: Double) = n match {
        case 1 => 0
        case _ => (innerRadius + totalMargin) / Math.sin(Math.PI / n)
      }

      val maxChars = gallifreyan.map(_.chars.length).max
      val charPos = getContentPos(maxChars, charRadius)

      val wordRadius = charPos + charRadius + totalMargin
      val wordPos = getContentPos(gallifreyan.size, wordRadius)

      val sentenceRadius = wordPos + wordRadius + totalMargin
      val canvasHalfSize = sentenceRadius + params.margin
      val canvasSize = 2 * canvasHalfSize

      def renderChar(cx: Double, cy: Double, char: ShermanChar) = {
        val cxs = cx.toString
        val cys = cy.toString
          <circle cx={cxs} cy={cys} r={charRadius.toString}/>
          <text x={cxs} y={cys} text-anchor="middle" dy="0.375em">
            {char.toString}
          </text>
      }

      def renderWord(cx: Double, cy: Double, word: ShermanWord) = {
        val ang = 2 * Math.PI / word.chars.length
        val chars = word.chars.zipWithIndex map {
          case (char, k) =>
            renderChar(
              cx + charPos * Math.sin(k * ang),
              cy + charPos * Math.cos(k * ang),
              char
            )
        }
          <circle cx={cx.toString} cy={cy.toString} r={wordRadius.toString}/>
          <g>
            {chars}
          </g>
      }

      val wordCircles = gallifreyan.zipWithIndex flatMap {
        case (word, k) =>
          val ang = 2 * Math.PI / gallifreyan.size
          renderWord(
            canvasHalfSize + wordPos * Math.sin(k * ang),
            canvasHalfSize + wordPos * Math.cos(k * ang),
            word
          )
      }

      val chs = canvasHalfSize.toString
      val cs = canvasSize.toString

      <svg viewBox={s"0 0 $cs $cs"} xmlns="http://www.w3.org/2000/svg">
        <rect width={cs} height={cs} fill="black"/>
        <g stroke="white" stroke-width={params.lineWidth.toString}>
          <circle cx={chs} cy={chs} r={sentenceRadius.toString}/>
          <g>
            {wordCircles}
          </g>
        </g>
      </svg>
    }
}
