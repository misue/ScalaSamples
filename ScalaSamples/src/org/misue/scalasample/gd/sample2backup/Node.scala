// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.gd.sample2backup

import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.Point2D

class Node(val label: String) {
    private val nodeColor = Color.blue
    private val labelColor = Color.white
    private val size: Int = 10
    private val diam: Int = size * 2

//    def draw(g2: Graphics2D, pos: Tuple2[Double, Double], nodeColor: Color, labelColor: Color): Unit = {
//        val (x, y) = pos
//        g2.setColor(nodeColor)
//        g2.fillOval((x - size).toInt, (y - size).toInt, diam, diam)
//        g2.setColor(labelColor)
//        g2.drawString(label, x.toInt, y.toInt)
//    }
//
//    def draw(g2: Graphics2D, pos: Tuple2[Double, Double]): Unit = draw(g2, pos, nodeColor, labelColor)

    def draw(g2: Graphics2D, pos: Point2D, nodeColor: Color, labelColor: Color): Unit = {
        val x = pos.getX.toInt
        val y = pos.getY.toInt
        g2.setColor(nodeColor)
        g2.fillOval(x - size, y - size, diam, diam)
        g2.setColor(labelColor)
        g2.drawString(label, x, y)
    }

    def draw(g2: Graphics2D, pos: Point2D): Unit = draw(g2, pos, nodeColor, labelColor)
}
