// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample8a

import scala.swing._

class FileMenuFactory(mediator: Mediator) {
    
    def createMenu(title: String): Menu = {
        new Menu(title) {
            contents += new MenuItem(Action("Open...") { openFile() })
        }
    }

    def openFile(): Unit = {
        val filechooser = new FileChooser()
        val selected = filechooser.showOpenDialog(null /* JFrame is necessary */ )
        if (selected == FileChooser.Result.Approve) 
            mediator ! ReadFile(filechooser.selectedFile)
    }

}
