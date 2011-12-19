// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package visual.sample01

import swing._
//import java.util._
import javax.swing.SwingUtilities

object SampleApp01 extends SwingApplication {
    private val cmdname = "SampleApp #01"
    private val canvasSize = new Dimension(800, 600)
    private val itemNum = 7

    override def startup(args: Array[String]): Unit = {

        println("1 On EDT: " + SwingUtilities.isEventDispatchThread)

        // Create a chart and a chart panel
        val chart: Chart = Chart.createSample(itemNum)
        val chartPanel = new ChartPanel(chart)
        chartPanel.preferredSize = canvasSize

        // Create the main frame
        val frame = top(chartPanel)
        if (frame.size == new Dimension(0, 0)) frame.pack()
        frame.visible = true

        // Create a layout maker
        val layoutMaker = new LayoutMaker(chartPanel.size)
        layoutMaker.layout(chart)
    }
    
    private def top(panel: Component) = new MainFrame() {
        title = cmdname
        contents = panel
    }

}
