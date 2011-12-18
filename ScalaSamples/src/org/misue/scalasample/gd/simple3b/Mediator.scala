// All Rights Reserved. Copyright (C) Kazuo Misue (2010)
// 2010.06.26
package org.misue.scalasample.gd.simple3b

import scala.actors._
import scala.actors.Actor._
import swing.event._
import java.io.File
import java.util.Calendar

case object Wakeup
case class ReadFile(fileneme: String)
case object ShowLabel
case object ShowStress
case object Scramble

class Mediator(graphPanel: GraphPanel) extends Actor {

    private var graph: Graph = null
    private var layoutFunc: LayoutFunc = null
    private var showLabel: Boolean = true
    private var showStress: Boolean = false

//    private val timer: javax.swing.Timer =
//        new javax.swing.Timer(33,
//            new java.awt.event.ActionListener {
//                override def actionPerformed(evt: java.awt.event.ActionEvent) {
//                    graphPanel.repaint()
//                }
//            })
//    timer.start()

    def act(): Unit = {

        val sleepTime: Int = 10

        def notifyLater() {
            val mainActor = self
            actor {
                Thread.sleep(sleepTime)
                mainActor ! Wakeup
            }
        }

        notifyLater()
        loop {
            react {
                case Wakeup =>
                    updateLayout()
                    notifyLater()
                case ReadFile(filename) => readFile(filename)
                case ShowLabel => switchShowLabel
                case ShowStress => switchShowStress
                case Scramble => scramble
            }
        }
    }

    private def readFile(filename: String): Unit = {
        graph = readData(filename)
        graph.showLabel = showLabel
        graph.showStress = showStress
        graphPanel.graph = graph
        layoutFunc = new LayoutFunc(graph)
    }

    private def switchShowLabel(): Unit = {
        showLabel = !showLabel
        graph.showLabel = showLabel
    }

    private def switchShowStress(): Unit = {
        showStress = !showStress
        graph.showStress = showStress
    }

    private def scramble(): Unit = graph.scramble(graphPanel.size)

    private def updateLayout(): Unit = {
        if (layoutFunc != null) {
            val beginTime = Calendar.getInstance.getTimeInMillis
            layoutFunc.update(graphPanel.size)
            val endTime = Calendar.getInstance.getTimeInMillis
            println((endTime - beginTime) + " msec.")
        }
    }

    private def readData(filename: String): Graph =
        if (filename != null) Graph.readFile(new File(filename)) else null

}
