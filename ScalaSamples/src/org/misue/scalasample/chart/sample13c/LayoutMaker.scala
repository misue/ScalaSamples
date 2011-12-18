// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample13c

import java.awt.Dimension
import java.awt.geom.Point2D

class LayoutMaker(chart: Chart) {
    private val itemSize = 20
    private val stepAngle: Double = Math.Pi / 360.0
    
    def initLayout(area: Dimension): Unit = {
        val centerX = area.getWidth() / 2.0
        val centerY = area.getHeight() / 2.0
        val radius = Math.min(area.getWidth(), area.getHeight()) * 0.35
        val itemSet = chart.itemSet
        val unitAngle = Math.Pi * 2.0 / itemSet.size

        itemSet.foldLeft[Int](0)((count, item) => {
            val angle = unitAngle * count
            val x = centerX + radius * Math.cos(angle)
            val y = centerY + radius * Math.sin(angle)
            item.pos = new Point2D.Double(x, y)
            item.size = itemSize
            count + 1
        })
    }
    
    def update(area: Dimension): Unit = {
        val centerX = area.getWidth() / 2.0
        val centerY = area.getHeight() / 2.0
        chart.itemSet.filterNot(_.pointed).foreach((item) => {
        	val crntX = item.pos.getX()
        	val crntY = item.pos.getY()
        	val vx = crntX - centerX
        	val vy = crntY - centerY
        	val angle = Math.atan2(vy, vx) + stepAngle
        	val radius = Math.sqrt(vx * vx + vy * vy)
            val x = centerX + radius * Math.cos(angle)
            val y = centerY + radius * Math.sin(angle)
            item.pos = new Point2D.Double(x, y)        	
        })
    }
    
}
