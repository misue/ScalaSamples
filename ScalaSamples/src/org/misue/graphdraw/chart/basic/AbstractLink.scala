// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.graphdraw.chart.basic

import java.util.Calendar
import java.awt.Color
import java.awt.Graphics2D
//import java.awt.Font
//import java.awt.FontMetrics

abstract class AbstractLink {
  def edge: Edge
  def tail: Node = edge.tail
  def head: Node = edge.head
  def date: Calendar = edge.date
  var active: Boolean = false

  var primePosX: Double = Double.NaN
  var primePosY: Double = Double.NaN
  var primePosZ: Double = Double.NaN
  
  private val defaultLinkColor = Color.darkGray
  protected def linkColor: Color = defaultLinkColor

  def isVisible(firstDate: Calendar, crntDate: Calendar): Boolean =
    !crntDate.before(date) && ((firstDate == null) || date.after(firstDate))

  def checkVisibility(firstDate: Calendar, crntDate: Calendar): Unit = {
    active = isVisible(firstDate, crntDate)
  }
  
  def checkVisibility(dateRange: Tuple2[Calendar, Calendar]): Unit =
    checkVisibility(dateRange._1, dateRange._2)
  
  def compPrimePos(date2pos: Calendar => Tuple3[Double, Double, Double]): Unit = {
    val (x, y, z) = date2pos(date)
    primePosX = x
    primePosY = y
    primePosZ = z
  }
  
  def draw(g: Graphics2D, map3DtoReal: (Double, Double, Double) => Tuple2[Int, Int]): Unit = {
    if (active) {
      val (x0, y0) = tail.mark.compPortPos(primePosZ, map3DtoReal)
      val (x1, y1) = head.mark.compPortPos(primePosZ, map3DtoReal)
      g.setColor(linkColor)
      g.drawLine(x0, y0, x1, y1)
    }
  }

}
