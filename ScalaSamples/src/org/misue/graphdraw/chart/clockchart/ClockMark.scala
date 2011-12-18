// All Rights Reserved. Copyright (C) Kazuo Misue (2010-2011)
package org.misue.graphdraw.chart.clockchart

import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics2D
import java.util.Calendar
import org.misue.graphdraw.chart.basic._
import java.awt.Font
import java.awt.FontMetrics

class ClockMark(val node: Node) extends AbstractMark {

  private var crntPosX = Double.NaN
  private var crntPosY = Double.NaN
  private var crntFreq = Double.NaN
  
  private var prevPosX = Double.NaN
  private var prevPosY = Double.NaN
  private var prevFreq = 0
  
  private var nextPosX = Double.NaN
  private var nextPosY = Double.NaN
  private var nextFreq = 0

  var realPosX: Int = -1000
  var realPosY: Int = -1000

//  private var active: Boolean = false

  // Constructor
  node.mark = this

  private def sumPos2D(firstDate: Calendar, lastDate: Calendar, showTail: Boolean, showHead: Boolean): Tuple4[Double, Double, Int, Int] = {
    val (tailX, tailY, tailCount) = if (showTail) sumPos2D(node.outEdges.map(_.link), firstDate, lastDate) else (0.0, 0.0, 0)
    val (headX, headY, headCount) = if (showHead) sumPos2D(node.inEdges.map(_.link), firstDate, lastDate) else (0.0, 0.0, 0)
    (tailX + headX, tailY + headY, tailCount, headCount)
  }  
  
  private def sumPos2D(links: List[AbstractLink], firstDate: Calendar, lastDate: Calendar): Tuple3[Double, Double, Int] = 
    sumPos2D(links, firstDate, lastDate, (x, y, z) => (x, y))
  
  private def sumPos2D(links: List[AbstractLink], firstDate: Calendar, lastDate: Calendar, 
      map3Dto2D: (Double, Double, Double) => Tuple2[Double, Double] = (x, y, z) => (x, y)): Tuple3[Double, Double, Int] = {

    def sumPos2D1(links: List[AbstractLink], sumX: Double, sumY: Double, count: Int): Tuple3[Double, Double, Int] = {
      links match {
        case List() => (sumX, sumY, count)
        case link :: rest if link.date.after(firstDate) => {
          if (lastDate.before(link.date)) (sumX, sumY, count)
          else {
            val (x, y) = map3Dto2D(link.primePosX, link.primePosY, link.primePosZ)
            sumPos2D1(rest, sumX + x, sumY + y, count + 1)
          }
        }
        case edge :: rest => sumPos2D1(rest, sumX, sumY, count)
      }
    }

    def sumPos2D2(links: List[AbstractLink], sumX: Double, sumY: Double, count: Int): Tuple3[Double, Double, Int] = {
      links match {
        case List() => (sumX, sumY, count)
        case link :: rest if (!lastDate.before(link.date)) => {
          val (x, y) = map3Dto2D(link.primePosX, link.primePosY, link.primePosZ)
          sumPos2D2(rest, sumX + x, sumY + y, count + 1)
        }
        case date :: rest => (sumX, sumY, count)
      }
    }

    if (firstDate == null) sumPos2D2(links, 0.0, 0.0, 0) else sumPos2D1(links, 0.0, 0.0, 0)
  }  
  

  // firstDate can be null.

  // Computes the normal position inside current time window.
  def compNormalPos(firstDate: Calendar, crntDate: Calendar, showTail: Boolean, showHead: Boolean): Unit = {
    prevPosX = nextPosX
    prevPosY = nextPosY
    prevFreq = nextFreq
    val (sumX, sumY, tailFreq, headFreq) = sumPos2D(firstDate, crntDate, showTail, showHead)
    nextFreq = tailFreq + headFreq
    if (nextFreq > 0) {
      active = true
      nextPosX = sumX / nextFreq
      nextPosY = sumY / nextFreq
      markColor = mixedColor(tailFreq, headFreq)
    } else {
      active = false
      nextPosX = Double.NaN
      nextPosY = Double.NaN
    }
//    if (prevPosX.isNaN) prevPosX = nextPosX
//    if (prevPosY.isNaN) prevPosY = nextPosY
  }
  

  // Interpolates the normal position of marks.
  def interpNormalPos(ratio: Double, showTail: Boolean, showHead: Boolean): Unit = {
//    active = true
//    if (!nextPosX.isNaN) {
    if (active) {
      if (prevFreq > 0) {
        crntPosX = interpolate(prevPosX, nextPosX, ratio)
        crntPosY = interpolate(prevPosY, nextPosY, ratio)
        crntFreq = interpolate(prevFreq, nextFreq, ratio)
      } else {
        crntPosX = nextPosX
        crntPosY = nextPosY
        crntFreq = nextFreq
      }
//    } else if (!prevPosX.isNaN) {
//      crntPosX = prevPosX
//      crntPosY = prevPosY
//      crntFreq = prevFreq
//    } else {
//      crntPosX = Double.NaN
//      crntPosY = Double.NaN
//      crntFreq = 0
//      active = false
    }
  }
  
  override def compPortPos(z: Double, map3DtoReal: (Double, Double, Double) => Tuple2[Int, Int]): Tuple2[Int, Int] = (realPosX, realPosY)

  // Computes real positions of active marks. These positions are also used to draw edges.
  def compPos(map2real: (Double, Double) => Tuple2[Int, Int]): Unit = {
    if (active) {
      val (x, y) = map2real(crntPosX, crntPosY)
      realPosX = x
      realPosY = y
    }
  }

  // Draws active marks.
  def draw(g: Graphics2D, labelMinFreq: Int): Unit = {
    if (active) {
      val diameter: Int = (6 * Math.sqrt(crntFreq)).toInt
      val radius = diameter / 2
      g.setColor(markColor)
      g.fillOval(realPosX - radius, realPosY - radius, diameter, diameter)
      if (crntFreq >= labelMinFreq) drawLabel(g, node.label, realPosX, realPosY, crntFreq)
    }
  }

}

object ClockMark {
  def create(node: Node): ClockMark = new ClockMark(node)
}
