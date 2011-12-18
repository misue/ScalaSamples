// All Rights Reserved. Copyright (C) Kazuo Misue (2010)
// 2010.06.23-06.26 Spring Embedder Model
package org.misue.scalasample.gd.sample1

import swing._
import swing.event._
import javax.swing.Timer
import java.awt.event.ActionListener
import java.awt.event.ActionEvent

object SampleApp12 extends SwingApplication {

    override def startup(args: Array[String]) {

        if (args.length > 0) {
            val graph: BasicGraph = BasicGraph.create(args(0))
            val panel = new MainFrame {
                contents = new Component {
                    preferredSize = new Dimension(640, 480)

                    override def paintComponent(g: Graphics2D) = {
                        super.paintComponent(g)
                        graph.draw(g, size.width, size.height)
                    }
                }
            }

            if (panel.size == new Dimension(0, 0)) panel.pack()
            panel.visible = true

            val timer1: Timer = new Timer(33,
                new ActionListener {
                    override def actionPerformed(evt: ActionEvent) {
                        panel.repaint()
                    }
                })
            timer1.start()

            val timer2: Timer = new Timer(3,
                new ActionListener {
                    override def actionPerformed(evt: ActionEvent) {
                        new Thread(new Runnable() {
                            override def run(): Unit = graph.relax()
                        }).start
                    }
                })
            timer2.start()

        } else {
            println("Specify a data file")
        }
    }

}

