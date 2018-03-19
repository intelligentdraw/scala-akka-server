package models

import models.Location.getDiagramWidth

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

case class Location(desc:String, left: Int, top: Int)

object Location{

  /**
    * Get count of bubbles
    * @param usecaseDiagram
    */
  def getAllUsecaseBubblesSetSize(usecaseDiagram: UsecaseDiagram): Int ={
    val includeBubble =  new scala.collection.mutable.HashSet[UsecaseInclude]()

    val allBubble = new scala.collection.mutable.HashSet[UsecaseBubble]()
    usecaseDiagram.actorsAndUsecases.values.foreach(set => {
      allBubble ++= set
      for (elem <- set) {
        includeBubble ++=elem.includes
      }
    })

    if (allBubble.size > includeBubble.size)
      allBubble.size
    else
      includeBubble.size
  }

  /**
    * Get the height of the diagram panel
    * @param usecaseDiagram
    * @return
    */
  def getDiagramHeight(usecaseDiagram: UsecaseDiagram): Int
    = getAllUsecaseBubblesSetSize(usecaseDiagram) * BubbleProperties.HEIGHT.getValue


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

      val x = if (UsecaseInclude.hasInclude(usecaseDiagram)){
        //get the subpanel size
        val subPanelWidth = DiagramProperties.LONG_WIDTH.getValue/3
        subPanelWidth + (subPanelWidth - BubbleProperties.WIDTH.getValue)/2

      }else{
        //get the subpanel size
        val subPanelWidth = DiagramProperties.SHORT_WIDTH.getValue/2
        subPanelWidth + (subPanelWidth - BubbleProperties.WIDTH.getValue)/2
      }

      locations += Location(bubble.desc, x.intValue(), yMilestone)
      yMilestone +=  BubbleProperties.HEIGHT.getValue
    })
    locations
  }

  /**
    * Create the locations of the bubble includes (bubbles)
    * @param usecaseDiagram
    * @return
    */
  def createIncludeLocations2(usecaseDiagram: UsecaseDiagram): scala.collection.mutable.Set[Location] ={
    //Get all the bubbles

    //create the locations
    val locations = new scala.collection.mutable.LinkedHashSet[Location]()
    var yMilestone: Int = 0


    val x = if (UsecaseInclude.hasInclude(usecaseDiagram)){
      //get the subpanel size
      val subPanelWidth = DiagramProperties.LONG_WIDTH.getValue/3
      (2 * subPanelWidth) + (subPanelWidth - BubbleProperties.WIDTH.getValue)/2

    }else{
      //get the subpanel size
      val subPanelWidth = DiagramProperties.SHORT_WIDTH.getValue/2
      (2 * subPanelWidth) + (subPanelWidth - BubbleProperties.WIDTH.getValue)/2
    }

    usecaseDiagram.actorsAndUsecases.values.foreach(set => {
      set.toList.foreach(bubble =>{
        bubble.includes.foreach(include =>{
          locations += Location(include.desc, x.intValue(), yMilestone)
          yMilestone +=  BubbleProperties.HEIGHT.getValue
        })
      })
    })
    locations
  }

  /**
    * Create the locations of the bubble includes (bubbles)
    * @param usecaseDiagram
    * @return
    */
  def createIncludeLocations(usecaseDiagram: UsecaseDiagram): scala.collection.mutable.LinkedHashSet[Location] ={
    //Get all the bubbles

    //create the locations
    val locations = new scala.collection.mutable.LinkedHashSet[Location]()


    val x = if (UsecaseInclude.hasInclude(usecaseDiagram)){
      //get the subpanel size
      val subPanelWidth = DiagramProperties.LONG_WIDTH.getValue/3
      (2 * subPanelWidth) + (subPanelWidth - BubbleProperties.WIDTH.getValue)/2

    }else{
      //get the subpanel size
      val subPanelWidth = DiagramProperties.SHORT_WIDTH.getValue/2
      (2 * subPanelWidth) + (subPanelWidth - BubbleProperties.WIDTH.getValue)/2
    }


    val diagramHeight = getDiagramHeight(usecaseDiagram)
    val milestone = diagramHeight/(getTotalIncludesCount(usecaseDiagram)+1)


    val includeBubbles =  new scala.collection.mutable.HashSet[UsecaseInclude]()
    usecaseDiagram.actorsAndUsecases.values.foreach(set => {
      set.toList.foreach(bubble =>{
        includeBubbles ++= bubble.includes
      })
    })

    includeBubbles.foreach(include=>{
      locations += Location(include.desc, x.intValue(), milestone * (includeBubbles.toList.reverse.indexOf(include)+1))
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

  def getDiagramWidth(usecaseDiagram: UsecaseDiagram):Int={
    if (UsecaseInclude.hasInclude(usecaseDiagram ))
      DiagramProperties.LONG_WIDTH.getValue
    else DiagramProperties.SHORT_WIDTH.getValue
  }

  def getTotalIncludesCount(usecaseDiagram: UsecaseDiagram): Int ={
    val includeBubble =  new scala.collection.mutable.HashSet[UsecaseInclude]()
    usecaseDiagram.actorsAndUsecases.values.foreach(set => {
      set.toList.foreach(bubble =>{
        includeBubble ++= bubble.includes
      })
    })
    includeBubble.size
  }
}
