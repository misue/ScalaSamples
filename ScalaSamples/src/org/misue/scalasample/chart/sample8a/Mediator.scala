// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample8a

import java.awt.Dimension
import java.io.File
import javax.swing.SwingUtilities

import scala.actors._
import scala.actors.Actor._
import java.awt.Dimension
import java.io.File
//import javax.swing.SwingUtilities

class Mediator(val chartPanel: ChartPanel, val area: Dimension) extends Actor {
    private val layoutMaker = new LayoutMaker(area)
    private var chart: Chart = null

    private def readData(file: File): Unit = {
//        println("2 On EDT:" + SwingUtilities.isEventDispatchThread())
        chart = DataReader.read(file)
        chartPanel.setChart(chart)
        layoutChart()
    }

    private def readData(filename: String): Unit = {
        chart = DataReader.read(filename)
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

    private def layoutChart(): Unit = {
        layoutMaker.layout(chart)
        chartPanel.repaint()
    }

    override def act(): Unit = {
        loop {
            react {
//                case Layout => layoutChart()
                case AddItem => addItem()
                case ReadFile(file) => readData(file)
                case ReadFile2(filename) => readData(filename)
            }
        }
    }

}

