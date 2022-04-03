package v1.gth

import play.api.Logger
import play.api.libs.json.JsValue
import play.api.mvc._

import javax.inject.Inject
import scala.language.postfixOps
import scala.xml.Utility

class TranslationController @Inject()(val controllerComponents: ControllerComponents)
  extends BaseController {

  private val logger = Logger(getClass)

  def translate: Action[JsValue] = Action(parse.json) { implicit request =>
    val input = (request.body \ "input").as[String]
    logger.error(s"Received request: $input")
    Ok(render(parseGallifreyan(input)))
  }

  private def render(gallifreyan: ShermanText, params: RenderingParams = RenderingParams()): xml.Node =
    Utility.trim {
      val totalMargin = params.margin + params.lineWidth

      val maxChars = gallifreyan.map(_.word.length).max
      val charWidthEstimate = params.fontSize / 2
      val wordRadius = maxChars * charWidthEstimate / 2 + totalMargin

      val n = gallifreyan.length
      val centreAngle = Math.PI / n
      val wordCentre = (wordRadius + totalMargin) / Math.sin(centreAngle)

      val outputRadius = wordCentre + wordRadius + totalMargin
      val canvasHalfSize = outputRadius + params.margin
      val canvasSize = 2 * canvasHalfSize

      val wordCircles = gallifreyan.zipWithIndex map { case (ShermanWord(word), k) =>
        val angle = 2 * Math.PI * k / n
        val cx = canvasHalfSize + wordCentre * Math.sin(angle) toString
        val cy = canvasHalfSize + wordCentre * Math.cos(angle) toString

          <circle cx={cx} cy={cy} r={wordRadius.toString}/>
          <text x={cx} y={cy} text-anchor="middle" dominant-baseline="central" fill="white" stroke-width="0">
            {word}
          </text>
      }

      val chs = canvasHalfSize.toString
      val cs = canvasSize.toString

      <svg viewBox={s"0 0 $cs $cs"} xmlns="http://www.w3.org/2000/svg">
        <g fill="black">
          <rect width={cs} height={cs}/>
          <g stroke="white" stroke-width={params.lineWidth.toString}>
            <circle cx={chs} cy={chs} r={outputRadius.toString}/>
            <g>
              {wordCircles}
            </g>
          </g>
        </g>
      </svg>
    }

  case class RenderingParams(
                              fontSize: Byte = 12,
                              margin: Byte = 5,
                              lineWidth: Byte = 1
                            )

  private def parseGallifreyan(input: String): ShermanText = input split "\\s+" map ShermanWord.apply toVector

  type ShermanText = Vector[ShermanWord]

  case class ShermanWord(word: String)
}
