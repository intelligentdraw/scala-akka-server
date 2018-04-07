package helpers

import java.awt.{BasicStroke, Color, Graphics2D}

import models._

object GraphicsHelper {

  /**
    *
    * @param usecaseDiag
    * @return
    */
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

    val g2d = graphics2D.create.asInstanceOf[Graphics2D]
    import java.awt.Font
    g2d.setFont(new Font("default", Font.BOLD, 12))

    val metrics = g2d.getFontMetrics
    val messageWidth = metrics.stringWidth(usecaseDiag.title)

    //message
    g2d.drawString(usecaseDiag.title, 10, 15)

    //bottom line
    graphics2D.drawLine(0, 20, messageWidth+10, 20)

    //right side line
    graphics2D.drawLine(messageWidth+20, 0, messageWidth+20, 10)

    //diagonal line to join bottom and side
    graphics2D.drawLine(messageWidth+10, 20, messageWidth+20, 10)

    g2d.dispose()
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
    val g2d = graphic2D.create.asInstanceOf[Graphics2D]
    import java.awt.Font
    g2d.setFont(new Font("default", Font.BOLD, 11))
    var wordLevel: Int = 4
    desc.split("\\W+").foreach(
      word => {
        wordLevel +=1;
        g2d.drawString(word, x- radius, y+wordLevel*radius)
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
    val g2d = graphics2D.create.asInstanceOf[Graphics2D]
    g2d.setStroke(new BasicStroke(1.5F))
    val x = x2 - x1
    val y = y2 - y1
    println("x: " + x)
    println("y: " + y)

    if (y  > 0) {

      val currentAngleInDecimal = scala.math.BigDecimal(y) / scala.math.BigDecimal(x)
      val currentAngle = scala.math.toDegrees(scala.math.atan(currentAngleInDecimal.toFloat))

      //arrow angle from line
      val arrowAngleBottomSide = currentAngle / 2

      //arrow x
      val arrowX = scala.math.BigDecimal(x) / scala.math.BigDecimal(10)
      //arrow y
      val arrowY = scala.math.tan(scala.math.toRadians(arrowAngleBottomSide)) * arrowX

      //draw the bottom side of arrow
      g2d.drawLine(x2 - arrowX.toInt, y2 - arrowY.intValue(), x2, y2)


      val arrowAngleTopSide: scala.math.BigDecimal = currentAngle + arrowAngleBottomSide

      val arrowY2 = x2 - (x2 - arrowX.toInt)

      val arrowX2 = arrowY2 / scala.math.tan(scala.math.toRadians(arrowAngleTopSide.toFloat))
      g2d.drawLine(x2 - arrowX2.intValue(), y2 - arrowY2.intValue(), x2, y2)

    }else{

      val currentAngleInDecimal = scala.math.BigDecimal(x) / scala.math.BigDecimal(y)
      val currentAngle = scala.math.toDegrees(scala.math.atan(currentAngleInDecimal.toFloat))
      println("currentAngle2: " + currentAngle)

      //arrow angle from line
      val arrowAngleBottomSide = currentAngle / 2

      //arrow x
      val arrowX = scala.math.BigDecimal(x) / scala.math.BigDecimal(10)
      //arrow y
      val arrowY = scala.math.tan(scala.math.toRadians(arrowAngleBottomSide)) * arrowX
      println("arrowY2: " + arrowY)

      //draw the bottom side of arrow
      g2d.drawLine(x2 - arrowX.toInt, y2 - arrowY.intValue(), x2, y2)


      val arrowAngleTopSide: scala.math.BigDecimal = currentAngle + arrowAngleBottomSide
      println("arrowAngleTopSide: " + arrowAngleTopSide.toFloat)

      val arrowX2 = x2 - (x2 - arrowX.toInt)
      println("arrowX2: " + arrowX2)

      val arrowY2 = arrowX2 / scala.math.tan(scala.math.toRadians(arrowAngleTopSide.toFloat))
      println("arrowX2: " + arrowX2)
      g2d.drawLine(x2 - arrowX2.intValue(), y2 - arrowY2.intValue(), x2, y2)

    }
    g2d.dispose()
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
    val stringWidth = metrics.stringWidth(desc)


    val g2d = graphics2D.create.asInstanceOf[Graphics2D]
    import java.awt.Font
    g2d.setFont(new Font("default", Font.BOLD, 11))
    if (stringWidth < width) {
      val textX = (width - stringWidth) / 2
      g2d.drawString(desc, x + textX, y + (height / 2))

    }else{
      val wordArr = desc.split("\\s")
      //wrap
      wordArr.foreach(word =>{
        val stringWidth = metrics.stringWidth(word)
        val textX = (width - stringWidth) / 2
        var row = wordArr.indexOf(word) + 1
        g2d.drawString(word,  x + textX, y + row * (height / (wordArr.length+1)))
      })
    }
    g2d.dispose()
  }
}
