package v1.post

import io.lemonlabs.uri.typesafe.dsl.pathPartToUrlDsl
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

import javax.inject.Inject

/**
  * Routes and URLs to the PostResource controller.
  */
class PostRouter @Inject()(controller: PostController) extends SimpleRouter {
  val prefix = "/v1/posts"

  def link(id: PostId): String = {
    val url = prefix / id.toString
    url.toString()
  }

  override def routes: Routes = {
    case GET(p"/") =>
      controller.index

    case POST(p"/") =>
      controller.process

    case GET(p"/$id") =>
      controller.show(id)
  }

}
