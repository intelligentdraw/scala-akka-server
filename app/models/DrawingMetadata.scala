package models
import play.api.libs.json.Json
case class DrawingMetadata(id: String, usecaseDiag: UsecaseDiagram)

//object DrawingMetadata{
//
//
//  var drawingsMap = Map(
//    ("aaaaa" -> DrawingMetadata("aaaaa", UsecaseDiagram.get("qe"))),
//    ("bbbbb" -> DrawingMetadata("bbbbb", UsecaseDiagram.get("qe"))),
//  )
//
//  implicit val format1 = Json.format[UsecaseActor]
//  implicit val format2 = Json.format[UsecaseBubble]
//  implicit val format3 = Json.format[UsecaseDiagram]
//  implicit val format4 = Json.format[DrawingMetadata]
//
//  def findAll = drawingsMap.values.toList
//
//  def find(id: String) = drawingsMap(id)
//
//}