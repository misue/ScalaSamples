// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample01

import java.awt.Dimension

class LayoutMaker(val area: Dimension) {
    private val centerX = area.getWidth() / 2.0
    private val centerY = area.getHeight() / 2.0
    private val radius = Math.min(area.getWidth(), area.getHeight()) * 0.35
    private val itemSize = 20

    def layout(chart: Chart): Unit = {
        val itemSet = chart.itemSet
        val itemNum = itemSet.size
        val unitAngle = Math.Pi * 2.0 / itemNum

        itemSet.foldLeft[Int](0)((count, item) => {
            layoutItem(item, unitAngle * count, itemSize)
            count + 1
        })
    }

    private def layoutItem(item: Item, angle: Double, size: Int): Unit = {
        item.x = (centerX + radius * Math.cos(angle)).toInt
        item.y = (centerY + radius * Math.sin(angle)).toInt
        item.size = itemSize
    }

}
