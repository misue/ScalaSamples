// All Rights Reserved. Copyright (C) Kazuo Misue (2010)
// 2010.06.26 Spring Model with Mouse-event handling and Buttons
// 2010.06.36 This can read CSV files.
package org.misue.scalasample.gd.sample3b

import swing._
import swing.event._

object SampleGDApp3 extends SwingApplication {
    val graphPanel = new GraphPanel()
    val mediator = new Mediator(graphPanel)

    override def startup(args: Array[String]) {

        val frame = top

        if (frame.size == new Dimension(0, 0)) frame.pack()
        frame.visible = true
        mediator.start
        if (args.length > 0) {
            println("readFile: " + args(0))
            mediator ! ReadFile(args(0))
        }
    }

    def top = new MainFrame {

        val menuFile = new Menu("File")
        menuFile.contents += new MenuItem(Action("Open...") {
            mediator ! OpenFile
        })

        val menuInfo = new Menu("Info") {
            contents += new CheckMenuItem("") {
                action = new Action("Show Stress") {
                    override def apply(): Unit = mediator ! ShowStress
                }
            }
        }

        menuBar = new MenuBar {
            contents += menuFile
            contents += menuInfo
        }

        contents = new BoxPanel(Orientation.Vertical) {

            contents += new BoxPanel(Orientation.Horizontal) {
                contents += new Button {
                    text = "Scramble"
                    listenTo(this)
                    reactions += {
                        case ButtonClicked(button) => mediator ! Scramble
                    }
                }

                contents += new Button {
                    text = "Shake"
                    listenTo(this)
                    reactions += {
                        case ButtonClicked(button) => mediator ! Shake
                    }
                }
            }
            contents += graphPanel

        }
    }

}
