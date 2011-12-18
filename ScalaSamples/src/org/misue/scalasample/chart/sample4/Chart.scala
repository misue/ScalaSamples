// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample4

import java.awt.Graphics2D

class Chart(val itemSet: List[Item]) {

    def entry(item: Item): Chart = new Chart(item :: itemSet)

    def draw(g2: Graphics2D, width: Int, height: Int): Unit = {
        val size = Math.min(width, height)
        val scale = size * 0.35
        val centerX = width / 2.0
        val centerY = height / 2.0
        itemSet.foreach(_.draw(g2, scale, centerX, centerY))
    }
}

object Chart {
    def createSample(n: Int): Chart = {
        if (n <= 0) new Chart(List())
        else createSample(n - 1).entry(new Item(n.toString))
    }
}
