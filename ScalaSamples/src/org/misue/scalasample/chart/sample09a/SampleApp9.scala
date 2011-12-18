// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample09a

import swing._
import java.util._
import java.io.File
import scala.swing.event.ButtonClicked
import java.awt.Color
import scala.actors.Actor
import scala.actors._

case class DefaultColor(color: Color)

object SampleApp9 extends SwingApplication {
    private val cmdname = "SampleApp #9"
    private val canvasSize: Dimension = new Dimension(800, 600)

    override def startup(args: Array[String]): Unit = {
        val chartPanel = new ChartPanel()
        chartPanel.preferredSize = canvasSize
        val mediator = new Mediator(chartPanel, canvasSize)
        mediator.start()
        val panel = top(mediator, chartPanel)
        if (panel.size == new Dimension(0, 0)) panel.pack()
        panel.visible = true
        if (args.length > 0) mediator ! ReadFile2(args(0))
    }

    private def top(mediator: Mediator, panel: Component) = new MainFrame() {
        title = cmdname

        val defaultColor: Color = mediator !? SendDefaultColor match {
            case DefaultColor(color) => color
            case _ => Color.black
        }

        // Setup Menu
        val fileMenuFactory = new FileMenuFactory(mediator)

        menuBar = new MenuBar {
            contents += fileMenuFactory.createMenu("File")
            // Add here if you want to more menus.
            // contents += ...
        }

        val ctrlPanel = new BoxPanel(Orientation.Vertical) {

            contents += new Button("Add Item") {
                listenTo(this)
                reactions += {
                    case ButtonClicked(button) => {
//                        new Thread(new Runnable() {
//                            override def run(): Unit = mediator ! AddItem
//                        }).start
                        mediator ! AddItem
                    }
                }
            }

            // Setup Controls
            val buttonRed = new RadioButton("Red") {
                selected = (defaultColor == Color.red)
                listenTo(this)
                reactions += {
                    case ButtonClicked(button) => {
                        mediator ! SetItemColor(Color.red)
                    }
                }
            }
            contents += buttonRed

            val buttonBlue = new RadioButton("Blue") {
                selected = (defaultColor == Color.blue)
                listenTo(this)
                reactions += {
                    case ButtonClicked(button) => {
                        mediator ! SetItemColor(Color.blue)
                    }
                }
            }
            contents += buttonBlue

            val buttonGroup = new ButtonGroup() {
                buttons += buttonRed
                buttons += buttonBlue
            }

            // Add here if you want to add more GUI parts.
            // contents += ...
        }

        contents = new BorderPanel {
            add(ctrlPanel, BorderPanel.Position.West)
            add(panel, BorderPanel.Position.Center)
        }

    }
}
