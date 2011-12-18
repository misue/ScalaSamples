// All Rights Reserved. Copyright (C) Kazuo Misue (2010-2011)
// (eg.) -file data/user2orgTwUser_with_timestamp_20110114.csv
// (eq.) -file data/now20110620-2.csv
package org.misue.graphdraw.chart

import swing._
import java.util._

object SampleApp2 extends SwingApplication {

  val mediator: Mediator = new Mediator
  
  override def startup(args: Array[String]) {

    def checkParam(i: Int): Option[String] = {
      if (i + 1 >= args.length) {
        None
      } else if ("-file".equals(args(i))) {
        Some(args(i + 1))
      } else {
        checkParam(i + 1)
      }
    }
    
    val panel = top
    if (panel.size == new Dimension(0,0)) panel.pack()
    panel.visible = true
    
    checkParam(0) match {
      case Some(f) => mediator.readFile(f) 
      case None =>
    }
      
    mediator.start()
    println("startup done.")
  }
  
  def top = new ChartFrame(mediator) {
    title = "SampleApp #2"
  }

}
