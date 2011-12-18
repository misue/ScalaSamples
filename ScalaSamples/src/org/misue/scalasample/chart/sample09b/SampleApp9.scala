// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample09b

import swing._
import java.util._
import java.io.File
import scala.swing.event.ButtonClicked
import java.awt.Color
import scala.actors.Actor
import scala.actors._

//case class DefaultColor(color: Color)

object SampleApp9 extends SwingApplication {
    private val cmdname = "SampleApp #9"
    private val canvasSize: Dimension = new Dimension(800, 600)

    private var buttonRed: RadioButton = null
    private var buttonBlue: RadioButton = null

    private val actor = new Actor {
        override val scheduler = new SchedulerAdapter {
            def execute(fun: => Unit) { Swing.onEDT(fun) }
        }
        start()

        def act() {
            loop {
                react {
                    case DefaultColor(color) if ((buttonRed != null) && (buttonBlue != null)) => {
                        buttonRed.selected = (color == Color.red)
                        buttonBlue.selected = (color == Color.blue)
                    }
                }
            }
        }
    }

    override def startup(args: Array[String]): Unit = {
        val chartPanel = new ChartPanel()
        chartPanel.preferredSize = canvasSize
        val mediator = new Mediator(chartPanel, canvasSize)
        mediator.start()
        val panel = top(mediator, chartPanel)
        if (panel.size == new Dimension(0, 0)) panel.pack()
        panel.visible = true
        mediator ! SendDefaultColor(actor)
        if (args.length > 0) mediator ! ReadFile2(args(0))
    }

    private def top(mediator: Mediator, panel: Component) = new MainFrame() {
        title = cmdname

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
                    case ButtonClicked(button) => mediator ! AddItem
                }
            }

            // Setup Controls
            buttonRed = new RadioButton("Red") {
                listenTo(this)
                reactions += {
                    case ButtonClicked(button) => mediator ! SetItemColor(Color.red)
                }
            }
            contents += buttonRed

            buttonBlue = new RadioButton("Blue") {
                listenTo(this)
                reactions += {
                    case ButtonClicked(button) => mediator ! SetItemColor(Color.blue)
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
