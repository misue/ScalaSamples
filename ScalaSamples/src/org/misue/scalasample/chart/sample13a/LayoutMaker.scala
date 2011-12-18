// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample13a

import java.awt.Dimension
import scala.actors.Actor

class LayoutMaker(val area: Dimension) extends Actor {
    private val centerX = area.getWidth() / 2.0
    private val centerY = area.getHeight() / 2.0
    private val radius = Math.min(area.getWidth(), area.getHeight()) * 0.35
    private val itemSize = 20
    private val rotateUnit = 3
	private var rotateDegree = 0

    private def layout(chart: Chart): Unit = {
        val itemSet = chart.itemSet
        val itemNum = itemSet.size
        val unitAngle = Math.Pi * 2.0 / itemNum
        var rotateAngle = Math.Pi * rotateDegree / 180.0

        itemSet.foldLeft[Int](0)((count, item) => {
            val angle = unitAngle * count + rotateAngle
            item.x = (centerX + radius * Math.cos(angle)).toInt
            item.y = (centerY + radius * Math.sin(angle)).toInt
            item.size = itemSize
            count + 1
        })
        rotateDegree += rotateUnit
        if (rotateDegree >= 360) rotateDegree -= 360
    }
    
    override def act(): Unit = {
        loop {
            react {
                case chart: Chart => layout(chart)
            }
        }
    }

}
