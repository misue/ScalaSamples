// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample7
import scala.swing._

class FileMenuFactory(mediator: Mediator) {
  
  def createMenu(title: String): Menu = {
    val menu = new Menu(title)
    menu.contents += new MenuItem(Action("Open...") {
      println("point 2")
      openFile()
    })
    return menu
  }

  def openFile(): Unit = {
    val filechooser = new FileChooser()
    val selected = filechooser.showOpenDialog(null /* JFrame is necessary */)  
    if (selected == FileChooser.Result.Approve) 
      mediator.readData(filechooser.selectedFile)
  }

}
