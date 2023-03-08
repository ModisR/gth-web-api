package v1.gth

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

    println(s"Received request: $input")

    Ok(renderer.render(parseWords(input)))
  }
}
