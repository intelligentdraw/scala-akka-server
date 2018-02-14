package controllers

import javax.inject._
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */

@Singleton
class BootstrapController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index = Assets.at("index.html")

  def index2(file: String) = {
    if (!file.contains("."))
      Assets.at("index.html")
    else
      Assets.at( file)
  }

  def index3(file: String) = Assets.at("index.html")

}
