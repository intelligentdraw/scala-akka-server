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

import scala.collection.mutable.ListBuffer

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

  /**
    * Get Drawing Metadata
    * @param id
    * @return
    */
  def getDrawingMetadata(id: String) = Action{

    val usecaseDiag = usecaseDiagrams.usecaseDiags(id)
    val actorLocations = Location.createActorLocation(usecaseDiag)
    val bubbleLoactions = Location.createBubbleLocations(usecaseDiag)
    val includeLocations = Location.createIncludeLocations(usecaseDiag)


    val usecaseElements = new ListBuffer[Map[String,String]]()


    actorLocations.foreach(actorLocation =>{
      val left = actorLocation.left - (ActorProperties.HEAD_RADIUS.getValue * 2)
      val top  = actorLocation.top  - (ActorProperties.HEAD_RADIUS.getValue * 2)
      val right = actorLocation.left + (ActorProperties.HEAD_RADIUS.getValue * 2)
      val bottom = actorLocation.top + (ActorProperties.HEAD_RADIUS.getValue * 4)
      val coords = String.valueOf(left) + ", " + String.valueOf(top) + ", " + String.valueOf(right) + ", " + String.valueOf(bottom)
      val metadataMap = new scala.collection.mutable.HashMap[String, String]()
      usecaseElements += Map(
        "name" -> actorLocation.desc,
        "coords"->coords,
        "shape" ->"rect")
    })

    bubbleLoactions.foreach(bubbleLocation=>{
      val left = bubbleLocation.left
      val top = bubbleLocation.top
      val right = bubbleLocation.left + BubbleProperties.WIDTH.getValue
      val bottom = bubbleLocation.top + BubbleProperties.HEIGHT.getValue
      val coords = String.valueOf(left) + ", " + String.valueOf(top) + ", " + String.valueOf(right) + ", " + String.valueOf(bottom)
      usecaseElements += Map(
        "name" -> bubbleLocation.desc,
        "coords"->coords,
        "shape" ->"rect")
    })


    includeLocations.foreach(includeLocation=>{
      val left = includeLocation.left
      val top = includeLocation.top
      val right = includeLocation.left + BubbleProperties.WIDTH.getValue
      val bottom = includeLocation.top + BubbleProperties.HEIGHT.getValue
      val coords = String.valueOf(left) + ", " + String.valueOf(top) + ", " + String.valueOf(right) + ", " + String.valueOf(bottom)
      usecaseElements += Map(
        "name" -> includeLocation.desc,
        "coords"->coords,
        "shape" ->"rect")
    })

    Ok(Json.toJson(usecaseElements))
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
    val includeLocations = Location.createIncludeLocations(usecaseDiag)

    GraphicsHelper.writeDiagTitle(imageAndGraphic2D._2, usecaseDiag)

    //draw the usecase actor
    actorLocations.foreach(actorLocation =>{
      GraphicsHelper.createUsecaseActor(imageAndGraphic2D._2, actorLocation.left, actorLocation.top, actorLocation.desc)
    })


    //draw the usecase bubble
    bubbleLocations.foreach(location => {
        GraphicsHelper.createUsecaseBubble(imageAndGraphic2D._2, location.left, location.top, location.desc)
      })

    //Draw the includes
    includeLocations.foreach(location => {
      GraphicsHelper.createUsecaseBubble(imageAndGraphic2D._2, location.left, location.top, location.desc)
    })

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


    bubbleLocations.foreach(bubbleLocation =>{
      usecaseDiag.actorsAndUsecases.values.foreach(set=>{
        set.foreach(bubble=>{
          //find the includes for the bubble
          if (bubble.desc.equals(bubbleLocation.desc)) {
            bubble.includes.foreach(include=>{
              includeLocations.foreach(includeLocation => {
                if (includeLocation.desc.equals(include.desc)){
                  GraphicsHelper.linkUsecaseToInclude(imageAndGraphic2D._2, bubbleLocation, includeLocation)
                }
              })
            })
          }
        })
      })
    })


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
