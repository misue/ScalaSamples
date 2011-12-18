// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample13d

import java.awt.Color
import scala.actors._
import scala.actors.Actor._
import java.awt.Dimension
import java.io.File
//import javax.swing.SwingUtilities

class Mediator(private val chartPanel: ChartPanel) extends Actor {
    
    private var chart: Chart = null
    private var layoutMaker: LayoutMaker = null//new LayoutMaker(canvasSize)
    private val defaultColor = Color.blue
    
    override def act(): Unit = {

        val sleepTime: Int = 1000

        def notifyLater() {
            val mainActor = self
            actor {
                Thread.sleep(sleepTime)
                mainActor ! Wakeup
            }
        }

        notifyLater()
        loop {
            react {
                case Wakeup => {
                    updateLayout()
                    notifyLater()
                }
//                case LayoutChart => layoutChart()
                case AddItem => addItem()
                case ReadFile(file) => readFile(file)
                case ReadFile2(filename) => readFile(filename)
            }
        }
    }
        
    private def updateLayout(): Unit = {
        if (layoutMaker != null) {
            layoutMaker.update(chartPanel.size)
//            chartPanel.repaint()
        }
    }
        
//    private def layoutChart(): Unit = {
//        layoutMaker.layout(chart)
//        chartPanel.repaint()
//    }
    
    private def setChart(chart: Chart): Unit = {
        chartPanel.chart = chart
        layoutMaker = new LayoutMaker(chart)
        layoutMaker.initLayout(chartPanel.size)
//        layoutChart()
    }

    private def readFile(file: File): Unit = setChart(DataReader.read(file))
    private def readFile(filename: String): Unit = setChart(DataReader.read(filename))
    
    private def addItem(): Unit = {
//        val crntChart = if (chart == null) new Chart() else chart
//        val label = String.valueOf(crntChart.itemNum)
//        chart = crntChart.entry(new Item(label))
//        layoutChart()
    }

}
