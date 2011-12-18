// All Rights Reserved. Copyright (C) Kazuo Misue (2010-2011)
package org.misue.graphdraw.chart

import swing._
import swing.event._
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.util.Calendar
import javax.imageio.ImageIO
import javax.swing.JFileChooser
import org.misue.graphdraw.chart.basic._
//import org.misue.graphdraw.chart.clockchart.ClockChartMaker
//import org.misue.graphdraw.chart.spiralchart.SpiralChartMaker
import org.misue.graphdraw.chart.spiralchart2.SpiralChartMaker
import org.misue.graphdraw.chart.util.DateUtil

class Mediator {

  val panel: ChartPanel = new ChartPanel(this)
//  val chartMaker: ChartMaker = new ClockChartMaker
  val chartMaker: ChartMaker = new SpiralChartMaker
  val layoutActor = new LayoutActor(this)
  var graph: Graph = null
  var chart: Chart = null
  private var _timeStepCode: Int = 3
  private var _tWinSizeCode: Int = 4
  private var _visibleMinFreq: Int = 0
  private var _labelMinFreq: Int = 2
  private var _intervals: Int = 10
  private var direction: Int = 0
  private var timeSlider: Slider = null
  
  var _showNode: Int = Mediator.showBoth
  var _periodCode: Int = DateUtil.oneDay
  
  def readData(file: File): Graph = DataReaderBG.readData(file)
   
  def incDate(date: Calendar, timeStepCode: Int, direction: Int): Calendar = 
    DateUtil.incDate(date, timeStepCode, direction)
  
  val timeScale: Int = 1000 * 60 * 30

  def setTimeSliderVal(graph: Graph): Unit = {
    if ((graph.firstDay != null) && (graph.finalDay != null)) {
      val firstDay: Calendar = graph.firstDay
      val finalDay: Calendar = graph.finalDay
      val firstDate: Int = (firstDay.getTimeInMillis / timeScale).toInt
      val finalDate: Int = (finalDay.getTimeInMillis / timeScale).toInt
      timeSlider.min = firstDate
      timeSlider.max = finalDate
      timeSlider.value = firstDate
      timeSlider.majorTickSpacing = 240
      timeSlider.minorTickSpacing = 24
      timeSlider.paintTicks = true
      timeSlider.paintLabels = true

      val (firstDayLabel, finalDayLabel) = if (firstDay.get(Calendar.YEAR) == finalDay.get(Calendar.YEAR)) {
        if (firstDay.get(Calendar.MONTH) == finalDay.get(Calendar.MONTH)) {
          (String.format("%1$td", firstDay), String.format("%1$td", finalDay))
        } else {
          (String.format("%1$tm-%1$td", firstDay), String.format("%1$tm-%1$td", finalDay))
        }
      } else {
        (String.format("%1$tY-%1$tm-%1$td", firstDay), String.format("%1$tY-%1$tm-%1$td", finalDay))
      }
      timeSlider.labels = Map(firstDate -> new Label(firstDayLabel), finalDate -> new Label(finalDayLabel))
    }
  }
  
  def readFile(filename: String): Unit = {
    graph = readData(new File(filename))
    setTimeSliderVal(graph)
    chart = chartMaker.createChart(graph, this)
//    chart.compPrimePos
  }
  
  def openFile(): Unit = {
//    import javax.swing.JFileChooser
    val filechooser: JFileChooser  = new JFileChooser()
    val selected: Int = filechooser.showOpenDialog(null /* JFrame is necessary */);
    if (selected == JFileChooser.APPROVE_OPTION) {
      val file: File = filechooser.getSelectedFile()
      graph = readData(file)
      setTimeSliderVal(graph)
      chart = chartMaker.createChart(graph, this)
//      chart.compPrimePos
    }
  }
  
//  def saveImage(): Unit = {    
//    val fileChooser = new JFileChooser
//    val option: Int = fileChooser.showSaveDialog(null /*frame*/)
//    if (option == JFileChooser.APPROVE_OPTION) {
//      val file: File = fileChooser.getSelectedFile
//      writePNGImage(file)
//    }
//  }
//  
  

  def showInfo(): Unit = {
  }

  def entryTimeSlider(slider: Slider): Unit = { timeSlider = slider }
  
  
  def timeStepCode: Int = _timeStepCode
  def timeStepCode_=(x: Int): Unit = {
    _timeStepCode = x
  }
  
  def tWinSizeCode: Int = _tWinSizeCode
  def tWinSizeCode_=(x: Int): Unit = {
    _tWinSizeCode = x
  }
  
