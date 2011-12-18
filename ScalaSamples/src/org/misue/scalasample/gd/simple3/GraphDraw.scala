// All Rights Reserved. Copyright (C) Kazuo Misue (2010)
// 2010.06.26 Spring Model with Mouse-event handling and Buttons
// 2010.06.36 This can read CSV files.
package org.misue.scalasample.gd.simple3

import swing._
import swing.event._

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

        val menuInfo = new Menu("Info") {
            contents += new CheckMenuItem("") {
                selected = true
                action = new Action("Show Label") {
                    override def apply(): Unit = mediator ! "showLabel"
                }
            }
            contents += new CheckMenuItem("") {
                action = new Action("Show Stress") {
                    override def apply(): Unit = mediator ! "showStress"
                }
            }
        }

        menuBar = new MenuBar {
            contents += menuInfo
        }

        contents = new BoxPanel(Orientation.Vertical) {

            contents += new BoxPanel(Orientation.Horizontal) {
                contents += new Button {
                    text = "Scramble"
                    listenTo(this)
                    reactions += {
                        case ButtonClicked(button) => mediator ! "scramble"
                    }
                }
            }
            contents += mediator.panel

        }
    }

}
