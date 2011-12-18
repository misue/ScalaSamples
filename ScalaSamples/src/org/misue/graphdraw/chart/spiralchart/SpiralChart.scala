// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.graphdraw.chart.spiralchart

import java.util.Calendar
import java.awt.Graphics2D
import java.awt.Dimension
import java.awt.Font
import org.misue.graphdraw.chart.util.DateUtil
import org.misue.graphdraw.chart.basic._
import org.misue.graphdraw.chart.Mediator
import java.awt.FontMetrics
import java.awt.Color

class SpiralChart(graph: Graph, mediator: Mediator) extends Chart {
  
  var windowBeginDate: Calendar = null
  
  // Constructor
  protected val markGroup: List[SpiralMark] = graph.nodes.map(SpiralMark.create(_))
  protected val linkGroup: List[SpiralLink] = graph.edges.map(SpiralLink.create(_))
  compPrimePos

  protected def periodCode: Int = mediator.periodCode

  private def date2ratio(date: Calendar): Double = date2ratio(date, periodCode)
  private def date2angle(date: Calendar): Double = date2angle(date, periodCode)
  private def date2pos(date: Calendar): Tuple3[Double, Double, Double] = date2pos(date, periodCode)
  
  override protected def date2ratio(date: Calendar, periodCode: Int): Double = {
    def ratioByHour: Double = {
      val day0inMillis: Long = graph.firstDay.getTimeInMillis
      ((date.getTimeInMillis - day0inMillis) / 1000) / (60.0 * 60)
    }

    def ratioByDay: Double = {
      val day0inMillis: Long = graph.firstDay.getTimeInMillis
      ((date.getTimeInMillis - day0inMillis) / 1000) / (60.0 * 60 * 24)
    }
    
    // 未完成
    def ratioByWeek: Double = {
      (date.get(Calendar.DAY_OF_WEEK) - 1 + ratioByDay) / 7.0
    }
    
    // 未完成
    def ratioByMonth: Double = {
      val date0: Calendar = graph.firstDate
      val diffMonth: Int = {
        val y: Int = date.get(Calendar.YEAR) - date0.get(Calendar.YEAR)
        val m: Int = date.get(Calendar.MONTH) - date0.get(Calendar.MONTH)
        y * 12 + m
      }
      diffMonth + (date.get(Calendar.DAY_OF_MONTH) - 1 + ratioByDay) / 31.0
    }
        
    periodCode match {
      case DateUtil.oneHour => ratioByHour
      case DateUtil.oneDay => ratioByDay
      case DateUtil.oneWeek => ratioByWeek
      case DateUtil.oneMonth => ratioByMonth
    }
  }

  override def compPrimePos: Unit = {
    linkGroup.foreach(_.compPrimePos(date2pos))
  }

  def alpha: Double = SpiralChart.alpha
  def beta: Double = SpiralChart.beta * 4.0 * alpha / (3.0)
  
  override def map3Dto2D(x0: Double, y0: Double, z0: Double): Tuple2[Double, Double] = (x0 * alpha + z0 * beta, y0)  

  override def update(crntDate: Calendar, tWinSizeCode: Int, showTail: Boolean, showHead: Boolean): Unit = {
    windowBeginDate = DateUtil.firstDate(crntDate, tWinSizeCode)
    markGroup.foreach(_.compNormalPos(windowBeginDate, crntDate, showTail, showHead, map3Dto2D));
    if (showTail && showHead) linkGroup.foreach(_.checkVisibility(windowBeginDate, crntDate))
  }

  override def update(ratio: Double, showTail: Boolean, showHead: Boolean): Unit = {
    markGroup.foreach(_.interpNormalPos(ratio, showTail, showHead))
  }

