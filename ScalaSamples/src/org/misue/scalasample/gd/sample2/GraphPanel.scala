// All Rights Reserved. Copyright (C) Kazuo Misue (2010-2011)
package org.misue.scalasample.gd.sample2

import swing._
import swing.event._
import java.awt.FontMetrics

class GraphPanel(mediator: Mediator) extends Component {

  private var buttonPressed: Boolean = false

  preferredSize = new Dimension(640, 480)

  listenTo(mouse.clicks, mouse.moves)
  reactions += {
    case e: MouseDragged => mouseDragged(e)
    case e: MousePressed => mousePressed(e)
    case e: MouseReleased => mouseReleased(e)
  }
  

  override def paintComponent(g: Graphics2D) = {
    super.paintComponent(g)
    val fm: FontMetrics = g.getFontMetrics
    val graph: Graph = mediator.graph
    if (graph != null) graph.draw(g, fm, mediator.showStress)
  }
  
  private def mousePressed(e: MousePressed): Unit = {
    val graph: Graph = mediator.graph
    if (graph != null) {
      buttonPressed = true
      graph.pickNode(e.point)
      repaint()
    }
  }

  private def mouseReleased(e: MouseReleased): Unit = {
    val graph: Graph = mediator.graph
    if (graph != null) {
      buttonPressed = false
      graph.releaseNode(e.point, !buttonPressed)
      repaint()
    }
  }

  private def mouseDragged(e: MouseDragged): Unit = {
    val graph: Graph = mediator.graph
    if (graph != null) {
      graph.dragNode(e.point)
      repaint()
    }
  }

}
