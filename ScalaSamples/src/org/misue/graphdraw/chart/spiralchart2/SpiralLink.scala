// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.graphdraw.chart.spiralchart2

import java.util.Calendar
import java.awt.Color
import java.awt.Graphics2D
import org.misue.graphdraw.chart.basic._
import org.misue.graphdraw.chart.util.DateUtil
//import java.awt.Font
//import java.awt.FontMetrics

class SpiralLink(val edge: Edge) extends AbstractLink {
  var primePos: Double = 0.0
  
  // Constructor
  edge.link = this
 
  override def compPrimePos(date2pos: Calendar => Tuple3[Double, Double, Double]): Unit = {
    val (_, _, z) = date2pos(date)
    primePos = z
  }

  def compNormalPos(dateRange: Tuple2[Calendar, Calendar], ratio2pos: Double => Tuple3[Double, Double, Double]): Unit = {
    active = DateUtil.inRange(date, dateRange)
    if (active) {
      val (x, y, z) = ratio2pos(primePos)
      primePosX = x
      primePosY = y
      primePosZ = z
    }
  }
  
}

object SpiralLink {
  def create(edge: Edge): SpiralLink = new SpiralLink(edge)
}