  override def draw(g: Graphics2D, area: Dimension, crntDate: Calendar, visibleMinFreq: Int, labelMinFreq: Int): Unit = {
    val centerX: Double = area.getWidth() / 2
    val centerY: Double = area.getHeight() / 2
    val scale: Double = (Math.min(area.getWidth(), area.getHeight()) - 100) / 2

    val windowBeginRatio: Double = if (windowBeginDate == null) 0.0 else Math.max(date2ratio(windowBeginDate), 0.0)
    val windowEndRatio: Double = if (crntDate == null) 0.0 else Math.max(date2ratio(crntDate), 0.0)

//    def map2real(x: Double, y: Double): Tuple2[Double, Double] = (centerX + x * scale, centerY + y * scale)
    def map2real(x: Double, y: Double): Tuple2[Int, Int] = ((scale * (1.1 * alpha + x - windowBeginRatio * beta)).toInt, (centerY + y * scale).toInt)
    def map2real2(p: Tuple2[Double, Double]): Tuple2[Int, Int] = map2real(p._1, p._2)
    
    def map3DtoReal(x: Double, y: Double, z: Double): Tuple2[Int, Int] = map2real2(map3Dto2D(x, y, z))

    def drawClockface(): Unit = {
      val diameter: Int = scale.toInt * 2
      val labelRadius: Int = scale.toInt + 15
      g.setFont(SpiralChart.font)
      val fm: FontMetrics = g.getFontMetrics()
      val labelHeight2: Int = (fm.getAscent + fm.getDescent) / 2
  
      def drawScaleByAngle(angle: Double, radius: Double, offset: Double): Unit = {
        val (px0, py0, z) = angle2pos(angle)
        val px1 = px0 * (1 + offset)
        val py1 = py0 * (1 + offset)
        val (posX0, posY0) = map2real2(map3Dto2D(px0, py0, z))
        val (posX1, posY1) = map2real2(map3Dto2D(px1, py1, z))        
        g.drawLine(posX0.toInt, posY0.toInt, posX1.toInt, posY1.toInt)
      }
      
      def drawScaleByRatio(ratio: Double, radius: Double, offset: Double): Unit = drawScaleByAngle(ratio2angle(ratio), radius, offset)
      
      def drawFaceScale(division: Int, shift: Double = 0.0, labelFunc: Int => String = _.toString): Unit = {
        val stepBegin: Int = (windowBeginRatio * division).toInt
        val stepEnd: Int = (windowEndRatio * division).toInt
        for (val step: Int <- stepBegin + 1 to stepEnd) {
          val ratio: Double = step.toDouble / division
          g.setColor(Color.GRAY)
          drawScaleByRatio(ratio, scale, 0.03)

          val label: String = labelFunc(step)
          val labelWidth2: Double = fm.stringWidth(label) / 2d
          val angle: Double = ratio2angle(ratio + shift / division)          
          val (px0, py0, z) = angle2pos(angle)
          val px1 = px0 * (1 + 0.1)
          val py1 = py0 * (1 + 0.1)
          val (posX1, posY1) = map2real2(map3Dto2D(px1, py1, z))        
          g.setColor(Color.WHITE)
          g.drawString(label, (posX1 - labelWidth2).toInt, posY1 + labelHeight2)
        }
      }      
     
      def drawFiveMinutesScale(steps: Int): Unit = drawFaceScale(steps, 0.1, ((x) => (x % steps * 5).toString))
      def drawOneHourFace(steps: Int): Unit = drawFaceScale(steps, 0.1, ((x) => (x % steps).toString))
      
      def drawOneWeekFace: Unit = drawFaceScale(7, 0.5, ((x) => (x % 7 + 1) match {
            case Calendar.SUNDAY => "SUN"
            case Calendar.MONDAY => "MON"
            case Calendar.TUESDAY => "TUE"
            case Calendar.WEDNESDAY => "WED"
            case Calendar.THURSDAY => "THU"
            case Calendar.FRIDAY => "FRI"
            case Calendar.SATURDAY => "SAT"
            case _ => "?"
          }))

      def drawOneMonthFace: Unit = drawFaceScale(31, 0.5, ((x) => (x % 31 + 1).toString))
      
      def drawCrntMark(date: Calendar): Unit = drawScaleByAngle(date2angle(date), scale, -0.03)   

      def ratio2realPos(ratio: Double): Tuple2[Int, Int] = map2real2(map3Dto2D(ratio2pos(ratio)))
      
      val divider: Int = 96
      
//      println("windowBeginRatio = " + windowBeginRatio + " windowEndRatio = " + windowEndRatio)
      val windowBeginStep: Int = (windowBeginRatio * divider).toInt
      val windowEndStep: Int = (windowEndRatio * divider).toInt

      g.setColor(Color.gray);
      if (windowBeginRatio < windowEndRatio) {
        var (posX0, posY0) = ratio2realPos(windowBeginRatio)
        for (val step: Int <- windowBeginStep + 1 to windowEndStep) {
          val (posX, posY) = ratio2realPos(step.toDouble / divider)
          g.drawLine(posX0, posY0, posX, posY)
          posX0 = posX
          posY0 = posY
        }
        val (posX, posY) = ratio2realPos(windowEndRatio)
        g.drawLine(posX0, posY0, posX, posY)
      } else {
        var (posX0, posY0) = ratio2realPos(0.0)
        for (val step: Int <- 1 to 2 * divider) {
          val (posX, posY) = ratio2realPos(step.toDouble / divider)
          g.drawLine(posX0, posY0, posX, posY)
          posX0 = posX
          posY0 = posY
        }
      }

      drawCrntMark(crntDate)

      periodCode match {
        case DateUtil.oneHour => drawFiveMinutesScale(12)
        case DateUtil.oneDay => drawOneHourFace(24)
        case DateUtil.oneWeek => drawOneWeekFace
        case DateUtil.oneMonth => drawOneMonthFace
      }

      g.setFont(SpiralChart.dateFont)
      g.setColor(Color.GRAY)
      g.drawString(String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM", crntDate), 10, 40)

    } // draw
    
    drawClockface()
//    if (mediator.isTailShown) markGroup.foreach(_.compPos(map2real));
//    if (mediator.isHeadShown) markGroup.foreach(_.compPos(map2real));
    if (mediator.isBothShown) linkGroup.foreach(_.draw(g, map3DtoReal))
    if (mediator.isTailShown) markGroup.foreach(_.draw(g, map3DtoReal, visibleMinFreq, labelMinFreq))
    if (mediator.isHeadShown) markGroup.foreach(_.draw(g, map3DtoReal, visibleMinFreq, labelMinFreq))
  }  
  
}


object SpiralChart {
  private val font: Font = new Font("Helvetica", Font.BOLD, 14)
  private val dateFont: Font = new Font("Helvetica", Font.PLAIN, 32)
  
