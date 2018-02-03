package controllers

import javax.inject.{Inject, Singleton}

import models.Drawing
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import play.api.mvc._

@Singleton
class DrawingController  @Inject()(cc: ControllerComponents) extends AbstractController(cc){

  def getDrawings = Action{

    val drawings = Drawing.findAll

    Ok(Json.toJson(drawings))
  }
}
