package models
import play.api.libs.json.Json

import scala.collection.mutable.ListBuffer

case class UsecaseDiagram(actorsAndUsecases: scala.collection.mutable.Map[UsecaseActor, scala.collection.mutable.Set[UsecaseBubble]])

case class UsecaseDiagrams(usecaseDiags: Map[String, UsecaseDiagram])

object UsecaseDiagrams{


  def createForTesting(): UsecaseDiagrams ={

    val usecaseBubble1 = UsecaseBubble("This is usecase 1")
    val usecaseBubble2 = UsecaseBubble("This is usecase 2")
    val usecaseBubble3 = UsecaseBubble("This is usecase 3")
    val usecaseBubble4 = UsecaseBubble("This is usecase 4")
    val usecaseBubble5 = UsecaseBubble("This is usecase 5")
    val usecaseBubble6 = UsecaseBubble("This is usecase 6")
    val usecaseBubble7 = UsecaseBubble("This is usecase 7")
    val usecaseBubble8 = UsecaseBubble("This is usecase 8")
    val usecaseBubble9 = UsecaseBubble("This is usecase 9")
    val usecaseBubble10 = UsecaseBubble("This is usecase 10")
    val usecaseBubble11 = UsecaseBubble("This is usecase 11")
    val usecaseBubble12 = UsecaseBubble("This is usecase 12")

    var set1 = new scala.collection.mutable.LinkedHashSet[UsecaseBubble]()
    set1 += usecaseBubble1
    set1 += usecaseBubble2
    set1 += usecaseBubble3
    set1 += usecaseBubble4
    set1 += usecaseBubble5
    set1 += usecaseBubble6
    set1 += usecaseBubble7
    set1 += usecaseBubble8

    var set2 = new scala.collection.mutable.LinkedHashSet[UsecaseBubble]()
    set2 += usecaseBubble1
    set2 += usecaseBubble2
    set2 += usecaseBubble3
    set2 += usecaseBubble4
    set2 += usecaseBubble8
    set2 += usecaseBubble9
    set2 += usecaseBubble10

    var set3 = new scala.collection.mutable.LinkedHashSet[UsecaseBubble]()
    set3 += usecaseBubble1
    set3 += usecaseBubble2
    set3 += usecaseBubble3
    set3 += usecaseBubble11
    set3 += usecaseBubble12


    UsecaseDiagrams(
      Map(
        "aaaaa"-> UsecaseDiagram(scala.collection.mutable.Map(
          UsecaseActor("Director")->set1,
          UsecaseActor("Office Worker")->set2,
          UsecaseActor("Customer")->set2,
          UsecaseActor("Factory Worker")->set3)),
        "bbbbb"-> UsecaseDiagram(scala.collection.mutable.Map(
          UsecaseActor("Managing Director")->set2,
          UsecaseActor("Acting Director")->set3)),
        "ccccc"-> UsecaseDiagram(scala.collection.mutable.Map(
          UsecaseActor("Managing Director")->set2,
          UsecaseActor("Acting Director")->set3)),
        "ddddd"-> UsecaseDiagram(scala.collection.mutable.Map(
          UsecaseActor("Manager")->set3))));

  }
}
