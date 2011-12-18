// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample01b

import java.awt.Graphics2D
import swing._

class ChartPanel(val chart: Chart) extends Component {

    override def paintComponent(g: Graphics2D) = {
        super.paintComponent(g)
        chart.draw(g, size.width, size.height)
    }

}
