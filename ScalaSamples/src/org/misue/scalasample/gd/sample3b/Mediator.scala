// All Rights Reserved. Copyright (C) Kazuo Misue (2010)
// 2010.06.26
package org.misue.scalasample.gd.sample3b

import scala.actors._
import scala.actors.Actor._
import swing.event._
import java.io.File
import java.util.Calendar

case object Wakeup
case object OpenFile
case class ReadFile(fileneme: String)
case object ShowLabel
case object ShowStress
case object Scramble
case object Shake

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
                case OpenFile => openFile()
                case ShowStress => switchShowStress()
                case Scramble => scramble()
                case Shake => shake()
            }
        }
    }

    private def readData(filename: String): Graph =
        if (filename != null) Graph.readFile(new File(filename)) else null

    private def openFile(): Unit = {
        def openDialog(): Graph = {
            import javax.swing.JFileChooser
            val filechooser: JFileChooser = new JFileChooser()
            val selected: Int = filechooser.showOpenDialog(null /* JFrame is necessary */ );
            if (selected == JFileChooser.APPROVE_OPTION) {
                val file: File = filechooser.getSelectedFile()
                println("Open file: " + file)
                Graph.readFile(file)
            } else null
        }

        graph = openDialog()
        graph.showStress = showStress
        graphPanel.graph = graph
        layoutFunc = new LayoutFunc(graph)
    }

    private def readFile(filename: String): Unit = {
        graph = readData(filename)
        graph.showStress = showStress
        graphPanel.graph = graph
        layoutFunc = new LayoutFunc(graph)
    }

    private def switchShowStress(): Unit = {
        showStress = !showStress
        graph.showStress = showStress
    }

    private def scramble(): Unit = graph.scramble(graphPanel.size)

    private def shake(): Unit = graph.shake()

    private def updateLayout(): Unit = {
        if (layoutFunc != null) {
            val beginTime = Calendar.getInstance.getTimeInMillis
            layoutFunc.update(graphPanel.size)
            val endTime = Calendar.getInstance.getTimeInMillis
            println((endTime - beginTime) + " msec.")
        }
    }

}
