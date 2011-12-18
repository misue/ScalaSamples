// All Rights Reserved. Copyright (C) Kazuo Misue (2010-2011)
package org.misue.scalasample.gd.simple3

import java.awt.Color
import java.awt.Graphics2D

class Edge(
    val tail: Node,
    val head: Node,
    val length: Double) {
    
    private val edgeColor: Color = Color.gray //new Color(100, 100, 128)
    private val stressColor: Color = Color.gray
    
    println("edge " + tail.label + " -> " + head.label + " with " + length)
    

    def draw(g: Graphics2D, showStress: Boolean) {
        val x1: Int = tail.pos.getX.toInt
        val y1: Int = tail.pos.getY.toInt
        val x2: Int = head.pos.getX.toInt
        val y2: Int = head.pos.getY.toInt
        g.setColor(edgeColor);
        g.drawLine(x1, y1, x2, y2)
        if (showStress) {
            val dx: Int = x2 - x1
            val dy: Int = y2 - y1
            val stressValue: Int = math.abs(math.sqrt(dx * dx + dy * dy) - length).toInt
            g.setColor(stressColor)
            g.drawString(String.valueOf(stressValue), x1 + dx / 2, y1 + dy / 2)
        }
    }
}