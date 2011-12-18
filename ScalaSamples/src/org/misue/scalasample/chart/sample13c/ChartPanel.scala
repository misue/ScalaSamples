// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample13c

import java.awt.Graphics2D
import swing._
import swing.event._
import javax.swing.Timer
import java.awt.event._
import scala.actors.Actor

class ChartPanel(var chart: Chart = null) extends Component {

    private var buttonPressed: Boolean = false

    listenTo(mouse.clicks, mouse.moves)
    reactions += {
        case e: MouseDragged => mouseDragged(e)
        case e: MousePressed => mousePressed(e)
        case e: MouseReleased => mouseReleased(e)
    }

    private val timer: javax.swing.Timer =
        new javax.swing.Timer(33,
            new java.awt.event.ActionListener {
                override def actionPerformed(evt: java.awt.event.ActionEvent) {
                    repaint()
                }
            })
    timer.start()

    override def paintComponent(g: Graphics2D) = {
        if (chart != null) {
            super.paintComponent(g)
            chart.draw(g, size.width, size.height)
        }
    }

    private def mousePressed(e: MousePressed): Unit = {
        if (chart != null) {
            chart.mousePressed(e.point)
//            repaint
        }
        e.consume
    }

    private def mouseReleased(e: MouseReleased): Unit = {
        if (chart != null) {
            chart.mouseReleased(e.point)
//            repaint
        }
        e.consume
    }

    private def mouseDragged(e: MouseDragged): Unit = {
        if (chart != null) {
            chart.mouseDragged(e.point)
//            repaint
        }
        e.consume
    }

}

