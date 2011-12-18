// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample99

import java.awt.Graphics2D

class Chart {
	var itemSet: List[Item] = List()
	
	def entry(item: Item): Unit =
		itemSet = item :: itemSet
	
	def draw(g2: Graphics2D, width: Int, height: Int): Unit =
		itemSet.foreach(_.draw(g2))

}

object Chart {
  	def createSample(n: Int): Chart = {
		val chart = new Chart
		for (val i <-  0 to n) {
			chart.entry(new Item(i.toString))		
		}
		return chart
	}
}
