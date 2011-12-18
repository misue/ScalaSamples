// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.gd.sample2backup

import java.awt.Graphics2D
import swing._
import javax.swing.Timer
import java.awt.event._
import scala.actors.Actor
//import java.util.Calendar

class GraphPanel(graph: Graph, layoutMaker: LayoutMaker) extends Component with Actor {

//    var oldTime: Long = 0

    private val timer = new Timer(33, new ActionListener() {
        def actionPerformed(e: ActionEvent): Unit = {

//            val crntTime = Calendar.getInstance.getTimeInMillis
//            println(crntTime - oldTime)
//            oldTime = crntTime

            layoutChart

        }
    })

    private def layoutChart: Unit = {
        layoutMaker.layout(graph)
        repaint
    }

    override def paintComponent(g: Graphics2D) = {
        super.paintComponent(g)
//        graph.draw(g, size.width, size.height)
    }

    override def act(): Unit = {
        timer.start
        loop {
            react {
                case "start" => timer.start
                case "stop" => timer.stop
            }
        }
    }

}

