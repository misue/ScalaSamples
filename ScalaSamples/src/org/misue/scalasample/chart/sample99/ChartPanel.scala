// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample99

import java.awt.Graphics
import java.awt.Graphics2D
import swing._
import swing.event._

class ChartPanel(chart: Chart) extends Component {

  override def paintComponent(g: Graphics2D) = {
    super.paintComponent(g)
    chart.draw(g, size.width, size.height)
  }

}

