// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample14

import java.awt.Graphics2D
import swing._
import javax.swing.Timer
import java.awt.event._
import scala.actors._
import scala.actors.Actor._
//import java.util.Calendar

//class ChartPanel(chart: Chart, layoutMaker: LayoutMaker) extends Component with Actor {
class ChartPanel(chart: Chart, layoutMaker: LayoutMaker) extends Actor {
    val panel = new Component() {
        override def paintComponent(g: Graphics2D) = {
            super.paintComponent(g)
            chart.draw(g, size.width, size.height)
        }
    }

    private def layoutChart: Unit = {
        layoutMaker.layout(chart)
        panel.repaint
    }

    override def act(): Unit = {
        var sleepTime: Int = 33
//        var oldTime: Long = 0

        def notifyLater(): Unit = {
            val mainActor: Actor = self
            actor {
                Thread.sleep(sleepTime)
                mainActor ! "wakeup"
            }
        }

        notifyLater
        loop {
            react {
                case "wakeup" => {
                    notifyLater
                    layoutChart
                    panel.repaint
//                    val crntTime = Calendar.getInstance.getTimeInMillis                    
//                    println(crntTime - oldTime)
//                    oldTime = crntTime
                }
            }
        }
    }

}

