package v1.gth

import play.api.Logger
import play.api.libs.json.JsValue
import play.api.mvc._

import javax.inject.Inject
import scala.concurrent.ExecutionContext

class TranslationController @Inject()(val controllerComponents: ControllerComponents)(
    implicit ec: ExecutionContext)
    extends BaseController {

  private val logger = Logger(getClass)

  def translate: Action[JsValue] = Action(parse.json) { implicit request =>
    val input = (request.body \ "input").as[String]
    logger.error(s"Received request: $input")
    Ok {
      <svg width="640" height="360">
        <rect width="640" height="360" fill="black"></rect>
        <text x="16" y="180" fill="white" font-size="32px">Received request: {input}</text>
      </svg>
    }
  }
}
