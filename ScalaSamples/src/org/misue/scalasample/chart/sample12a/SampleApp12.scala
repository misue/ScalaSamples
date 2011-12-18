// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample12a

import swing._
import java.util._

object SampleApp12 extends SwingApplication {
    val cmdname = "SampleApp #12"
    val canvasSize = new Dimension(800, 600)

    override def startup(args: Array[String]): Unit = {

        if (args.length > 0) {
            val chart: Chart = DataReader.read(args(0))
            val chartPanel = new ChartPanel(chart)
            chartPanel.preferredSize = canvasSize

            val panel = new MainFrame() {
                title = cmdname
                contents = chartPanel
            }
            if (panel.size == new Dimension(0, 0)) panel.pack()
            panel.visible = true

            val layoutMaker = new LayoutMaker(chartPanel.size)
            layoutMaker.layout(chart)
        } else {
            println("Specify a data file")
        }

    }

}
