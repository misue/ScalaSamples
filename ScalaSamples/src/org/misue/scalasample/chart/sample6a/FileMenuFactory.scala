// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample6a
import scala.swing._
//import javax.swing.SwingUtilities

class FileMenuFactory(mediator: Mediator) {

    def createMenu(title: String): Menu = {
        new Menu(title) {
            contents += new MenuItem(Action("Open...") { openFile() })
        }
    }

    def openFile(): Unit = {
        val filechooser = new FileChooser()
        val selected = filechooser.showOpenDialog(null /* JFrame is necessary */ )
        if (selected == FileChooser.Result.Approve) {
//            println("1 On EDT:" + SwingUtilities.isEventDispatchThread())
            mediator ! ReadFile(filechooser.selectedFile)
        }            
    }

}
