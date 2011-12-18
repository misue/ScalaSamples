// All Rights Reserved. Copyright (C) Kazuo Misue (2010-2011)
package org.misue.graphdraw.chart.clockchart

import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics2D
import java.util.Calendar
import org.misue.graphdraw.chart.util.DateUtil
import org.misue.graphdraw.chart.basic._
import java.awt.FontMetrics
import java.awt.Font
import org.misue.graphdraw.chart.Mediator

class ClockChart(graph: Graph, mediator: Mediator) extends Chart {
  
   // Constructor
  protected val markGroup: List[ClockMark] = graph.nodes.map(ClockMark.create(_))
  protected val linkGroup: List[ClockLink] = graph.edges.map(ClockLink.create(_))
  compPrimePos
  
  
  protected def periodCode: Int = mediator.periodCode
  
  private def date2ratio(date: Calendar): Double = date2ratio(date, periodCode)
  private def date2angle(date: Calendar): Double = date2angle(date, periodCode)
  private def date2pos(date: Calendar): Tuple3[Double,Double,Double] = date2pos(date, periodCode)
  
  override def compPrimePos: Unit = {
    linkGroup.foreach(_.compPrimePos(date2pos))
  }  
  
  override def update(crntDate: Calendar, tWinSizeCode: Int, showTail: Boolean, showHead: Boolean): Unit = {
    val firstDate = DateUtil.firstDate(crntDate, tWinSizeCode)
    markGroup.foreach(_.compNormalPos(firstDate, crntDate, showTail, showHead));
    if (showTail && showHead) linkGroup.foreach(_.checkVisibility(firstDate, crntDate))
  }
   
  override def update(ratio: Double, showTail: Boolean, showHead: Boolean): Unit = {
    markGroup.foreach(_.interpNormalPos(ratio, showTail, showHead))
  }

  override def draw(g: Graphics2D, area: Dimension, crntDate: Calendar, visibleMinFreq: Int, labelMinFreq: Int): Unit = {
    val centerX: Double = area.getWidth() / 2
    val centerY: Double = area.getHeight() / 2
    val scale: Double = (Math.min(area.getWidth(), area.getHeight()) - 100) / 2

    def map2real(x: Double, y: Double): Tuple2[Int, Int] = ((centerX + x * scale).toInt, (centerY + y * scale).toInt)
    def map2real2(p: Tuple2[Double, Double]): Tuple2[Int, Int] = map2real(p._1, p._2)
    def map3DtoReal(x: Double, y: Double, z: Double): Tuple2[Int, Int] = map2real2(map3Dto2D(x, y, z))
  
    
    def drawClockface(): Unit = {
      val diameter: Int = scale.toInt * 2
      val labelRadius: Int = scale.toInt + 15
      g.setFont(ClockChart.font)
      val fm: FontMetrics = g.getFontMetrics()
      val labelHeight: Int = fm.getAscent

      
      def drawScaleByAngle(angle: Double, radius: Double, offset: Double): Unit = {
        val px = Math.cos(angle)
        val py = Math.sin(angle)
        val posX0 = centerX + radius * px
        val posY0 = centerY + radius * py
        val posX1 = centerX + (radius + offset) * px
        val posY1 = centerY + (radius + offset) * py
        g.drawLine(posX0.toInt, posY0.toInt, posX1.toInt, posY1.toInt)
      }

      def drawFaceScale(unitTime: Int, steps: Int, shift: Double = 0.0, labelFunc: Int => String = _.toString): Unit = {
        val period = steps * unitTime
        for (val i: Int <- 0 until steps) {
          val step: Int = i * unitTime // five minutes
          g.setColor(Color.GRAY)
          drawScaleByAngle(Math.Pi * (2.0 * step / period - 0.5), scale, 5)

          val label: String = labelFunc(step)
          val labelWidth2: Double = fm.stringWidth(label) / 2d
          val angle: Double = Math.Pi * (2.0 * (step + shift) / period - 0.5)
          val cosAngle = Math.cos(angle)
          val sinAngle = Math.sin(angle)
          val posX: Int = (centerX + labelRadius * cosAngle - labelWidth2).toInt
          val posY: Int = (centerY + labelRadius * sinAngle + labelWidth2).toInt
          g.setColor(Color.WHITE)
          g.drawString(label, posX, posY)
        }
      }
     
      def drawFiveMinutesScale(steps: Int): Unit = drawFaceScale(5, steps)
      def drawOneHourScale(steps: Int): Unit = drawFaceScale(1, steps)
      
      def drawOneWeekFace: Unit = drawFaceScale(1, 7, 0.5, ((x) => (x + 1) match {
            case Calendar.SUNDAY => "SUN"
            case Calendar.MONDAY => "MON"
            case Calendar.TUESDAY => "TUE"
            case Calendar.WEDNESDAY => "WED"
            case Calendar.THURSDAY => "THU"
            case Calendar.FRIDAY => "FRI"
            case Calendar.SATURDAY => "SAT"
            case _ => "?"
          }))

      def drawOneDayScale(steps: Int): Unit = drawFaceScale(1, steps, 0.5, ((x) => (x + 1).toString))
      
      def drawCrntMark(date: Calendar): Unit = drawScaleByAngle(date2angle(date), scale, -5)      
      
      // Body of drawClockFace
      g.setColor(Color.gray);
      g.drawOval((centerX - scale).toInt, (centerY - scale).toInt, diameter, diameter)
      drawCrntMark(crntDate)

      periodCode match {
        case DateUtil.oneHour => drawFiveMinutesScale(12)
        case DateUtil.oneDay => drawOneHourScale(24)
        case DateUtil.oneWeek => drawOneWeekFace
        case DateUtil.oneMonth => drawOneDayScale(31)
      }

      g.setFont(ClockChart.dateFont)
      g.setColor(Color.GRAY)
      g.drawString(String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM", crntDate), 10, 40)

    } // draw
    
    drawClockface()
    if (mediator.isTailShown) markGroup.foreach(_.compPos(map2real));
    if (mediator.isHeadShown) markGroup.foreach(_.compPos(map2real));
    if (mediator.isBothShown) linkGroup.foreach(_.draw(g, map3DtoReal))
    if (mediator.isTailShown) markGroup.foreach(_.draw(g, labelMinFreq))
    if (mediator.isHeadShown) markGroup.foreach(_.draw(g, labelMinFreq))
  }
}


object ClockChart {
  private val font: Font = new Font("Helvetica", Font.BOLD, 14)
  private val dateFont: Font = new Font("Helvetica", Font.PLAIN, 32)

  def create(graph: Graph, mediator: Mediator) = new ClockChart(graph, mediator)  
}