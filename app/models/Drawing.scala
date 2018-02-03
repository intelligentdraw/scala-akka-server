package models

import java.util.Date

import play.api.libs.json.Json

case class Drawing(title: String, fullname: String, category: String, subCategory: String, drawingType: String, created: String, updated: String)

object Drawing{
  var drawings = Set(
    Drawing("Create Account", "James Brown", "NPR", "Accounts", "Use Case", "12-Mar-17", "12-Mar-17"),
    Drawing("Update Account", "Femi Jones", "NPR", "Accounts", "Use Case", "12-Mar-17", "12-Mar-17"),
    Drawing("Update Account", "Femi Jones", "NPR2", "Accounts", "Use Case", "12-Mar-17", "12-Mar-17"),
    Drawing("Update Account", "James Brown", "NPR2", "Accounts", "Sequence Diagram", "12-Mar-17", "12-Mar-17"),
    Drawing("Create Account", "Femi Jones", "NPR2", "Accounts", "Use Case", "12-Mar-17", "12-Mar-17"),
    Drawing("Delete Account", "James Brown", "NPR", "Accounts", "Use Case", "12-Mar-17", "12-Mar-17"),
    Drawing("Create Account", "Femi Jones", "NPR", "Accounts", "Use Case", "12-Mar-17", "12-Mar-17"),
    Drawing("Create Account", "James Brown", "NPR", "Accounts", "Use Case", "12-Mar-17", "12-Mar-17"),
    Drawing("Create Account", "James Brown", "NPR", "Accounts", "Use Case", "12-Mar-17", "12-Mar-17"),
    Drawing("Update Account", "James Brown", "NPR2", "Accounts", "Sequence Diagram", "12-Mar-17", "12-Mar-17"),
    Drawing("Create Account", "Blessing Obon", "NPR2", "Accounts", "Use Case", "12-Mar-17", "12-Mar-17"),
    Drawing("Delete Account", "James Brown", "NPR", "Accounts", "Use Case", "12-Mar-17", "12-Mar-17"),
    Drawing("Update Account", "James Brown", "NPR2", "Accounts", "Sequence Diagram", "12-Mar-17", "12-Mar-17"),
    Drawing("Create Account", "Blessing Obon", "NPR2", "Accounts", "Use Case", "12-Mar-17", "12-Mar-17"),
    Drawing("Delete Account", "James Brown", "NPR", "Accounts", "Use Case", "12-Mar-17", "12-Mar-17"),
    Drawing("Update Account", "James Brown", "NPR2", "Accounts", "Sequence Diagram", "12-Mar-17", "12-Mar-17"),
    Drawing("Create Account", "Blessing Obon", "NPR2", "Accounts", "Use Case", "12-Mar-17", "12-Mar-17"),
    Drawing("Delete Account", "James Brown", "NPR", "Accounts", "Use Case", "12-Mar-17", "12-Mar-17"),
  )

  implicit val format = Json.format[Drawing]

  def findAll = drawings.toList.sortBy( _.title)

}