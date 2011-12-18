// All Rights Reserved. Copyright (C) Kazuo Misue (2010-2011)
package org.misue.scalasample.gd.sample3b

import swing._
import swing.event._
import java.awt.FontMetrics

class GraphPanel(var graph: Graph = null) extends Component {

    private var buttonPressed: Boolean = false

    preferredSize = new Dimension(640, 480)

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
        if (graph != null) {
            super.paintComponent(g)
            val fm: FontMetrics = g.getFontMetrics
            graph.draw(g, fm)
        }
    }

    private def mousePressed(e: MousePressed): Unit = {
        if (graph != null) {
            buttonPressed = true
            graph.pickNode(e.point)
            repaint()
        }
    }

    private def mouseReleased(e: MouseReleased): Unit = {
        if (graph != null) {
            buttonPressed = false
            graph.releaseNode(e.point, !buttonPressed)
            repaint()
        }
    }

    private def mouseDragged(e: MouseDragged): Unit = {
        if (graph != null) {
            graph.dragNode(e.point)
            repaint()
        }
    }

}
