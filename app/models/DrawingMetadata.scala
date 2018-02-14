package models
import play.api.libs.json.Json
case class DrawingMetadata(id: String, usecaseDiag: UsecaseDiagram)

object DrawingMetadata{


  var drawingsMap = Map(
    ("kj34kjwfw1" -> DrawingMetadata("kj34kjwfw1", UsecaseDiagram.get("qe"))),
    ("kj34kjwf13" -> DrawingMetadata("kj34kjwf13", UsecaseDiagram.get("qe"))),
  )

  implicit val format0 = Json.format[Location]
  implicit val format1 = Json.format[UsecaseActor]
  implicit val format2 = Json.format[UsecaseBubble]
  implicit val format3 = Json.format[UsecaseDiagram]
  implicit val format4 = Json.format[DrawingMetadata]

  def findAll = drawingsMap.values.toList

  def find(id: String) = drawingsMap(id)

}