// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.gd.sample2backup

import swing._
import java.util._
//import org.misue.scalasample.chart.sample1._

object SampleGDApp2 extends SwingApplication {
    val cmdname = "SampleGDApp #2"
    val canvasSize = new Dimension(800, 600)

    override def startup(args: Array[String]): Unit = {

        if (args.length > 0) {
            val graph: Graph = DataReader.read(args(0))
            val layoutMaker = new LayoutMaker(canvasSize)
            val graphPanel = new GraphPanel(graph, layoutMaker)
            graphPanel.preferredSize = canvasSize

            val panel = new MainFrame() {
                title = cmdname
                contents = graphPanel
            }
            if (panel.size == new Dimension(0, 0)) panel.pack()
            panel.visible = true
            graphPanel.start
        } else {
            println("Specify a data file")
        }

    }

}
