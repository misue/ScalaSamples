// All Rights Reserved. Copyright (C) Kazuo Misue (2010-2011)
package org.misue.scalasample.gd.trial3

import java.awt.Dimension
import java.awt.FontMetrics
import java.awt.Graphics2D
import java.awt.geom.Point2D
import java.io.File
import scala.io.Source
import java.awt.Font

class Graph(val nodes: List[Node], val edges: List[Edge]) {
    
    private val font: Font = new Font("Helvetica", Font.PLAIN, 12)

    def draw(g: Graphics2D, fm: FontMetrics, showLabel: Boolean, showStress: Boolean): Unit = {
        g.setFont(font)
        edges.foreach(_.draw(g, showStress))
        nodes.foreach(_.draw(g, showLabel, fm))
    }

    def scramble(d: Dimension): Unit = {
        nodes.foreach(node => {
            val x = 10 + (d.width - 20) * math.random
            val y = 10 + (d.height - 20) * math.random
            node.pos = new Point2D.Double(x, y)
        })
    }

}

object Graph {

    def readFile(file: File): Graph = {

        val triples: List[Tuple3[String, String, Double]] = Source.fromFile(file).getLines.map((line) => {
            val tokens = line.split(",")
            tokens.length match {
                case 3 => Some(tokens(0), tokens(1), tokens(2).toDouble)
                case _ => None
            }
        }).filter(_.isDefined).map(_.get).toList

        val nodeMap = triples.map((t) => List(t._1, t._2)).flatten.foldLeft[Map[String, Node]](Map())((map, label) => {
            map.get(label) match {
                case Some(node) => map
                case None => map + (label -> new Node(label))
            }
        })
        val nodeSet: List[Node] = nodeMap.values.toList
        val edgeSet: List[Edge] = triples.map((t) => new Edge(nodeMap.get(t._1).get, nodeMap.get(t._2).get, t._3))
        new Graph(nodeSet, edgeSet)
    }

}

