// All Rights Reserved. Copyright (C) Kazuo Misue (2010)
// 2010.06.26

package org.misue.scalasample.gd.exercise8

import swing.event._
import java.io.File

class Mediator {

  val panel: GraphPanel = new GraphPanel(this)
  var layoutFunc: LayoutFunc = null// = new LayoutFunc(graph)
  var graph: Graph = null
  var showStress: Boolean = false
  
  def openFile(): Unit = {
    import javax.swing.JFileChooser
    val filechooser: JFileChooser  = new JFileChooser()
    val selected: Int = filechooser.showOpenDialog(null /* JFrame is necessary */);
    if (selected == JFileChooser.APPROVE_OPTION) {
      val file: File = filechooser.getSelectedFile()
      println("Open file: " + file)
      graph = readData(file)
      layoutFunc = new LayoutFunc(graph)
    }
  }

  def showInfo(): Unit = {
    showStress = !showStress
  }

  def scramble(): Unit = graph.scramble(panel.size)

  def shake(): Unit = graph.shake()


  def relax(): Unit = {
    if (layoutFunc != null) {
      layoutFunc.relax(panel.size)
      panel.repaint()
    }
  }

  def readData(file: File): Graph = {
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

}
