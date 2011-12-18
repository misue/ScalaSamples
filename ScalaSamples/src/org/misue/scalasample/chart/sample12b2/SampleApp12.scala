// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample12b2

import swing._
import java.util._

object SampleApp12 extends SwingApplication {
    private val cmdname = "SampleApp #12b"
    private val canvasSize = new Dimension(800, 600)

    override def startup(args: Array[String]): Unit = {
        if (args.length == 0) {
            println("Specify a file name.")
            exit(0)
        }
        
        // Create a chart panel
        val chartPanel = new ChartPanel
        chartPanel.preferredSize = canvasSize
        
        // Create the mediator and start it
        val mediator = new Mediator(chartPanel, canvasSize)
        mediator.start() // start actor
        
        // Create the main frame
        val frame = top(mediator, chartPanel)
        if (frame.size == new Dimension(0, 0)) frame.pack()
        frame.visible = true
        
        // Read file specified by a command line argument
        mediator ! ReadFile2(args(0))
    }
    
    private def top(mediator: Mediator, panel: Component) = new MainFrame() {
        title = cmdname
        contents = panel
    }

}