  def visibleMinFreq: Int = _visibleMinFreq
  def visibleMinFreq_=(x: Int): Unit = {
    _visibleMinFreq = x
  }
  
  def labelMinFreq: Int = _labelMinFreq
  def labelMinFreq_=(x: Int): Unit = {
    _labelMinFreq = x
  }


  var startDate: Calendar = Calendar.getInstance
  var _crntDate: Calendar = startDate.clone.asInstanceOf[Calendar]
  def crntDate: Calendar = _crntDate
  def crntDate_=(x: Calendar): Unit = {
    _crntDate = x
    timeSlider.value = (x.getTimeInMillis / timeScale).toInt
  }
  
//  Used from timeSlider
  def dateval: Int = (crntDate.getTimeInMillis / timeScale).toInt
  def dateval_=(x: Int): Unit = {
    _crntDate.setTimeInMillis(x.asInstanceOf[Long] * timeScale)
  }
  
  
//  def showNode: Int = _showNode
  def isTailShown: Boolean = (_showNode & Mediator.showTail) != 0
  def isHeadShown: Boolean = (_showNode & Mediator.showHead) != 0
  def isBothShown: Boolean = isTailShown && isHeadShown
  def setShowOnlyTail: Unit = { _showNode = Mediator.showTail }
  def setShowOnlyHead: Unit = { _showNode = Mediator.showHead }
  def setShowBothNode: Unit = { _showNode = Mediator.showTail | Mediator.showHead }
  
  def periodCode: Int = _periodCode
  def periodCode_=(x: Int): Unit = {
    if (_periodCode != x) {
      _periodCode = x
      println("Period has been changed to " + x)
      chart.compPrimePos
    }
  }
  
  def createChartMakerControl(): Component = chartMaker.createControl(this)
  
  def makeChart(): Unit = chart = chartMaker.createChart(graph, this)
  
  def start(): Unit = {
    layoutActor.start()
//    layoutActor ! 100
  }
  
  // This method is called only form LayoutActor
  def initLayout(): Unit = {
    direction = 0
    crntDate = startDate.clone.asInstanceOf[Calendar]
//    println("initLayout(): crntDate: ", String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM", crntDate))
//    layoutActor ! "Init"
  }
  
  def playPause(): Unit = {
    direction = 0
  }
  
  def playForward(): Unit = {
    direction = 1
  }
  
  def playBackward(): Unit = {
    direction = -1
  }
  
  def intervals: Int = _intervals
  def intervals_=(x: Int): Unit = _intervals = x
  
  var interval: Int = 0
  
  // panel.repaint() calls paintComponent(g) and then paintComponent(g) calls this method.
  def drawChart(g: Graphics2D, area: Dimension): Unit = {
    if (chart != null) {
//      println("Mediator.drawChart() is invoked.")
//      println("drawChart().crntDate: ", String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM", crntDate))
//      if (interval == 0) chart.draw(g, panel.size, crntDate, tWinSizeCode)
//      else chart.draw(g, panel.size, crntDate, intervals, interval)
      chart.draw(g, area, crntDate, visibleMinFreq, labelMinFreq)
    }
  }

  //  def writePNGImage(file: File): Unit = {
  //    val size0: Dimension = chart.properSize
  //    val size1: Dimension = new Dimension(size0.width + 20, size0.height + 20)
  //    val bufferedImage =	new BufferedImage(size1.width, size1.height, BufferedImage.TYPE_INT_RGB)
  //    val g: Graphics2D = bufferedImage.createGraphics
  //    g.setColor(Color.BLACK)
  //    g.fillRect(0, 0, size1.width, size1.height)
  //    g.setFont(g.getFont.deriveFont(9f))
  //    chart.draw(g, size1, incDate)
  //    g.dispose()
  //    ImageIO.write(bufferedImage, "png", file)    
  //  }
  //

  def updateLayout(): Unit = {
    if (interval >= intervals) {
      interval = 0
      crntDate = incDate(crntDate, timeStepCode, direction)
      chart.update(crntDate, tWinSizeCode, isTailShown, isHeadShown)
    }
    chart.update(interval.toDouble / intervals, isTailShown, isHeadShown)
    interval += 1
  }
  
  def relax(): Unit = {
    if (chart != null) {
//      if (interval == intervals) {
//    	interval = 0
//    	crntDate = incDate(crntDate, timeStepCode, direction)
//      }        
//      interval += 1
      updateLayout()
      panel.repaint()
    }
  }

}

object Mediator {
  val showTail: Int = 1
  val showHead: Int = 2
  val showBoth: Int = showTail | showHead
}
