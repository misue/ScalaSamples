// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample8a

import swing._
import java.util._
import java.io.File
import scala.swing.event.ButtonClicked
import javax.swing.SwingUtilities

object SampleApp8 extends SwingApplication {
    val cmdname = "SampleApp #8"
    val canvasSize: Dimension = new Dimension(800, 600)

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

            contents += new Button("Add Item") {
                listenTo(this)
                reactions += {
                    case ButtonClicked(button) => {
                        println("1 On EDT:" + SwingUtilities.isEventDispatchThread())
                        mediator ! AddItem
                    }
                }
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