// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample6
import scala.swing._
import javax.swing.SwingUtilities

class FileMenuFactory(mediator: Mediator) {

    def createMenu(title: String): Menu = {
        val menu = new Menu(title)
        menu.contents += new MenuItem(Action("Open...") {
//            println("point 2")
            openFile()
        })
        return menu
    }

    def openFile(): Unit = {
        val filechooser = new FileChooser()
        val selected = filechooser.showOpenDialog(null /* JFrame is necessary */ )
        if (selected == FileChooser.Result.Approve) {
            println("1 On EDT:" + SwingUtilities.isEventDispatchThread())
            new Thread(new Runnable() {
		        override def run(): Unit = mediator.readData(filechooser.selectedFile)
		    }).start
        }
            
    }

}
