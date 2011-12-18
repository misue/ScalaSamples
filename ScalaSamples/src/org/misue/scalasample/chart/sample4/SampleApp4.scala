// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample4

import swing._
import java.util._

object SampleApp4 extends SwingApplication {
  val cmdname = "SampleApp #4"
  val defaultItemNum = 7
  val canvasSize = new Dimension(800, 600)
  
  override def startup(args: Array[String]): Unit = {

    val itemNum = if (args.length > 0) args(0).toInt else defaultItemNum
    
    val chart: Chart = Chart.createSample(itemNum)
    val chartPanel = new ChartPanel(chart)
    chartPanel.preferredSize = canvasSize
    
    val panel = new MainFrame() {
      title = cmdname
      contents = chartPanel
    }
    if (panel.size == new Dimension(0, 0)) panel.pack()
    panel.visible = true

    val layoutMaker = new LayoutMaker
    layoutMaker.layout(chart)

  }
  
}
