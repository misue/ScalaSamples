// All Rights Reserved. Copyright (C) Kazuo Misue (2010)
// 2010.06.26 Spring Model with Mouse-event handling and Buttons
// 2010.06.36 This can read CSV files.
package org.misue.scalasample.gd.sample3

import swing._
import swing.event._
import javax.swing.Timer
import java.awt.event.ActionListener
import java.awt.event.ActionEvent

object GraphDraw extends SwingApplication {
    val mediator: Mediator = new Mediator

    override def startup(args: Array[String]) {

        val panel = top

        if (panel.size == new Dimension(0, 0)) panel.pack()
        panel.visible = true
        mediator.start
        if (args.length > 0) {
            println("readFile: " + args(0))
            mediator ! ("readFile", args(0))
        }
    }

    def top = new MainFrame {

        val menuFile = new Menu("File")
        menuFile.contents += new MenuItem(Action("Open...") {
            mediator ! "openFile"
        })

        val menuInfo = new Menu("Info")
        menuInfo.contents += new CheckMenuItem("") {
            action = new Action("Show Info") {
                override def apply(): Unit = {
                    mediator ! "showInfo"
                }
            }
        }

        menuBar = new MenuBar {
            contents += menuFile
            contents += menuInfo
        }

        contents = new BoxPanel(Orientation.Vertical) {

            contents += mediator.panel

            contents += new BoxPanel(Orientation.Horizontal) {
                contents += new Button {
                    text = "Scramble"
                    listenTo(this)
                    reactions += {
                        case ButtonClicked(button) => mediator ! "scramble"
                    }
                }

                contents += new Button {
                    text = "Shake"
                    listenTo(this)
                    reactions += {
                        case ButtonClicked(button) => mediator ! "shake"
                    }
                }
            }
        }
        
//        contents = mediator.panel
    }

}
