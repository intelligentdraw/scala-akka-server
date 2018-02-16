package helpers

import java.awt.Graphics2D

object GraphicsHelper {


  def createUsecaseActor(graphic2D: Graphics2D, x: Int, y:Int, radius: Int, desc: String): Unit ={
    val diameter:Int = radius * 2

    //head
    graphic2D.fillOval(x-radius, y-radius, diameter, diameter)

    //arms
    graphic2D.drawLine(x- radius, y+2*radius, x+radius, y+2*radius)

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
}
