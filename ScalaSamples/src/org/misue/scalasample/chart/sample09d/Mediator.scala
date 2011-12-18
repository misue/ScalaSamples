// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample09d

import scala.actors._
import scala.actors.Actor._
import java.awt.Dimension
import java.awt.Color
import java.io.File
import javax.swing.SwingUtilities
//import javax.swing.SwingUtilities

class Mediator(private val chartPanel: ChartPanel, private val area: Dimension) extends Actor {
    private val layoutMaker = new LayoutMaker(area)
    private val defaultColor = Color.blue
    private var chart: Chart = null
    private var crntColor = defaultColor

    override def act(): Unit = {
        loop {
            react {
//                case LayoutChart => layoutChart()
                case AddItem => addItem()
                case SetItemColor(color) => setItemColor(color)
                case ReadFile(file) => readData(file)
                case ReadFile2(filename) => readData(filename)
                case SendDefaultColor(actor) => actor ! DefaultColor(defaultColor)
                case SetItemLabel(label) => setItemLabel(label)
            }
        }
    }

    private def readData(file: File): Unit = {
//        println("2 On EDT:" + SwingUtilities.isEventDispatchThread())
        chart = DataReader.read(file, crntColor)
        chartPanel.setChart(chart)
        layoutChart()
    }

    private def readData(filename: String): Unit = {
        chart = DataReader.read(filename, crntColor)
        chartPanel.setChart(chart)
        layoutChart()
    }

    private def addItem(): Unit = {
//        println("2 On EDT:" + SwingUtilities.isEventDispatchThread())
        val crntChart = if (chart == null) new Chart(crntColor) else chart
        val label = String.valueOf(crntChart.itemNum)
        chart = crntChart.entry(new Item(label))
        chartPanel.setChart(chart)
        layoutChart()
    }

    private def setItemColor(color: Color): Unit = {
//        println("2 On EDT:" + SwingUtilities.isEventDispatchThread())
        crntColor = color
        if (chart == null) {
            chart = new Chart(color)
            chartPanel.setChart(chart)
        } else {
            chart.setItemColor(color)
        }
        chartPanel.repaint()
    }
    
    private def setItemLabel(label: String): Unit = {
        if (chart != null) chart.setItemLabel(label)
        chartPanel.repaint()
    }

    private def layoutChart(): Unit = {
        layoutMaker.layout(chart)
        chartPanel.repaint()
    }

}
