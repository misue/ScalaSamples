// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample8a

import java.awt.Graphics2D
import swing._

class ChartPanel(var chart: Chart = null) extends Component {

    def setChart(chart: Chart): Unit = {
        this.chart = chart
        repaint
    }

    override def paintComponent(g: Graphics2D) = {
        super.paintComponent(g)
        if (chart != null) chart.draw(g, size.width, size.height)
    }

}
