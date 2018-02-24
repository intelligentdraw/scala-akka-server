package models

import models.Location.getDiagramWidth

import scala.collection.mutable.ListBuffer

case class Location(desc:String, left: Int, top: Int)

object Location{

  /**
    * Get count of bubbles
    * @param usecaseDiagram
    */
  def getAllUsecaseBubblesSetSize(usecaseDiagram: UsecaseDiagram): Int ={

    val all = new scala.collection.mutable.HashSet[UsecaseBubble]()
    usecaseDiagram.actorsAndUsecases.values.foreach(set => {
      all ++= set
    })
    all.size
  }

  /**
    * Get the height of the diagram panel
    * @param usecaseDiagram
    * @return
    */
  def getDiagramHeight(usecaseDiagram: UsecaseDiagram): Int
    = getAllUsecaseBubblesSetSize(usecaseDiagram) * BubbleProperties.HEIGHT.getValue

  /**
    * Get the width of the diagram panel
    * @return
    */
  def getDiagramWidth = DiagramProperties.WIDTH.getValue

  /**
    * Create the locations of the usecases (bubbles)
    * @param usecaseDiagram
    * @return
    */
  def createBubbleLocations(usecaseDiagram: UsecaseDiagram): scala.collection.mutable.Set[Location] ={


    //Get all the bubbles
    val all = new scala.collection.mutable.LinkedHashSet[UsecaseBubble]()
    usecaseDiagram.actorsAndUsecases.values.foreach(set => all ++= set)

    //create the locations
    val locations = new scala.collection.mutable.LinkedHashSet[Location]()
    var yMilestone: Int = 0
    all.foreach(bubble =>{
      val fraction = 0.6:BigDecimal
      val x =   fraction * getDiagramWidth
      locations += Location(bubble.desc, x.intValue(), yMilestone)
      yMilestone +=  BubbleProperties.HEIGHT.getValue
    })
    locations
  }

  /**
    * Create the location of the usecase actor
    * @param usecaseDiagram
    */
  def createActorLocation(usecaseDiagram: UsecaseDiagram): Set[Location]={
    val diagramHeight = getDiagramHeight(usecaseDiagram)
    val milestone = diagramHeight/(usecaseDiagram.actorsAndUsecases.keys.size+1)

    usecaseDiagram.actorsAndUsecases.keys.toSet.map((actor: UsecaseActor)=>{
      Location(actor.name, 50, milestone * (usecaseDiagram.actorsAndUsecases.keys.toList.indexOf(actor)+1))
    })


  }
}
