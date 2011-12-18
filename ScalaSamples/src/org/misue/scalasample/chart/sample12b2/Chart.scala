// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample12b2

import java.awt.Graphics2D
import java.awt.Point

class Chart(val itemSet: List[Item] = List()) {
    private var selectedItem: Item = null
    
  	def itemNum: Int = itemSet.size

    def entry(item: Item): Chart = new Chart(item :: itemSet)

    private def findItem(p: Point): Option[Item] = itemSet.find(_.isPointed(p))

    def draw(g2: Graphics2D, width: Int, height: Int): Unit =
        itemSet.foreach(_.draw(g2))

    def mousePressed(p: Point): Unit = {
        if (selectedItem != null) {
            selectedItem.selected = false
            selectedItem = null
        }
        findItem(p) match {
            case Some(pointedItem) => {
                selectedItem = pointedItem
                selectedItem.selected = true
                selectedItem.pos = p
            }
            case None =>
        }
    }

    def mouseReleased(p: Point): Unit = {
        if (selectedItem != null) selectedItem.pos = p
    }

    def mouseDragged(p: Point): Unit = {
        if (selectedItem != null) selectedItem.pos = p
    }
}

object Chart {
    def createSample(n: Int): Chart = {
        if (n <= 0) new Chart(List())
        else createSample(n - 1).entry(new Item(n.toString))
    }
}
