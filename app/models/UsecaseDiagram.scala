package models
import play.api.libs.json.Json

import scala.collection.mutable.ListBuffer

case class UsecaseDiagram(title:String, actorsAndUsecases: scala.collection.mutable.Map[UsecaseActor, scala.collection.mutable.Set[UsecaseBubble]])

case class UsecaseDiagrams(usecaseDiags: scala.collection.mutable.Map[String, UsecaseDiagram])

object UsecaseDiagrams{


  def createForTesting(): UsecaseDiagrams ={

    val usecaseBubble1 = UsecaseBubble("This is usecase 1", Set(UsecaseInclude("I extend usecase 1")))
    val usecaseBubble2 = UsecaseBubble("This is usecase 2", Set.empty)
    val usecaseBubble3 = UsecaseBubble("This is usecase 3", Set.empty)
    val usecaseBubble4 = UsecaseBubble("This is usecase 4", Set(UsecaseInclude("I extend usecase 4")))
    val usecaseBubble5 = UsecaseBubble("This is usecase 5", Set.empty)
    val usecaseBubble6 = UsecaseBubble("This is usecase 6", Set(UsecaseInclude("I_extends usecase_6_and_know"), UsecaseInclude("I also extends usecase 7")))
    val usecaseBubble7 = UsecaseBubble("This is usecase 7", Set.empty)
    val usecaseBubble8 = UsecaseBubble("This is usecase 8", Set.empty)
    val usecaseBubble9 = UsecaseBubble("This is usecase 9", Set.empty)
    val usecaseBubble10 = UsecaseBubble("This is usecase 10", Set.empty)
    val usecaseBubble11 = UsecaseBubble("This is usecase 11", Set.empty)
    val usecaseBubble12 = UsecaseBubble("This is usecase 12 1314", Set(UsecaseInclude("I extend usecase 4")))

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


    var rootMap = scala.collection.mutable.Map.empty[String, UsecaseDiagram]


    rootMap ("aaaaa") = UsecaseDiagram("Usecase diagram One", scala.collection.mutable.Map(
        UsecaseActor("Director")->set1,
      UsecaseActor("Office Worker")->set2,
      UsecaseActor("Customer")->set2,
      UsecaseActor("Factory Worker")->set3))


    rootMap ("bbbbb") = UsecaseDiagram("Usecase diagram Two", scala.collection.mutable.Map(
      UsecaseActor("Director")->set2,
      UsecaseActor("Managing Director")->set3))

    UsecaseDiagrams(rootMap)

  }
}
