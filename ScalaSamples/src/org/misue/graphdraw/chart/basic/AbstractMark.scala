// All Rights Reserved. Copyright (C) Kazuo Misue (2010-2011)
package org.misue.graphdraw.chart.basic

import java.util.Calendar
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Font
import java.awt.FontMetrics

abstract class AbstractMark {

  protected val labelColor: Color = Color.white
  protected var markColor: Color = null
  protected var active: Boolean = false
  protected var visible: Boolean = false

  
  def compPortPos(z: Double, map3DtoReal: (Double, Double, Double) => Tuple2[Int, Int]): Tuple2[Int, Int]

  protected def interpolate(x0: Double, x1: Double, ratio: Double): Double = ratio * x1 + (1.0 - ratio) * x0
  protected def interpolate(p0: Tuple2[Double, Double], p1: Tuple2[Double, Double], ratio: Double): Tuple2[Double, Double] = 
    (interpolate(p0._1, p1._1, ratio), interpolate(p0._2, p1._2, ratio))
//  protected def interpolate(p0: Tuple3[Double, Double, Double], p1: Tuple3[Double, Double, Double], ratio: Double): Tuple3[Double, Double, Double] = 
//    (interpolate(p0._1, p1._1, ratio), interpolate(p0._2, p1._2, ratio), interpolate(p0._3, p1._3, ratio))

  protected def mixedColor(prop: Float): Color = {
    val hue: Float = (0.90 - prop / 3.5).toFloat
    val rgb: Int = Color.HSBtoRGB(hue, 1.0f, 1.0f)
    val r: Int = (rgb & 0xFF0000) >> 16
    val g: Int = (rgb & 0x00FF00) >> 8
    val b: Int = (rgb & 0x0000FF)
    val alpha: Int = 64//128
    //    val rgba: Int = (alpha << 24) | rgb
    //    val c = new Color(rgba, true)
    new Color(r, g, b, alpha)
  }
  
  protected def mixedColor(weight1: Int, weight2: Int): Color = mixedColor(weight1.toFloat / (weight1 + weight2))
  
  protected def drawLabel(g: Graphics2D, label: String, x: Int, y: Int, freq: Double): Unit = {
    val font: Font = new Font("メイリオ", Font.PLAIN, (7 + Math.sqrt(freq)).toInt)
    g.setFont(font)
    val fm: FontMetrics = g.getFontMetrics
    val offsetX = fm.stringWidth(label) / 2
    val offsetY = fm.getAscent / 2
    g.setColor(labelColor)
    g.drawString(label, (x - offsetX).toInt, (y + offsetY).toInt)
  }  
}
