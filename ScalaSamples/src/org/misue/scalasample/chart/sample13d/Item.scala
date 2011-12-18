// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample13d

import java.awt.Color
import java.awt.Graphics2D
import java.awt.Point
import java.awt.geom.Point2D

class Item(label: String) {
    var crntPos: Point2D = null
    var nextPos: Point2D = null
    var size: Int = 0
	var selected: Boolean = false
    var pointed: Boolean = false

    private val nodeColor = Color.blue
    private val labelColor = Color.white
    private val selectedColor = Color.yellow
    
    def pos: Point2D = crntPos
    
    def pos_=(p: Point2D): Unit = {
        crntPos = p
        nextPos = p
    }
    
    def isPointed(px: Int, py: Int): Boolean = {
		val dx = crntPos.getX - px
		val dy = crntPos.getY - py
		(dx * dx + dy * dy) <= (size * size)
	}
    
    def isPointed(p: Point): Boolean = isPointed(p.x, p.y)
    
    // exponential speed 
    private def updatePos1(alpha: Double): Unit = {
        val newX = (1.0 - alpha) * crntPos.getX + alpha * nextPos.getX
        val newY = (1.0 - alpha) * crntPos.getY + alpha * nextPos.getY
        crntPos = new Point2D.Double(newX, newY)
    }
    
    // constant speed
    private def updatePos2(delta: Double): Unit = {
        val distance = crntPos.distance(nextPos)
        if (distance <= delta) {
            crntPos = nextPos
        } else {
            val deltaX = (nextPos.getX - crntPos.getX) * delta / distance
            val deltaY = (nextPos.getY - crntPos.getY) * delta / distance
            crntPos = new Point2D.Double(crntPos.getX + deltaX, crntPos.getY + deltaY)
        }       
    }
    
    def draw(g2: Graphics2D, nodeColor: Color, labelColor: Color): Unit = {
        updatePos1(0.1)
//        updatePos2(10.0)
        val d: Int = size * 2
        g2.setColor(if (selected) selectedColor else nodeColor)
        g2.fillOval((crntPos.getX - size).toInt, (crntPos.getY - size).toInt, d, d)
        g2.setColor(labelColor)
        g2.drawString(label, crntPos.getX.toInt, crntPos.getY.toInt)
    }

    def draw(g2: Graphics2D): Unit = draw(g2, nodeColor, labelColor)

}
