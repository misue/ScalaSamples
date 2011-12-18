// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample6a

import scala.actors._
import scala.actors.Actor._
import java.awt.Dimension
import java.io.File
//import javax.swing.SwingUtilities

class Mediator(private val chartPanel: ChartPanel, private val area: Dimension) extends Actor {
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

    private def layoutChart(): Unit = {
        layoutMaker.layout(chart)
        chartPanel.repaint()
    }

    override def act(): Unit = {
        loop {
            react {
//                case Layout => layoutChart()
                case ReadFile(file) => readData(file)
                case ReadFile2(filename) => readData(filename)
            }
        }
    }

}


