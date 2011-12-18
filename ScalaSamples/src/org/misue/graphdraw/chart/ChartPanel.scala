// All Rights Reserved. Copyright (C) Kazuo Misue (2010)
package org.misue.graphdraw.chart

import java.awt.Color
import swing._
import swing.event._
import org.misue.graphdraw.chart.basic.Graph

class ChartPanel(mediator: Mediator) extends Component {

  private var buttonPressed: Boolean = false

  listenTo(mouse.clicks, mouse.moves)
  reactions += {
    case e: MouseDragged => mouseDragged(e)
    case e: MousePressed => mousePressed(e)
    case e: MouseReleased => mouseReleased(e)
  }

  override def paintComponent(g: Graphics2D) = {
    super.paintComponent(g)
    g.setColor(Color.BLACK)
    g.fillRect(0, 0, size.width, size.height)
    g.setFont(g.getFont.deriveFont(9f))
    mediator.drawChart(g, size)
  }
  
  def mousePressed(e: MousePressed): Unit = {
    val graph: Graph = mediator.graph
    if (graph != null) {
      buttonPressed = true
//      graph.pickNode(e.point)
      repaint()
    }
  }

  def mouseReleased(e: MouseReleased): Unit = {
    val graph: Graph = mediator.graph
    if (graph != null) {
      buttonPressed = false
//      graph.releaseNode(e.point, !buttonPressed)
      repaint()
    }
  }

  def mouseDragged(e: MouseDragged): Unit = {
    val graph: Graph = mediator.graph
    if (graph != null) {
//      graph.dragNode(e.point)
      repaint()
    }
  }

}
