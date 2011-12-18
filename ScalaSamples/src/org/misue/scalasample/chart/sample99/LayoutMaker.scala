// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample99

import java.awt.Dimension

class LayoutMaker(val area: Dimension) {
  	private val	centerX = area.getWidth() / 2.0
	private val	centerY = area.getHeight() / 2.0
	private val	radius = Math.min(area.getWidth(), area.getHeight()) * 0.35
	private val	itemSize = (radius / 10.0).toInt

	def layout(chart: Chart): Unit = {

		val itemSet = chart.itemSet
		val itemNum = itemSet.size
		val angle = Math.Pi * 2.0 / itemNum
		
		// TODO: don't use count.
		var count: Int = 0
		itemSet.foreach((item) => {
			item.x = (centerX + radius * Math.cos(angle * count)).toInt
			item.y = (centerY + radius * Math.sin(angle * count)).toInt
			item.size = itemSize
			count += 1
		})
	}

}
