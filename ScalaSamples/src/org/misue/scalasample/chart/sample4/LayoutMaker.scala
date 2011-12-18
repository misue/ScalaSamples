// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample4

import java.awt.Dimension

class LayoutMaker {

    def layout(chart: Chart): Unit = {
        val itemSet = chart.itemSet
        val itemNum = itemSet.size
        val unitAngle = Math.Pi * 2.0 / itemNum

        itemSet.foldLeft[Int](0)((count, item) => {
            val angle = unitAngle * count
            item.x = Math.cos(angle)
            item.y = Math.sin(angle)
            count + 1
        })
    }

}
