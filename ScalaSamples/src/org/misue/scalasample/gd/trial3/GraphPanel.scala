// All Rights Reserved. Copyright (C) Kazuo Misue (2010-2011)
package org.misue.scalasample.gd.trial3

import swing._
import java.awt.FontMetrics

class GraphPanel(mediator: Mediator) extends Component {

  preferredSize = new Dimension(640, 480)

  override def paintComponent(g: Graphics2D) = {
    super.paintComponent(g)
    val fm: FontMetrics = g.getFontMetrics
    val graph: Graph = mediator.graph
    if (graph != null) graph.draw(g, fm, mediator.showLabel, mediator.showStress)
  }

}
