// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample7

import swing._
import java.util._
//import javax.swing.JFileChooser
import java.io.File

object SampleApp7 extends SwingApplication {
  val cmdname = "SampleApp #7"
  val canvasSize: Dimension = new Dimension(800, 600)

  override def startup(args: Array[String]) {
    val chartPanel = new ChartPanel()
    chartPanel.preferredSize = canvasSize
    val mediator = new Mediator(chartPanel, canvasSize)
    val panel = top(mediator, chartPanel)
    if (panel.size == new Dimension(0, 0)) panel.pack()
    panel.visible = true
    if (args.length > 0) mediator.readData(args(0))
  }

  def top(mediator: Mediator, panel: Component) = new MainFrame() {
    title = cmdname
    preferredSize = canvasSize

    // Setup Menu
    val fileMenuFactory = new FileMenuFactory(mediator)

    menuBar = new MenuBar {
      contents += fileMenuFactory.createMenu("File")
    }

    contents = panel
  }
}
