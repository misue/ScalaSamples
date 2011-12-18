// All Rights Reserved. Copyright (C) Kazuo Misue (2010-2011)
package org.misue.graphdraw.chart.basic

import java.awt.Dimension
import java.awt.Graphics2D
import java.util.Calendar
import org.misue.graphdraw.chart.util.DateUtil

abstract class Chart {  
  
  protected def markGroup: List[AbstractMark]
  protected def linkGroup: List[AbstractLink]
  protected def periodCode: Int
  
  // Computes primary positions of marks. They may depend on the current time period.
  def compPrimePos: Unit
  
  // Computes normal positions of marks inside current time window.
  def update(crntDate: Calendar, pastWindowSize: Int, showTail: Boolean, showHead: Boolean): Unit
  
  // Interpolates normal positions of marks.
  def update(ratio: Double, showTail: Boolean, showHead: Boolean): Unit
  
  // Draws a chart.
  def draw(g: Graphics2D, area: Dimension, crntDate: Calendar, visibleMinFreq: Int, labelMinFreq: Int): Unit

  protected def date2ratio(date: Calendar, periodCode: Int): Double = {
    def ratioByHour: Double = (date.get(Calendar.MINUTE) + (date.get(Calendar.SECOND) / 60.0)) / 60.0
    def ratioByDay: Double = (date.get(Calendar.HOUR_OF_DAY) + ratioByHour) / 24.0    
    def ratioByWeek: Double = (date.get(Calendar.DAY_OF_WEEK) - 1 + ratioByDay) / 7.0    
    def ratioByMonth: Double = (date.get(Calendar.DAY_OF_MONTH) - 1 + ratioByDay) / 31.0
        
    periodCode match {
      case DateUtil.oneHour => ratioByHour
      case DateUtil.oneDay => ratioByDay
      case DateUtil.oneWeek => ratioByWeek
      case DateUtil.oneMonth => ratioByMonth
    }
  }
  
  protected def date2angle(date: Calendar, periodCode: Int): Double = ratio2angle(date2ratio(date, periodCode))
  protected def ratio2angle(ratio: Double): Double = Math.Pi * (2.0 * ratio - 0.5)
  protected def angle2pos(angle: Double): Tuple3[Double,Double,Double] = (Math.cos(angle), Math.sin(angle), angle / (2.0 * Math.Pi) + 0.25)
  protected def date2pos(date: Calendar, periodCode: Int): Tuple3[Double,Double,Double] = ratio2pos(date2ratio(date, periodCode))
  protected def ratio2pos(ratio: Double): Tuple3[Double, Double, Double] = {
    val angle = ratio2angle(ratio)
    (Math.cos(angle), Math.sin(angle), ratio)    
  }
  protected def map3Dto2D(x: Double, y: Double, z: Double): Tuple2[Double, Double] = (x, y)
  protected def map3Dto2D(p: Tuple3[Double, Double, Double]): Tuple2[Double, Double] = map3Dto2D(p._1, p._2, p._3)
  
}