// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.gd.sample2backup

import java.awt.Dimension
import scala.actors.Actor

class LayoutMaker(val area: Dimension) {
    private val centerX = area.getWidth() / 2.0
    private val centerY = area.getHeight() / 2.0
    private val radius = Math.min(area.getWidth(), area.getHeight()) * 0.35
    private val itemSize = 20
    private val rotateUnit = 3
	private var rotateDegree = 0

    def layout(graph: Graph): Unit = {
        val nodeSet = graph.nodeSet
        val itemNum = nodeSet.size
        val unitAngle = Math.Pi * 2.0 / itemNum
        var rotateAngle = Math.Pi * rotateDegree / 180.0

        nodeSet.foldLeft[Int](0)((count, item) => {
            val angle = unitAngle * count + rotateAngle
//            item.x = (centerX + radius * Math.cos(angle)).toInt
//            item.y = (centerY + radius * Math.sin(angle)).toInt
//            item.size = itemSize
            count + 1
        })
        rotateDegree += rotateUnit
        if (rotateDegree >= 360) rotateDegree -= 360
    }
}
