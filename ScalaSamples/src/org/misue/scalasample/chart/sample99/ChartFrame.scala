// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample99


import swing._
import swing.event._

class ChartFrame(chart: Chart) extends MainFrame {

  val panel: ChartPanel = new ChartPanel(chart)
  preferredSize = new Dimension(800, 600)
  contents = panel
  
}
