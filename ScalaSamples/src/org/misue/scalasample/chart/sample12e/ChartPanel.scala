// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample12e

import java.awt.Graphics2D
import swing._
import scala.swing.event._

//class ChartPanel(chart: Chart) extends Component {
class ChartPanel(chart: Chart) extends Panel {

    // This is important to get key events.
    focusable = true

    listenTo(keys)
    reactions += {
        case KeyPressed(_, Key.A, _, _) => println("A was typed.")
        //        case e: KeyTyped => println("KeyTyped") //keyTyped(e)
        //        case e: KeyPressed => println("KeyPressed")
        //        case e: KeyReleased => println("KeyReleased")
    }

    listenTo(mouse.clicks, mouse.moves)
    reactions += {
        case e: MouseDragged => mouseDragged(e)
        case e: MousePressed => mousePressed(e)
        case e: MouseReleased => mouseReleased(e)
    }

    override def paintComponent(g: Graphics2D) = {
        super.paintComponent(g)
        chart.draw(g, size.width, size.height)
    }

    private def mousePressed(e: MousePressed): Unit = {
        if (chart != null) {
            chart.mousePressed(e.point)
            repaint
        }
        e.consume
    }

    private def mouseReleased(e: MouseReleased): Unit = {
        if (chart != null) {
            chart.mouseReleased(e.point)
            repaint
        }
        e.consume
    }

    private def mouseDragged(e: MouseDragged): Unit = {
        if (chart != null) {
            chart.mouseDragged(e.point)
            repaint
        }
        e.consume
    }

    private def keyTyped(e: KeyTyped): Unit = {
        print(e.char)
        e.consume
    }

}

