// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample12c

import java.awt.Graphics2D
import java.awt.Point
import java.awt.Color

class Chart(val itemSet: List[Item]) {
    private var selectedItems: List[Item] = List()
    private val rubberBandColor = Color.GRAY
    private var p0: Point = null // one vertex of rubber band ractangle
    private var xmin: Int = 0
    private var xmax: Int = 0
    private var ymin: Int = 0
    private var ymax: Int = 0
    
    
    def entry(item: Item): Chart = new Chart(item :: itemSet)

//    private def findItem(p: Point): Option[Item] = itemSet.find(_.isPointed(p))
    private def findItem(): List[Item] = 
        itemSet.filter(_.isEnclosed(xmin, ymin, xmax, ymax))

    def draw(g2: Graphics2D, width: Int, height: Int): Unit = {
        itemSet.foreach(_.draw(g2))
        if ((p0 != null) && ((xmin < xmax) || (ymin < ymax))) {
            g2.setColor(rubberBandColor)
            g2.drawRect(xmin, ymin, xmax - xmin, ymax - ymin)
        }
    }        

    def mousePressed(p: Point): Unit = {
        selectedItems.foreach(_.selected = false)
        selectedItems = List()
        p0 = p
        xmin = p0.x
        ymin = p0.y
        xmax = p0.x
        ymax = p0.y
    }

    def mouseReleased(p1: Point): Unit = {
        p0 = null
    }

    def mouseDragged(p1: Point): Unit = {
        xmin = Math.min(p0.x, p1.x)
        ymin = Math.min(p0.y, p1.y)
        xmax = Math.max(p0.x, p1.x)
        ymax = Math.max(p0.y, p1.y)
        selectedItems.foreach(_.selected = false)
        selectedItems = findItem
        selectedItems.foreach(_.selected = true)
    }
}

object Chart {
    def createSample(n: Int): Chart = {
        if (n <= 0) new Chart(List())
        else createSample(n - 1).entry(new Item(n.toString))
    }
}
