// All Rights Reserved. Copyright (C) Kazuo Misue (2010)
package org.misue.graphdraw.chart

import scala.actors._
import scala.actors.Actor._

class LayoutActor(mediator: Mediator) extends Actor {
  
  def act() {
    
    var active: Boolean = true
    var sleepTime: Int = 30
    
    def emoteLater() {
      val mainActor = self
      actor {
        Thread.sleep(sleepTime)
        mainActor ! "Emote"
      }
    }
    
    emoteLater()
    loop {
      react {
        case "Emote" => 
          if (active) {
            mediator.relax()
          }
          mediator.panel.repaint()
          emoteLater()
//        case "Init" =>
//          mediator.initLayout()
        case s: Int => {
            if (s > 0) {
              sleepTime = s
              active = true
            } else {
              active = false
            }
        }
      }
    }
  }
}
