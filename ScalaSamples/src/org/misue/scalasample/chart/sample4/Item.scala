// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample4

import java.awt.Color
import java.awt.Graphics2D

class Item(label: String) {
    var x: Double = 0
    var y: Double = 0

    private val nodeColor = Color.blue
    private val labelColor = Color.white
    private val itemSize = 0.1

    def draw(g2: Graphics2D, scale: Double, centerX: Double, centerY: Double, nodeColor: Color, labelColor: Color): Unit = {
        val r = itemSize * scale
        val d = (r * 2).toInt
        val posX = (centerX + scale * x).toInt
        val posY = (centerY + scale * y).toInt

        g2.setColor(nodeColor)
        g2.fillOval((posX - r).toInt, (posY - r).toInt, d, d)
        g2.setColor(labelColor)
        g2.drawString(label, posX.toInt, posY.toInt)
    }

    def draw(g2: Graphics2D, scale: Double, centerX: Double, centerY: Double): Unit =
        draw(g2, scale, centerX, centerY, nodeColor, labelColor)

}
