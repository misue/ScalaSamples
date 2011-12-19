// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package visual.sample01

import java.awt.Color
import java.awt.Graphics2D

class Item(val label: String) {
    var x: Int = 0
    var y: Int = 0
    var size: Int = 0

    private val nodeColor = Color.blue
    private val labelColor = Color.white

    def draw(g2: Graphics2D, nodeColor: Color, labelColor: Color): Unit = {
        val d: Int = size * 2
        g2.setColor(nodeColor)
        g2.fillOval(x - size, y - size, d, d)
        g2.setColor(labelColor)
        g2.drawString(label, x, y)
    }

    def draw(g2: Graphics2D): Unit = draw(g2, nodeColor, labelColor)

}
