// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample09d
import scala.swing._
//import javax.swing.SwingUtilities

class FileMenuFactory(mediator: Mediator) {

    def createMenu(title: String): Menu = {
        val menu = new Menu(title)
        menu.contents += new MenuItem(Action("Open...") {
            openFile()
        })
        return menu
    }

    def openFile(): Unit = {
        val filechooser = new FileChooser()
        val selected = filechooser.showOpenDialog(null /* JFrame is necessary */ )
        if (selected == FileChooser.Result.Approve)
            mediator ! ReadFile(filechooser.selectedFile)            
    }

}
