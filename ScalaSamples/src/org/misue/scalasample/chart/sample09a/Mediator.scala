// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample09a

import java.awt.Dimension
import java.io.File
import javax.swing.SwingUtilities
import java.awt.Color

import scala.actors._
import scala.actors.Actor._
import java.awt.Dimension
import java.io.File
//import javax.swing.SwingUtilities

//case object LayoutChart
//case object AddItem
//case class SetItemColor(color: Color)
//case class ReadFile(file: File)
//case class ReadData(filename: String)
//case object SendDefaultColor

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
                case SendDefaultColor => reply(DefaultColor(defaultColor))
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
        println("2 On EDT:" + SwingUtilities.isEventDispatchThread())
        val crntChart = if (chart == null) new Chart(crntColor) else chart
        val label = String.valueOf(crntChart.itemNum)
        chart = crntChart.entry(new Item(label))
        chartPanel.setChart(chart)
        layoutChart()
    }

    private def setItemColor(color: Color): Unit = {
        println("2 On EDT:" + SwingUtilities.isEventDispatchThread())
        crntColor = color
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

}
