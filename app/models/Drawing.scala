package models

import java.util.Date

import play.api.libs.json.Json

case class Drawing(id: String, title: String, fullname: String, category: String, subCategory: String, drawingType: String, created: String, updated: String)

object Drawing{


  var drawingsMap = Map(
    ("aaaaa" -> Drawing("aaaaa", "Create Account", "James Brown", "NPR", "Accounts", "Use Case", "12-Mar-17", "12-Mar-17")),
    ("bbbbb" -> Drawing("bbbbb", "Update Account", "Femi Jones", "NPR", "Accounts", "Use Case", "12-Mar-17", "12-Mar-17")),
    ("ccccc" -> Drawing("ccccc", "Update Account", "Femi Jones", "NPR", "Accounts", "Use Case", "12-Mar-17", "12-Mar-17")),
    ("ddddd" -> Drawing("ddddd", "Update Account", "Femi Jones", "NPR", "Accounts", "Use Case", "12-Mar-17", "12-Mar-17")),
  )

  implicit val format = Json.format[Drawing]

  def findAll = drawingsMap.values.toList.sortBy(_.title)

}