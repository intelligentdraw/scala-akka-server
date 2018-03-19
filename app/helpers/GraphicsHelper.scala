package helpers

import java.awt.{Color, Graphics2D}

import models._

object GraphicsHelper {




  def drawDiagramPanel(usecaseDiag:UsecaseDiagram) = {


    val diagWidth = if (UsecaseInclude.hasInclude(usecaseDiag ))
      DiagramProperties.LONG_WIDTH.getValue
    else DiagramProperties.SHORT_WIDTH.getValue

    val diagHeight = Location.getDiagramHeight(usecaseDiag)

    import java.awt.Graphics2D
    import java.awt.image.BufferedImage
    val image = new BufferedImage(diagWidth, diagHeight, BufferedImage.TYPE_INT_BGR)
    val graphic2D = image.createGraphics

    import java.awt.Color
    graphic2D.setColor(Color.white)
    graphic2D.fillRect(0, 0, diagWidth, diagHeight)

    graphic2D.setColor(Color.lightGray)
    graphic2D.drawRect(0, 0, diagWidth, diagHeight-1)

    graphic2D.setColor(Color.black)
    graphic2D.drawRect(250, 0, diagWidth - 251, diagHeight - 1)
    (image, graphic2D)
  }

  def writeDiagTitle(graphics2D: Graphics2D, usecaseDiag:UsecaseDiagram): Unit ={
    val metrics = graphics2D.getFontMetrics
    val messageWidth = metrics.stringWidth(usecaseDiag.title)

    //message
    graphics2D.drawString(usecaseDiag.title, 10, 15)

    //bottom line
    graphics2D.drawLine(0, 20, messageWidth+10, 20)

    //right side line
    graphics2D.drawLine(messageWidth+20, 0, messageWidth+20, 10)

    //diagonal line to join bottom and side
    graphics2D.drawLine(messageWidth+10, 20, messageWidth+20, 10)
  }

  def createUsecaseActor(graphic2D: Graphics2D, x: Int, y:Int,  desc: String): Unit ={
    val radius = ActorProperties.HEAD_RADIUS.getValue
    val diameter:Int = radius * 2

    //head
    graphic2D.drawOval(x-radius, y-radius, diameter, diameter)


    graphic2D.setColor(Color.lightGray)
    graphic2D.fillOval(x-radius, y-radius, diameter, diameter)
    graphic2D.setColor(Color.black)

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


    val height= BubbleProperties.HEIGHT.getValue
    val margin = BubbleProperties.MARGIN.getValue

    graphic2D.drawLine(actor.left+slightShiftToRight, actor.top+slightShiftToBottom, usecase.left+margin, usecase.top+ (height/2))
  }


  def linkUsecaseToInclude(graphic2D: Graphics2D, bubble: Location, include: Location)={


    val width= BubbleProperties.WIDTH.getValue
    val height= BubbleProperties.HEIGHT.getValue
    val margin = BubbleProperties.MARGIN.getValue

    import java.awt.Graphics2D
    import java.awt.BasicStroke
    //create copy
    val g2d = graphic2D.create.asInstanceOf[Graphics2D]
    //set the stroke of the copy, not the original
    val dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, Array[Float](5), 0)
    g2d.setStroke(dashed)
    g2d.drawLine(bubble.left+(width-margin), bubble.top+ (height/2), include.left+margin, include.top+ (height/2))
    //add the arrow
    createArrow(graphic2D , bubble.left+(width-margin), bubble.top+ (height/2), include.left+margin, include.top+ (height/2))
    g2d.dispose
  }

  /**
    *
    * @param graphics2D
    * @param x1 of the line
    * @param y1 of the line
    * @param x2 of the line
    * @param y2 of the line
    */
  private def createArrow(graphics2D: Graphics2D, x1:Int, y1:Int, x2:Int, y2:Int): Unit ={
    val x = x2 - x1
    val y = y2 - y1
    println("x: " + x)
    println("y: " + y)

    //angle of the linking line between the usecase and include-usecase AND the adjacent X axis
    val currentAngle = scala.math.toDegrees(scala.math.atan(y/x))
    println("currentAngle:: " + currentAngle)

    //arrow angle from line
    val arrowAngleBottomSide = currentAngle/2
    println("arrowAngleBottomSide: " + arrowAngleBottomSide)

    //arrow x
    val arrowX = x/10
    println("arrowX: " + arrowX)
    //arrow y
    val arrowY = scala.math.atan(scala.math.toRadians(arrowAngleBottomSide)) * arrowX
    println("arrowY: " + arrowY)

    //draw the bottom side of arrow
    graphics2D.drawLine(x2 - arrowX, y2 - arrowY.intValue(), x2, y2)



    //angle of the linking line and the Y axis
    val currentAngleTopSide = (currentAngle - arrowAngleBottomSide)+currentAngle
    val angleFromYaxis = 90 - currentAngleTopSide
    println("angleFromYaxis: " + angleFromYaxis)
    val arrowY2 = y/2


    val arrowX2 = scala.math.atan(angleFromYaxis) * arrowY2
    graphics2D.drawLine(x2 - arrowX2.intValue(), y2 - arrowY2.intValue(), x2, y2)

  }

  def createUsecaseBubble(graphics2D: Graphics2D, x: Int, y:Int, desc: String): Unit ={
    val width= BubbleProperties.WIDTH.getValue
    val height= BubbleProperties.HEIGHT.getValue
    val margin = BubbleProperties.MARGIN.getValue

    graphics2D.drawOval(x+margin, y+margin, width-margin-margin, height-margin-margin)



    graphics2D.setColor(Color.lightGray)
    graphics2D.fillOval(x+margin, y+margin, width-margin-margin, height-margin-margin)
    graphics2D.setColor(Color.black)

    val metrics = graphics2D.getFontMetrics
    val textX = (width - metrics.stringWidth(desc))/2
    graphics2D.drawString(desc,x + textX, y+(height/2))
  }
}
