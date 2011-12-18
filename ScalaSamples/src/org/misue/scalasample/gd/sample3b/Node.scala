// All Rights Reserved. Copyright (C) Kazuo Misue (2010)
package org.misue.scalasample.gd.sample3b

import java.awt.Color
import java.awt.FontMetrics
import java.awt.Graphics2D
import java.awt.geom.Point2D
//import java.awt.Font

class Node(val label: String) {

    val x: Double = 10 + 780 * math.random
    val y: Double = 10 + 580 * math.random

    var pos: Point2D = new Point2D.Double(x, y)
    var dx: Double = 0
    var dy: Double = 0
    var fixed: Boolean = false

    val fixedColor: Color = Color.red
    val selectedColor: Color = Color.pink
    val nodeColor: Color = new Color(250, 220, 100)

    def draw(g: Graphics2D, fm: FontMetrics): Unit = {
        val ix: Int = pos.getX.toInt
        val iy: Int = pos.getY.toInt
        g.setColor(nodeColor)
        val w: Int = fm.stringWidth(label) + 10
        val h: Int = fm.getHeight() + 4
        g.fillRect(ix - w / 2, iy - h / 2, w, h);
        g.setColor(Color.black);
        g.drawRect(ix - w / 2, iy - h / 2, w - 1, h - 1);
        g.drawString(label, ix - (w - 10) / 2, (iy - (h - 4) / 2) + fm.getAscent());
    }

}
