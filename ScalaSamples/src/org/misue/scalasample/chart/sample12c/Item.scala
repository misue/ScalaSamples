// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample12c

import java.awt.Color
import java.awt.Graphics2D
import java.awt.Point

class Item(label: String) {
    var pos: Point = new Point
    var size: Int = 0
	var selected: Boolean = false

    private val nodeColor = Color.blue
    private val labelColor = Color.white
    private val selectedColor = Color.yellow
    
    def isPointed(px: Int, py: Int): Boolean = {
		val dx = pos.x - px
		val dy = pos.y - py
		(dx * dx + dy * dy) <= (size * size)
	}
    
    def isPointed(p: Point): Boolean = isPointed(p.x, p.y)
    
    def isEnclosed(x0: Int, y0: Int, x1: Int, y1: Int): Boolean = {
        assume((x0 <= x1) && (y0 <= y1)) 
        (x0 <= pos.x - size) && (pos.x + size <= x1) && (y0 <= pos.y - size) && (pos.y + size <= y1)
    }
    
    def isEnclosed(p0: Point, p1: Point): Boolean = isEnclosed(p0.x, p0.y, p1.x, p1.y)    
    
    def draw(g2: Graphics2D, nodeColor: Color, labelColor: Color): Unit = {
        val d: Int = size * 2
        g2.setColor(if (selected) selectedColor else nodeColor)
        g2.fillOval(pos.x - size, pos.y - size, d, d)
        g2.setColor(labelColor)
        g2.drawString(label, pos.x, pos.y)
    }

    def draw(g2: Graphics2D): Unit = draw(g2, nodeColor, labelColor)

}
