// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.gd.sample2backup

import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.Point2D

class Edge(val tail: Node, val head: Node) {
    
    val edgeColor: Color = Color.GRAY

    def draw(g2: Graphics2D, nodeMap: Map[Node, Point2D], edgeColor: Color): Unit = {
        val tailPos = nodeMap.get(tail).get
        val headPos = nodeMap.get(head).get
        g2.setColor(edgeColor)
        g2.drawLine(tailPos.getX.toInt, tailPos.getY.toInt, headPos.getX.toInt, headPos.getY.toInt)
    }

    def draw(g2: Graphics2D, nodeMap: Map[Node, Point2D]): Unit = draw(g2, nodeMap, edgeColor)
}
