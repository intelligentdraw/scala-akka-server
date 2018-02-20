package controllers

import java.awt.{Color, Graphics2D}
import java.awt.geom.Arc2D
import java.io.ByteArrayOutputStream
import javax.inject.{Inject, Singleton}

import helpers.GraphicsHelper
import models._
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import play.api.mvc._

@Singleton
class DrawingController  @Inject()(cc: ControllerComponents) extends AbstractController(cc){

  var usecaseDiagrams = UsecaseDiagrams.createForTesting();

  def getDrawings = Action{
    val drawings = Drawing.findAll
    Ok(Json.toJson(drawings))
  }

  def getDrawing(id: String) = Action{
    val drawing = Drawing.findAll
    Ok(Json.toJson(drawing))
  }

  def getDrawingMetadata(id: String) = Action{
    val drawing = Drawing.findAll
   // val drawing:DrawingMetadata = DrawingMetadata.find(id)
    Ok(Json.toJson(drawing))
  }

  /**
    * Get the binary for the use case diagram
    * @param id
    * @return
    */
  def getUsecaseDiagramImageBinary(id: String) = Action{

    val usecaseDiag:UsecaseDiagram = usecaseDiagrams.usecaseDiags(id)

    val imageAndGraphic2D = GraphicsHelper.drawDiagramPanel(usecaseDiag)

    val actorLocations = Location.createActorLocation(usecaseDiag)

    val bubbleLocations = Location.createBubbleLocations(usecaseDiag)

    actorLocations.foreach(actorLocation =>{
      GraphicsHelper.createUsecaseActor(imageAndGraphic2D._2, actorLocation.left, actorLocation.top, actorLocation.desc)
    } )


    bubbleLocations.foreach(location => {
        GraphicsHelper.createUsecaseBubble(imageAndGraphic2D._2, location.left, location.top, location.desc)
      }
    )

    //link the actors to their own bubbles
    actorLocations.foreach(actorLocation=>{
        usecaseDiag.actorsAndUsecases.keys.foreach(actor=>
          if (actorLocation.desc.equals(actor.name)){
            usecaseDiag.actorsAndUsecases(actor).foreach(bubble => {
              bubbleLocations.foreach(bubbleLocation => {
                if (bubble.desc.equals(bubbleLocation.desc)){
                  GraphicsHelper.linkActorToUsecase(imageAndGraphic2D._2, actorLocation, bubbleLocation)
                }
              })
            })
          }
        )
      }
    )





    val baos = new ByteArrayOutputStream()
    import javax.imageio.ImageIO
    ImageIO.write(imageAndGraphic2D._1, "png", baos)


    baos.flush
    val imageInByte = baos.toByteArray
    baos.close

    import org.apache.commons.io.IOUtils
    import java.lang.IllegalArgumentException
    val MimeType = "image/png"
    try{

      Ok(imageInByte).as(MimeType)

    } catch {
      case e: IllegalArgumentException =>
        BadRequest("Could not load the image")
    }

  }
}
