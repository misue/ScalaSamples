// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample10

import java.awt.Graphics2D
import java.awt.Color

class Chart(val itemSet: List[Item] = List()) {
//	private val labelColor = Color.white
	var itemSize = 30
    
	def itemNum: Int = itemSet.size
	
//	def setItemColor(color: Color): Unit = {
//		nodeColor = color
//	}
    	
    def entry(item: Item): Chart = new Chart(item :: itemSet)

    def draw(g2: Graphics2D, width: Int, height: Int): Unit =
        itemSet.foreach(_.draw(g2, itemSize))

}

object Chart {
    def createSample(n: Int): Chart = {
        if (n <= 0) new Chart()//List())
        else createSample(n - 1).entry(new Item(n.toString))
    }
}
