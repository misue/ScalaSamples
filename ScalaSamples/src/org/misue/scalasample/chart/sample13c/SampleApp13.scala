// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample13c

import swing._
import java.util._
//import org.misue.scalasample.chart.sample1._

object SampleApp13 extends SwingApplication {
    val cmdname = "SampleApp #13"
    val canvasSize = new Dimension(800, 600)
    
    val chartPanel = new ChartPanel()
    val mediator = new Mediator(chartPanel)

    override def startup(args: Array[String]): Unit = {

        if (args.length > 0) {
//            val chart: Chart = DataReader.read(args(0))
//            val layoutMaker = new LayoutMaker(canvasSize)
//            val chartPanel = new ChartPanel(chart, layoutMaker)
            chartPanel.preferredSize = canvasSize

            val frame = new MainFrame() {
                title = cmdname
                contents = chartPanel
            }
            if (frame.size == new Dimension(0, 0)) frame.pack()
            frame.visible = true
//            chartPanel.start
            mediator.start // as an actor
            
            println("Reading file:" + args(0))
            mediator ! ReadFile2(args(0))
        } else {
            println("Specify a data file")
        }

    }

}
