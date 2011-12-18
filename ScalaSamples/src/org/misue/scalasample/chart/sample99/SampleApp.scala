// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample99

import swing._
import java.util._

object SampleApp extends SwingApplication {

  val chart: Chart = Chart.createSample(7)


  override def startup(args: Array[String]) {

    val panel = top
    if (panel.size == new Dimension(0, 0)) panel.pack()
    panel.visible = true

    val layoutMaker = new LayoutMaker(panel.size)
    layoutMaker.layout(chart)
  }

  def top = new ChartFrame(chart) {
    title = "SampleApp #1"
  }

}