  var alpha: Double = 1.0
  var beta: Double = 0.0

//  def create(markGroup: List[SpiralMark], edges: List[Edge], mediator: Mediator) = new SpiralChart(markGroup, edges, mediator)  
  def create(graph: Graph, mediator: Mediator) = new SpiralChart(graph, mediator)  
  
  import scala.swing._
  import scala.swing.event._

  def createControl(mediator: Mediator): swing.Component = {
    new BoxPanel(Orientation.Vertical) { 
      contents += new Label("Alpha Value (x0.01):")
      contents += new Slider {
        min = 0
        max = 100
        value = (alpha * 100).toInt
        majorTickSpacing = 10
        minorTickSpacing = 5
        paintTicks = true
        paintLabels = true
        snapToTicks = false
        listenTo(this)
        reactions += {
          case ValueChanged(slider) => {
            alpha = slider.asInstanceOf[Slider].value / 100.0
          }
        }
      }
      
      contents += new Label("Beta Value (x0.01):")
      contents += new Slider {
        min = 0
        max = 500
        value = (beta * 100).toInt
        majorTickSpacing = 50
        minorTickSpacing = 25
        paintTicks = true
        paintLabels = true
        snapToTicks = false
        listenTo(this)
        reactions += {
          case ValueChanged(slider) => {
            beta = slider.asInstanceOf[Slider].value / 100.0
          }
        }
      }
      
    }    
  }

}