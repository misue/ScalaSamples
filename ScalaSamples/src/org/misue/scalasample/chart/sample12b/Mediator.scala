// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample12b

import java.awt.Dimension
import java.io.File
import javax.swing.SwingUtilities
import java.awt.Color

import scala.actors._
import scala.actors.Actor._
import java.awt.Dimension
import java.io.File
//import javax.swing.SwingUtilities

class Mediator(val chartPanel: ChartPanel, val canvasSize: Dimension) extends Actor {
    private val layoutMaker = new LayoutMaker(canvasSize)
    val defaultColor = Color.blue
    
    override def act(): Unit = {
        loop {
            react {
                case "layout" => layoutChart()
                case "addItem" => addItem()
//                case ("setItemColor", color: Color) => setItemColor(color)
//                case ("changeSize", size: Int) => changeSize(size)
                case ("read", file: File) => readData(file)
                case ("read", filename: String) => readData(filename)
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
//        println("2 On EDT:" + SwingUtilities.isEventDispatchThread())
        chart = DataReader.read(file)
//        chartPanel.setChart(chart)
        layoutChart()
    }

    private def readData(filename: String): Unit = {
        chart = DataReader.read(filename)
//        chartPanel.setChart(chart)
        layoutChart()
    }
    
    private def addItem(): Unit = {
        println("2 On EDT:" + SwingUtilities.isEventDispatchThread())
        val crntChart = if (chart == null) new Chart() else chart
        val label = String.valueOf(crntChart.itemNum)
        chart = crntChart.entry(new Item(label))
//        chartPanel.setChart(chart)
        layoutChart()
    }
    
//    private def setItemColor(color: Color): Unit = {
//        println("2 On EDT:" + SwingUtilities.isEventDispatchThread())
//        if (chart == null) {
//            chart = new Chart(color)
//            chartPanel.setChart(chart)
//        } else {
//            chart.setItemColor(color)
//        }
//        chartPanel.repaint()
//    }
    
//    private def changeSize(s: Int): Unit = {
//        println("2 On EDT:" + SwingUtilities.isEventDispatchThread())
//		if (chart != null) {
//			chart.itemSize = s
//			chartPanel.repaint()
//		}
//	}

}
