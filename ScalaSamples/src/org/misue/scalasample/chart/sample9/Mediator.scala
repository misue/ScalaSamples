// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample9

import java.awt.Dimension
import java.io.File
import javax.swing.SwingUtilities
import java.awt.Color

class Mediator(chartPanel: ChartPanel, area: Dimension) {
    private val layoutMaker = new LayoutMaker(area)
    private var chart: Chart = null
    val defaultColor = Color.blue


    def readData(file: File): Unit = {
        chart = DataReader.read(file, defaultColor)
        layoutChart()
        chartPanel.setChart(chart)
    }

    def readData(filename: String): Unit = {
        chart = DataReader.read(filename, defaultColor)
        layoutChart()
        chartPanel.setChart(chart)
    }

    def layoutChart(): Unit = {
        layoutMaker.layout(chart)
    }
    
    def addItem(): Unit = {
        val crntChart = if (chart == null) new Chart(defaultColor) else chart
        val label = String.valueOf(crntChart.itemNum)
        chart = crntChart.entry(new Item(label))
        chartPanel.setChart(chart)
        layoutChart()
        chartPanel.repaint()
    }
    
    def setItemColor(color: Color): Unit = {
        if (chart == null) {
            chart = new Chart(color)
            chartPanel.setChart(chart)
        } else {
            chart.setItemColor(color)
        }
        chartPanel.repaint()
    }
}
