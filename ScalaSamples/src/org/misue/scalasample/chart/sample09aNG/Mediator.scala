// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample09aNG

import java.awt.Dimension
import java.io.File
import javax.swing.SwingUtilities
import java.awt.Color

import scala.actors._
import scala.actors.Actor._
import java.awt.Dimension
import java.io.File
//import javax.swing.SwingUtilities

class Mediator(val chartPanel: ChartPanel, val area: Dimension) extends Actor {
    private val layoutMaker = new LayoutMaker(area)
    private var chart: Chart = null
    val defaultColor = Color.blue


    private def readData(file: File): Unit = {
//        println("2 On EDT:" + SwingUtilities.isEventDispatchThread())
        chart = DataReader.read(file, defaultColor)
        chartPanel.setChart(chart)
        layoutChart()
    }

    private def readData(filename: String): Unit = {
        chart = DataReader.read(filename, defaultColor)
        chartPanel.setChart(chart)
        layoutChart()
    }
    
    private def addItem(): Unit = {
        println("2 On EDT:" + SwingUtilities.isEventDispatchThread())
        val crntChart = if (chart == null) new Chart() else chart
        val label = String.valueOf(crntChart.itemNum)
        chart = crntChart.entry(new Item(label))
        chartPanel.setChart(chart)
        layoutChart()
    }
    
    private def setItemColor(color: Color): Unit = {
        println("2 On EDT:" + SwingUtilities.isEventDispatchThread())
        if (chart == null) {
            chart = new Chart(color)
            chartPanel.setChart(chart)
        } else {
            chart.setItemColor(color)
        }
        chartPanel.repaint()
    }

    private def layoutChart(): Unit = {
        layoutMaker.layout(chart)
        chartPanel.repaint()
    }

    override def act(): Unit = {
        loop {
            react {
                case "layout" => layoutChart()
                case "addItem" => addItem()
                case ("setItemColor", color: Color) => setItemColor(color)
                case ("read", file: File) => readData(file)
                case ("read", filename: String) => readData(filename)
            }
        }
    }

}

//class Mediator(chartPanel: ChartPanel, area: Dimension) {
//    private val layoutMaker = new LayoutMaker(area)
//    private var chart: Chart = null
//    val defaultColor = Color.blue
//
//
//    def readData(file: File): Unit = {
//        chart = DataReader.read(file, defaultColor)
//        layoutChart()
//        chartPanel.setChart(chart)
//    }
//
//    def readData(filename: String): Unit = {
//        chart = DataReader.read(filename, defaultColor)
//        layoutChart()
//        chartPanel.setChart(chart)
//    }
//
//    def layoutChart(): Unit = {
//        layoutMaker.layout(chart)
//    }
//    
//    def addItem(): Unit = {
//        val crntChart = if (chart == null) new Chart(defaultColor) else chart
//        val label = String.valueOf(crntChart.itemNum)
//        chart = crntChart.entry(new Item(label))
//        chartPanel.setChart(chart)
//        layoutChart()
//        chartPanel.repaint()
//    }
//    
//    def setItemColor(color: Color): Unit = {
//        if (chart == null) {
//            chart = new Chart(color)
//            chartPanel.setChart(chart)
//        } else {
//            chart.setItemColor(color)
//        }
//        chartPanel.repaint()
//    }
//}
