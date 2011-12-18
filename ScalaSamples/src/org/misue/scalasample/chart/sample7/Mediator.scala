// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample7

import java.awt.Dimension
import java.io.File

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

}
