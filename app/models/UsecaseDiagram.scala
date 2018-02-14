package models
import play.api.libs.json.Json

import scala.collection.mutable.ListBuffer

case class UsecaseDiagram( usecaseActor: UsecaseActor, bubbles: ListBuffer[UsecaseBubble])

object UsecaseDiagram{



  def get(id:String) = {
    var list = new ListBuffer[UsecaseBubble]()
    list += UsecaseBubble("This is usecase 1", Location(50, 100))
    list += UsecaseBubble("This is usecase 2", Location(50, 200))
    list += UsecaseBubble("This is usecase 3", Location(50, 300))
    list += UsecaseBubble("This is usecase 4", Location(50, 400))
    list += UsecaseBubble("This is usecase 5", Location(50, 500))
    UsecaseDiagram(new UsecaseActor("Actor 1", Location(0, 300)), list)
  }
}
