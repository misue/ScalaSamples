// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample13a

import java.awt.Graphics2D
import swing._
import javax.swing.Timer
import java.awt.event._

class ChartPanel(chart: Chart, layoutMaker: LayoutMaker) extends Component {

    private val timer = new Timer(33, new ActionListener() {
        def actionPerformed(e: ActionEvent): Unit = {
            layoutChart
        }
    })

    def start: Unit = timer.start

    def stop: Unit = timer.stop

    private def layoutChart: Unit = {
        //		chart.layout(layoutMaker);
        layoutMaker ! chart
        repaint
    }

    override def paintComponent(g: Graphics2D) = {
        super.paintComponent(g)
        chart.draw(g, size.width, size.height)
    }

}

