// All Rights Reserved. Copyright (C) Kazuo Misue (2010-2011)
package org.misue.scalasample.gd.simple3b

import swing._
import java.awt.FontMetrics

class GraphPanel(var graph: Graph = null) extends Component {

    preferredSize = new Dimension(640, 480)

    private val timer: javax.swing.Timer =
        new javax.swing.Timer(33,
            new java.awt.event.ActionListener {
                override def actionPerformed(evt: java.awt.event.ActionEvent) {
                    repaint()
                }
            })
    timer.start()

    override def paintComponent(g: Graphics2D) = {
        super.paintComponent(g)
        val fm: FontMetrics = g.getFontMetrics
        if (graph != null) graph.draw(g, fm)
    }

}
