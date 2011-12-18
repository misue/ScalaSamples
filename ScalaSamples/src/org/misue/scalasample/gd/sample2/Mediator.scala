// All Rights Reserved. Copyright (C) Kazuo Misue (2010)
// 2010.06.26
package org.misue.scalasample.gd.sample2

import scala.actors._
import scala.actors.Actor._
import swing.event._
import java.io.File

class Mediator extends Actor {

    val panel: GraphPanel = new GraphPanel(this)
    private var layoutFunc: LayoutFunc = null // = new LayoutFunc(graph)
    var graph: Graph = null
    var showStress: Boolean = false

    private val timer: javax.swing.Timer =
        new javax.swing.Timer(100,
            new java.awt.event.ActionListener {
                override def actionPerformed(evt: java.awt.event.ActionEvent) {
                    panel.repaint()
                }
            })
    timer.start()

    def act(): Unit = {

        val sleepTime: Int = 10

        def emoteLater() {
            val mainActor = self
            actor {
                Thread.sleep(sleepTime)
                mainActor ! "Emote"
            }
        }

        emoteLater()
        loop {
            react {
                case "Emote" => {
                    relax()
                    emoteLater()
                }
                case ("readFile", filename: String) => {
                    graph = readData(filename)
                    layoutFunc = new LayoutFunc(graph)
                }
                case "openFile" => {
                    openFile()
                    layoutFunc = new LayoutFunc(graph)
                }
                case "showInfo" => showInfo
                case "scramble" => scramble
                case "shake" => shake
            }
        }
    }

    private def openFile(): Unit = {
        import javax.swing.JFileChooser
        val filechooser: JFileChooser = new JFileChooser()
        val selected: Int = filechooser.showOpenDialog(null /* JFrame is necessary */ );
        if (selected == JFileChooser.APPROVE_OPTION) {
            val file: File = filechooser.getSelectedFile()
            println("Open file: " + file)
            graph = readData(file)
        }
    }

    private def showInfo(): Unit = {
        showStress = !showStress
    }

    private def scramble(): Unit = graph.scramble(panel.size)

    private def shake(): Unit = graph.shake()

    private def relax(): Unit = {
        if (layoutFunc != null) {
            layoutFunc.relax(panel.size)
            panel.repaint()
        }
    }

    private def readData(file: File): Graph = {
        import scala.io.Source

        val graph: Graph = new Graph()
        for (line <- Source.fromFile(file).getLines) {
            println(line)
            val tokens = line split ","
            tokens.length match {
                case 1 => graph.addNode(tokens(0))
                case 3 => graph.addEdge(tokens(0), tokens(1), tokens(2).toInt)
                case _ =>
            }
        }
        graph
    }
    
    private def readData(filename: String): Graph =
        if (filename != null) readData(new File(filename)) else null


}
