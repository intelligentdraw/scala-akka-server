package controllers

import java.awt.Color
import java.awt.geom.Arc2D
import java.io.ByteArrayOutputStream
import javax.inject.{Inject, Singleton}

import models.Drawing
import models.DrawingMetadata
import models.UsecaseDiagram
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import play.api.mvc._

@Singleton
class DrawingController  @Inject()(cc: ControllerComponents) extends AbstractController(cc){

  def getDrawings = Action{
    val drawings = Drawing.findAll
    Ok(Json.toJson(drawings))
  }

  def getDrawing(id: String) = Action{
    val drawing = Drawing find id
    Ok(Json.toJson(drawing))
  }

  def getDrawingMetadata(id: String) = Action{
    val drawing:DrawingMetadata = DrawingMetadata.find(id)
    Ok(Json.toJson(drawing))
  }

  def getImage(id: String) = Action{

    val drawingMetadata:DrawingMetadata = DrawingMetadata.find("kj34kjwfw1")
    val usecaseDiag:UsecaseDiagram = drawingMetadata.usecaseDiag

    import java.awt.Graphics2D
    import java.awt.image.BufferedImage
    val image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_BGR)
    val graphic2D = image.createGraphics

    val color = Color.decode("#ffffff")
    graphic2D.setPaint(color)
    //graphic2D.fillArc(0, 0, 50, 50, 0, 45)
    //graphic2D.fillArc(0, 0, 50, 50, 135, 45)

    graphic2D.draw(new Arc2D.Double(10, 10,
      50,
      50,
      90, 135,
      Arc2D.OPEN))

    val baos = new ByteArrayOutputStream()
    import javax.imageio.ImageIO
    ImageIO.write(image, "png", baos)


    baos.flush
    val imageInByte = baos.toByteArray
    baos.close

    import org.apache.commons.io.IOUtils
    import java.lang.IllegalArgumentException
    val MimeType = "image/png"
    try{

      //val imageData: Array[Byte] = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("moses.png"))
      Ok(imageInByte).as(MimeType)

    } catch {
      case e: IllegalArgumentException =>
        BadRequest("Could not load the image")
    }

  }
}
