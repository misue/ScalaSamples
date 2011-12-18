// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample12b2

import java.awt.Color
import scala.actors._
import scala.actors.Actor._
import java.awt.Dimension
import java.io.File
//import javax.swing.SwingUtilities

class Mediator(private val chartPanel: ChartPanel, private val canvasSize: Dimension) extends Actor {
    private val layoutMaker = new LayoutMaker(canvasSize)
    private val defaultColor = Color.blue
    
    override def act(): Unit = {
        loop {
            react {
                case LayoutChart => layoutChart()
                case AddItem => addItem()
                case ReadFile(file) => readData(file)
                case ReadFile2(filename) => readData(filename)
            }
        }
    }
    
    private def chart: Chart = chartPanel.chart
    private def chart_=(chart: Chart): Unit = chartPanel.setChart(chart)
    
    private def layoutChart(): Unit = {
        layoutMaker.layout(chart)
        chartPanel.repaint()
    }

    private def readData(file: File): Unit = {
        chart = DataReader.read(file)
        layoutChart()
    }

    private def readData(filename: String): Unit = {
        chart = DataReader.read(filename)
        layoutChart()
    }
    
    private def addItem(): Unit = {
        val crntChart = if (chart == null) new Chart() else chart
        val label = String.valueOf(crntChart.itemNum)
        chart = crntChart.entry(new Item(label))
        layoutChart()
    }

}
