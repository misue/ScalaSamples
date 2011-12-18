// All Rights Reserved. Copyright (C) Kazuo Misue (2010-2011)
package org.misue.scalasample.gd.sample3b

import java.awt.Dimension
import java.awt.FontMetrics
import java.awt.Graphics2D
import java.awt.geom.Point2D
import java.io.File
import scala.io.Source
import java.awt.Font

class Graph(val nodes: List[Node], val edges: List[Edge]) {

    private val font: Font = new Font("Helvetica", Font.PLAIN, 9)
    var showStress: Boolean = false
    var pick: Node = null
    var pickfixed: Boolean = false

    def draw(g: Graphics2D, fm: FontMetrics): Unit = {
        g.setFont(font)
        edges.foreach(_.draw(g, showStress))
        nodes.foreach(_.draw(g, fm))
    }

    private def draw(g: Graphics2D, fm: FontMetrics, showStress: Boolean): Unit = {
        g.setFont(font)
        edges.foreach(_.draw(g, showStress))
        nodes.foreach(_.draw(g, fm))
    }

    def pickNode(p1: Point2D): Unit = {
        var bestdist: Double = 1000000
        nodes.map(node => {
            //        val diffx: Double = node.x - x
            //	val diffy: Double = node.y - y
            val dist2: Double = p1.distanceSq(node.pos)
            if (dist2 < bestdist) {
                pick = node
                bestdist = dist2
            }
        })
        pickfixed = pick.fixed
        pick.fixed = true
        //    pick.x = x
        //    pick.y = y
        pick.pos = p1
    }

    def releaseNode(p1: Point2D, release: Boolean): Unit = {
        pick.fixed = pickfixed
        //    pick.x = x
        //    pick.y = y
        pick.pos = p1
        if (release) {
            pick = null
        }
    }

    def dragNode(p1: Point2D): Unit = {
        //    pick.x = x
        //    pick.y = y
        pick.pos = p1
    }

    def scramble(d: Dimension): Unit = {
        nodes.filterNot(_.fixed) foreach (node => {
            val x = 10 + (d.width - 20) * math.random
            val y = 10 + (d.height - 20) * math.random
            node.pos = new Point2D.Double(x, y)
        })
    }

    def shake(): Unit = {
        nodes.filterNot(_.fixed).foreach(node => {
            val x = node.pos.getX + 80 * math.random - 40
            val y = node.pos.getY + 80 * math.random - 40
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

