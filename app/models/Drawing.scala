package models

import java.util.Date

import play.api.libs.json.Json

case class Drawing(id: String, title: String, fullname: String, category: String, subCategory: String, drawingType: String, created: String, updated: String)

object Drawing{


  var drawingsMap = Map(
    ("kj34kjwfw1" -> Drawing("kj34kjwfw1", "Create Account", "James Brown", "NPR", "Accounts", "Use Case", "12-Mar-17", "12-Mar-17")),
    ("kj34kjwfw2" -> Drawing("kj34kjwfw2", "Update Account", "Femi Jones", "NPR", "Accounts", "Use Case", "12-Mar-17", "12-Mar-17")),
    ("kj34kjwfw3" -> Drawing("kj34kjwfw3", "Update Account", "Femi Jones", "NPR", "Accounts", "Use Case", "12-Mar-17", "12-Mar-17")),
    ("kj34kjwfw4" -> Drawing("kj34kjwfw4", "Update Account", "Femi Jones", "NPR", "Accounts", "Use Case", "12-Mar-17", "12-Mar-17")),
    ("kj34kjwfw5" -> Drawing("kj34kjwfw5", "Update Account", "Femi Jones", "NPR", "Accounts", "Use Case", "12-Mar-17", "12-Mar-17")),
    ("kj34kjwfw6" -> Drawing("kj34kjwfw6", "Update Account", "Femi Jones", "NPR", "Accounts", "Use Case", "12-Mar-17", "12-Mar-17")),
    ("kj34kjwfw7" -> Drawing("kj34kjwfw7", "Update Account", "Femi Jones", "NPR", "Accounts", "Use Case", "12-Mar-17", "12-Mar-17")),
    ("kj34kjwfw8" -> Drawing("kj34kjwfw8", "Update Account", "Femi Jones", "NPR", "Accounts", "Use Case", "12-Mar-17", "12-Mar-17")),
    ("kj34kjwfw9" -> Drawing("kj34kjwfw9", "Update Account", "Femi Jones", "NPR", "Accounts", "Use Case", "12-Mar-17", "12-Mar-17")),
    ("kj34kjwf10" -> Drawing("kj34kjwf10", "Update Account", "Femi Jones", "NPR", "Accounts", "Use Case", "12-Mar-17", "12-Mar-17")),
    ("kj34kjwf11" -> Drawing("kj34kjwf11", "Update Account", "Femi Jones", "NPR", "Accounts", "Use Case", "12-Mar-17", "12-Mar-17")),
    ("kj34kjwf12" -> Drawing("kj34kjwf12", "Update Account", "Femi Jones", "NPR", "Accounts", "Use Case", "12-Mar-17", "12-Mar-17")),
    ("kj34kjwf13" -> Drawing("kj34kjwf13", "Update Account", "Femi Jones", "NPR", "Accounts", "Use Case", "12-Mar-17", "12-Mar-17")),
    ("kj34kjwf14" -> Drawing("kj34kjwf14", "Update Account", "Femi Jones", "NPR", "Accounts", "Use Case", "12-Mar-17", "12-Mar-17")),
    ("kj34kjwf15" -> Drawing("kj34kjwf15", "Update Account", "Femi Jones", "NPR", "Accounts", "Use Case", "12-Mar-17", "12-Mar-17")),
    ("kj34kjwf16" -> Drawing("kj34kjwf16", "Update Account", "Femi Jones", "NPR", "Accounts", "Use Case", "12-Mar-17", "12-Mar-17")),
    ("kj34kjwf17" -> Drawing("kj34kjwf17", "Update Account", "Femi Jones", "NPR", "Accounts", "Use Case", "12-Mar-17", "12-Mar-17")),
    ("kj34kjwf18" -> Drawing("kj34kjwf18", "Update Account", "Femi Jones", "NPR", "Accounts", "Use Case", "12-Mar-17", "12-Mar-17")),
    ("kj34kjwf19" -> Drawing("kj34kjwf19", "Update Account", "Femi Jones", "NPR", "Accounts", "Use Case", "12-Mar-17", "12-Mar-17")),
    ("kj34kjwf20" -> Drawing("kj34kjwf20", "Update Account", "Femi Jones", "NPR", "Accounts", "Use Case", "12-Mar-17", "12-Mar-17")),
  )

  implicit val format = Json.format[Drawing]

  def findAll = drawingsMap.values.toList.sortBy(_.title)

  def find(id: String) = drawingsMap(id)

}