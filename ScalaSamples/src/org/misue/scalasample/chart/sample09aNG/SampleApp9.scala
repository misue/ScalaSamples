// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample09aNG

import swing._
import java.util._
import java.io.File
import scala.swing.event.ButtonClicked
import java.awt.Color

object SampleApp9 extends SwingApplication {
    val cmdname = "SampleApp #9"
    val canvasSize: Dimension = new Dimension(800, 600)
//	private val initFilename
	private val color1 = Color.red
    private val color2 = Color.blue

    override def startup(args: Array[String]): Unit = {
        val chartPanel = new ChartPanel()
        chartPanel.preferredSize = canvasSize
        val mediator = new Mediator(chartPanel, canvasSize)
        mediator.start()
        val panel = top(mediator, chartPanel)
        if (panel.size == new Dimension(0, 0)) panel.pack()
        panel.visible = true
        if (args.length > 0) mediator ! ("read", args(0))
    }

    def top(mediator: Mediator, panel: Component) = new MainFrame() {
        title = cmdname

        // Setup Menu
        val fileMenuFactory = new FileMenuFactory(mediator)

        menuBar = new MenuBar {
            contents += fileMenuFactory.createMenu("File")
            // Add here if you want to more menus.
            // contents += ...
        }

        val ctrlPanel = new BoxPanel(Orientation.Vertical) {

//            contents += new Button("Add Item") {
//                listenTo(this)
//                reactions += {
//                    case ButtonClicked(button) => {
//                        new Thread(new Runnable() {
//                            override def run(): Unit = mediator.addItem()
//                        }).start                        
//                    }
//                }
//            }

            // Setup Controls
            val buttonRed = new RadioButton("Red") {
                selected = (mediator.defaultColor == color1)
                listenTo(this)
                reactions += {
                    case ButtonClicked(button) => {
//                        new Thread(new Runnable() {
//                            override def run(): Unit = mediator.setItemColor(color1)
//                        }).start
                        mediator ! ("setItemColor", color1)
                    }
                }
            }
            contents += buttonRed
            
            val buttonBlue = new RadioButton("Blue") {
                selected = (mediator.defaultColor == color2)
                listenTo(this)
                reactions += {
                    case ButtonClicked(button) => {
//                        new Thread(new Runnable() {
//                            override def run(): Unit = mediator.setItemColor(color2)
//                        }).start
                        mediator ! ("setItemColor", color2)
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
