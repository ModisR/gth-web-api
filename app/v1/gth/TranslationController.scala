package v1.gth

import play.api.libs.json.Json
import play.api.mvc._
import v1.gth.parse.{Parse, ShermanChar}
import v1.gth.render.ShermanRenderer

import javax.inject.Inject
import scala.language.postfixOps

class TranslationController @Inject()(val controllerComponents: ControllerComponents,
                                      renderer: ShermanRenderer)
  extends BaseController {

  private def parseWords(text: String): Array[Seq[ShermanChar]] =
    text.toUpperCase.trim split "\\s+" map Parse.seqOf[ShermanChar] collect {
      case Parse.Success(_, result) => result
    }

  def translate(input: String): Action[AnyContent] = Action {
    Ok(renderer.render(parseWords(input)))
  }

  def json(input: String): Action[AnyContent] = Action {
    Ok(Json toJson parseWords(input))
  }
}
