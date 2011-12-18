// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.graphdraw.chart.spiralchart2

import org.misue.graphdraw.chart.basic._
import java.util.Calendar
import java.awt.Graphics2D
import java.awt.BasicStroke

class SpiralMark(val node: Node) extends AbstractMark {
  
  private var crntPosX: Double = Double.NaN
  private var crntPosY: Double = Double.NaN
  private var crntPosZ: Tuple2[Double, Double] = (Double.NaN, Double.NaN)
  private var crntFreq = Double.NaN
  
  private var prevPosX: Double = Double.NaN
  private var prevPosY: Double = Double.NaN
  private var prevPosZ: Tuple2[Double, Double] = (Double.NaN, Double.NaN)
  private var prevFreq = Double.NaN
  
  private var nextPosX: Double = Double.NaN
  private var nextPosY: Double = Double.NaN  
  private var nextPosZ: Tuple2[Double, Double] = (Double.NaN, Double.NaN)
  private var nextFreq = Double.NaN

  private var crntRadius: Int = 0
  
  // Constructor
  node.mark = this
  
  private def sumPos3D(dateRange: Tuple2[Calendar, Calendar], showTail: Boolean, showHead: Boolean): Tuple6[Double, Double, Double, Double, Int, Int] = {
    val (tailX, tailY, tailMinZ, tailMaxZ, tailCount) = 
      if (showTail) sumPos3D(node.outEdges.map(_.link), dateRange) else (0.0, 0.0, Double.MaxValue, Double.MinValue, 0)
    val (headX, headY, headMinZ, headMaxZ, headCount) = 
      if (showHead) sumPos3D(node.inEdges.map(_.link), dateRange) else (0.0, 0.0, Double.MaxValue, Double.MinValue, 0)
    (tailX + headX, tailY + headY, Math.min(tailMinZ, headMinZ), Math.max(tailMaxZ, headMaxZ), tailCount, headCount)
  }
  
  private def sumPos3D(links: List[AbstractLink], dateRange: Tuple2[Calendar, Calendar]): Tuple5[Double, Double, Double, Double, Int] = {
    val (beginDate, endDate) = dateRange

    def sumPos3D1(links: List[AbstractLink], sumX: Double, sumY: Double, minZ: Double, maxZ: Double, count: Int): Tuple5[Double, Double, Double, Double, Int] = {
      links match {
        case List() => (sumX, sumY, minZ, maxZ, count)
        case edge :: rest if edge.date.after(beginDate) => {
          if (endDate.before(edge.date))
        	(sumX, sumY, minZ, maxZ, count)
          else {
            val x = sumX + edge.primePosX
            val y = sumY + edge.primePosY
            val z0 = Math.min(minZ, edge.primePosZ)
            val z1 = Math.max(maxZ, edge.primePosZ)
        	sumPos3D1(rest, x, y, z0, z1, count + 1)
          }
        }
        case edge :: rest => sumPos3D1(rest, sumX, sumY, minZ, maxZ, count)
      }
    }

    def sumPos3D2(links: List[AbstractLink], sumX: Double, sumY: Double, minZ: Double, maxZ: Double, count: Int): Tuple5[Double, Double, Double, Double, Int] = {
      links match {
        case List() => (sumX, sumY, minZ, maxZ, count)
        case link :: rest if (!endDate.before(link.date)) => {
          val x = sumX + link.primePosX
          val y = sumY + link.primePosY
          val z0 = Math.min(minZ, link.primePosZ)
          val z1 = Math.max(maxZ, link.primePosZ)
          sumPos3D2(rest, x, y, z0, z1, count + 1)
        }
        case date :: rest => (sumX, sumY, minZ, maxZ, count)
      }
    }

    if (beginDate == null) 
      sumPos3D2(links, 0.0, 0.0, Double.MaxValue, Double.MinValue, 0) 
    else 
      sumPos3D1(links, 0.0, 0.0, Double.MaxValue, Double.MinValue, 0)
  }  
  

  def compNormalPos(dateRange: Tuple2[Calendar, Calendar], showTail: Boolean, showHead: Boolean): Unit = {
    prevPosX = nextPosX
    prevPosY = nextPosY
    prevPosZ = nextPosZ
    prevFreq = nextFreq
    val (sumX, sumY, minZ, maxZ, tailFreq, headFreq) = sumPos3D(dateRange, showTail, showHead)
    nextFreq = tailFreq + headFreq
    if (nextFreq > 0) {
      active = true
      nextPosX = sumX / nextFreq
      nextPosY = sumY / nextFreq
      nextPosZ = (minZ, maxZ)
      markColor = mixedColor(tailFreq, headFreq)
    } else {
      active = false
      nextPosX = Double.NaN
      nextPosY = Double.NaN
      nextPosZ = (Double.NaN, Double.NaN)
    }
//    if (prevPosX.isNaN) prevPosX = nextPosX
//    if (prevPosY.isNaN) prevPosY = nextPosY
//    if (prevPosZ._1.isNaN || prevPosZ._2.isNaN) prevPosZ = nextPosZ
  }
  
  def interpNormalPos(ratio: Double, showTail: Boolean, showHead: Boolean): Unit = {
//    active = true
//    if (!nextPosX.isNaN) {
    if (active) {
      if (prevFreq > 0) {
        crntPosX = interpolate(prevPosX, nextPosX, ratio)
        crntPosY = interpolate(prevPosY, nextPosY, ratio)
        crntPosZ = interpolate(prevPosZ, nextPosZ, ratio)
        crntFreq = interpolate(prevFreq, nextFreq, ratio)
      } else {
        crntPosX = nextPosX
        crntPosY = nextPosY
        crntPosZ = nextPosZ
        crntFreq = nextFreq
      }
//    } else if (!prevPosX.isNaN) {
//      crntPosX = prevPosX
//      crntPosY = prevPosY
//      crntPosZ = prevPosZ
//      crntFreq = prevFreq
//    } else {
//      crntPosX = Double.NaN
//      crntPosY = Double.NaN
//      crntPosZ = (Double.NaN, Double.NaN)
//      crntFreq = 0
//      active = false
    }
  }

  def compPos(map2real: (Double, Double) => Tuple2[Int, Int]): Unit = {}
  
  override def compPortPos(z: Double, map3DtoReal: (Double, Double, Double) => Tuple2[Int, Int]): Tuple2[Int, Int] = {
    map3DtoReal(crntPosX, crntPosY, z)
  }
  
  def draw(g: Graphics2D, map2real: (Double, Double, Double) => Tuple2[Int, Int], visibleMinFreq: Int, labelMinFreq: Int): Unit = {
    if (active && (crntFreq >= visibleMinFreq)) {
      val diameter = (6 * Math.sqrt(crntFreq)).toInt
//      val radius: Int = diameter / 2      
      g.setColor(markColor)
      val (px0, py0) = map2real(crntPosX, crntPosY, crntPosZ._1)
      val (px1, py1) = map2real(crntPosX, crntPosY, crntPosZ._2)
      g.setStroke(new BasicStroke(diameter, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND))
      g.drawLine(px0, py0, px1, py1)
      if (crntFreq >= labelMinFreq) drawLabel(g, node.label, (px0 + px1) / 2, py0, crntFreq)
    }
  }
  
}

object SpiralMark {
  def create(node: Node): SpiralMark = new SpiralMark(node)

}