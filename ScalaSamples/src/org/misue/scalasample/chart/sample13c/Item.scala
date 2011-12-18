// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample13c

import java.awt.Color
import java.awt.Graphics2D
import java.awt.Point
import java.awt.geom.Point2D

class Item(label: String) {
    var pos: Point2D = null
    var size: Int = 0
	var selected: Boolean = false
    var pointed: Boolean = false

    private val nodeColor = Color.blue
    private val labelColor = Color.white
    private val selectedColor = Color.yellow
    
    def isPointed(px: Int, py: Int): Boolean = {
		val dx = pos.getX - px
		val dy = pos.getY - py
		(dx * dx + dy * dy) <= (size * size)
	}
    
    def isPointed(p: Point): Boolean = isPointed(p.x, p.y)
    
    def draw(g2: Graphics2D, nodeColor: Color, labelColor: Color): Unit = {
        val d: Int = size * 2
        g2.setColor(if (selected) selectedColor else nodeColor)
        g2.fillOval((pos.getX - size).toInt, (pos.getY - size).toInt, d, d)
        g2.setColor(labelColor)
        g2.drawString(label, pos.getX.toInt, pos.getY.toInt)
    }

    def draw(g2: Graphics2D): Unit = draw(g2, nodeColor, labelColor)

}
