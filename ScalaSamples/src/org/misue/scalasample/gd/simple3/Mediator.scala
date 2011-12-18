// All Rights Reserved. Copyright (C) Kazuo Misue (2010)
// 2010.06.26
package org.misue.scalasample.gd.simple3

import scala.actors._
import scala.actors.Actor._
import swing.event._
import java.io.File
import java.util.Calendar

class Mediator extends Actor {

    val panel: GraphPanel = new GraphPanel(this)
    private var layoutFunc: LayoutFunc = null // = new LayoutFunc(graph)
    var graph: Graph = null
    var showLabel: Boolean = true
    var showStress: Boolean = false

    private val timer: javax.swing.Timer =
        new javax.swing.Timer(33,
            new java.awt.event.ActionListener {
                override def actionPerformed(evt: java.awt.event.ActionEvent) {
                    panel.repaint()
                }
            })
    timer.start()

    def act(): Unit = {

        val sleepTime: Int = 10

        def notifyLater() {
            val mainActor = self
            actor {
                Thread.sleep(sleepTime)
                mainActor ! "wakeup"
            }
        }

        notifyLater()
        loop {
            react {
                case "wakeup" => {
                    updateLayout()
                    notifyLater()
                }
                case ("readFile", filename: String) => {
                    graph = readData(filename)
                    layoutFunc = new LayoutFunc(graph)
                }
                case "showLabel" => switchShowLabel
                case "showStress" => switchShowStress
                case "scramble" => scramble
            }
        }
    }

    
    private def switchShowLabel(): Unit = showLabel = !showLabel
        
    private def switchShowStress(): Unit = showStress = !showStress

    private def scramble(): Unit = graph.scramble(panel.size)

    private def updateLayout(): Unit = {
        if (layoutFunc != null) {
            val beginTime = Calendar.getInstance.getTimeInMillis
            layoutFunc.update(panel.size)
            val endTime = Calendar.getInstance.getTimeInMillis
            println((endTime - beginTime) + " msec.")
        }
    }

    private def readData(filename: String): Graph =
        if (filename != null) Graph.readFile(new File(filename)) else null

}
