package controllers

import javax.inject._
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */

@Singleton
class BootstrapController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index = Assets.at( "/public/javascripts", "index.html")

  def index2(file: String) = Assets.at("/public/javascripts/", file)

  def index3(path1: String, path2: String) = Assets.at("/public/javascripts/" + path1, path2)

}
