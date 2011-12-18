// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample13d

import java.awt.Graphics2D
import scala.actors.Actor
import java.awt.Point

class Chart(val itemSet: List[Item] = Nil) {
    private var selectedItem: Item = null
    private var pointedItem: Item = null

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
            case Some(item) => {
                pointedItem = item
                pointedItem.pointed = true
                selectedItem = item
                selectedItem.selected = true
                selectedItem.pos = p
            }
            case None =>
        }
    }

    def mouseReleased(p: Point): Unit = {
        if (selectedItem != null) selectedItem.pos = p
        if (pointedItem != null) {
            pointedItem.pointed = false
            pointedItem = null
        }
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
