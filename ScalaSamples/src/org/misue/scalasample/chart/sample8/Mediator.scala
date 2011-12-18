// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample8

import java.awt.Dimension
import java.io.File
import javax.swing.SwingUtilities

class Mediator(chartPanel: ChartPanel, area: Dimension) {
    private val layoutMaker = new LayoutMaker(area)
    private var chart: Chart = null

    def readData(file: File): Unit = {
        chart = DataReader.read(file)
        layoutChart()
        chartPanel.setChart(chart)
    }

    def readData(filename: String): Unit = {
        chart = DataReader.read(filename)
        layoutChart()
        chartPanel.setChart(chart)
    }

    def layoutChart(): Unit = {
        layoutMaker.layout(chart)
    }
    
    def addItem(): Unit = {
        val crntChart = if (chart == null) new Chart() else chart
        val label = String.valueOf(crntChart.itemNum)
        chart = crntChart.entry(new Item(label))
        chartPanel.setChart(chart)
        layoutChart()
        chartPanel.repaint()
    }
}
