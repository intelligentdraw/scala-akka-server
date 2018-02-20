package helpers

import java.awt.Graphics2D

import models.{ActorProperties, BubbleProperties, Location, UsecaseDiagram}

object GraphicsHelper {




  def drawDiagramPanel(usecaseDiag:UsecaseDiagram) = {


    val diagWidth = Location.getDiagramWidth
    val diagHeight = Location.getDiagramHeight(usecaseDiag)

    import java.awt.Graphics2D
    import java.awt.image.BufferedImage
    val image = new BufferedImage(diagWidth, diagHeight, BufferedImage.TYPE_INT_BGR)
    val graphic2D = image.createGraphics

    import java.awt.Color
    graphic2D.setColor(Color.white)
    graphic2D.fillRect(0, 0, diagWidth, diagHeight)
    graphic2D.setColor(Color.black)

    graphic2D.drawRect(250, 0, diagWidth - 251, diagHeight - 1)
    (image, graphic2D)
  }

  def createUsecaseActor(graphic2D: Graphics2D, x: Int, y:Int,  desc: String): Unit ={
    val radius = ActorProperties.HEAD_RADIUS.getValue
    val diameter:Int = radius * 2

    //head
    graphic2D.drawOval(x-radius, y-radius, diameter, diameter)

    /**
      * Create a slight shift at the shoulders
      * @param length
      * @return
      */
    def getShoulderY(length:Int): Int ={
      val value = 1.5:BigDecimal
      (value.*(length)).intValue()
    }

    val shoulderY = y+getShoulderY(radius)
    val handY = y+2*radius

    //left arm
    graphic2D.drawLine(x- radius, handY, x, shoulderY)

    //right arm
    graphic2D.drawLine(x, shoulderY, x+radius,handY)

    //torso
    graphic2D.drawLine(x, y+radius, x, y+ 3 * radius)

    //legs
    graphic2D.drawLine( x, y + 3 * radius, x-radius, y+ 4 * radius)
    graphic2D.drawLine( x, y + 3 * radius, x+radius, y+ 4 * radius)

    //desc
    var wordLevel: Int = 4
    desc.split("\\W+").foreach(
      word => {
        wordLevel +=1;
        graphic2D.drawString(word, x- radius, y+wordLevel*radius)
      }
    )
  }

  def linkActorToUsecase(graphic2D: Graphics2D, actor: Location, usecase: Location): Unit ={
    val halfOfRecWidth= BubbleProperties.WIDTH.getValue/2
    val slightShiftToRight = ActorProperties.HEAD_RADIUS.getValue
    val slightShiftToBottom = ActorProperties.HEAD_RADIUS.getValue


    val width= BubbleProperties.WIDTH.getValue
    val height= BubbleProperties.HEIGHT.getValue
    val margin = BubbleProperties.MARGIN.getValue

    graphic2D.drawLine(actor.left+slightShiftToRight, actor.top+slightShiftToBottom, usecase.left+margin, usecase.top+ (height/2))
  }

  def createUsecaseBubble(graphics2D: Graphics2D, x: Int, y:Int, desc: String): Unit ={
    val width= BubbleProperties.WIDTH.getValue
    val height= BubbleProperties.HEIGHT.getValue
    val margin = BubbleProperties.MARGIN.getValue

    graphics2D.drawOval(x+margin, y+margin, width-margin-margin, height-margin-margin)

    val metrics = graphics2D.getFontMetrics
    val textX = (width - metrics.stringWidth(desc))/2
    graphics2D.drawString(desc,x + textX, y+(height/2))
  }
}
