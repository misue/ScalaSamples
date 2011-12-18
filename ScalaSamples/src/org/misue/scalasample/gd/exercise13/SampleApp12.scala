// All Rights Reserved. Copyright (C) Kazuo Misue (2010)
// 2010.06.23-06.26 Spring Embedder Model
package org.misue.scalasample.gd.exercise13

import swing._
import swing.event._

object SampleApp12 extends SwingApplication {
	
  val graph: BasicGraph = new BasicGraph()

  override def startup(args: Array[String]) {
    import javax.swing.Timer
    import java.awt.event.ActionListener
    import java.awt.event.ActionEvent
    
    val t = top
    if (t.size == new Dimension(0,0)) t.pack()
    t.visible = true
    val timer1: Timer = new Timer(33,
	new ActionListener {
          override def actionPerformed(evt: ActionEvent) {
            t.repaint()
          }
	})
    timer1.start()

    val timer2: Timer = new Timer(3,
	new ActionListener {
          override def actionPerformed(evt: ActionEvent) {
//            println(graph.relax())
            graph.relax()
//            graph.findStableState()
          }
	})
    timer2.start()
  }
	
  def top = new MainFrame {
    contents = new Component {
      preferredSize = new Dimension(640, 480)
			
      override def paintComponent(g: Graphics2D) = {
	super.paintComponent(g)
	graph.draw(g, size.width, size.height)				
      }
    }
  }
}

