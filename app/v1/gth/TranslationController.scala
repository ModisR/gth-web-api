package v1.gth

import play.api.Logger
import play.api.libs.json.JsValue
import play.api.mvc._
import v1.gth.parse.{Parse, ShermanWord}
import v1.gth.render.ShermanRenderer

import javax.inject.Inject
import scala.language.postfixOps

class TranslationController @Inject()(val controllerComponents: ControllerComponents,
                                      renderer: ShermanRenderer)
  extends BaseController {

  private val logger = Logger(getClass)

  private def parseWords(text: String): Iterable[ShermanWord] = text.toUpperCase.trim split "\\s+" map Parse.apply[ShermanWord] collect {
    case Parse.Success(_, result) => result
  }

  def translate: Action[JsValue] = Action(parse.json) { implicit request =>
    val input = (request.body \ "input").as[String]

    logger.error(s"Received request: $input")

    Ok(renderer.render(parseWords(input)))
  }
}
