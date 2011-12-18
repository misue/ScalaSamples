// All Rights Reserved. Copyright (C) Kazuo Misue (2010)
package org.misue.scalasample.gd.sample2

import java.awt.Dimension
import java.awt.FontMetrics
import java.awt.Graphics2D
import java.awt.geom.Point2D

class Graph {
  var nodes: List[Node] = List()
  var edges: List[Edge] = List()
  var adjacency: Map[Tuple2[Node, Node], Edge] = Map()
  var pick: Node = null
  var pickfixed: Boolean = false

  def addNode(label: String): Node = {
    val x: Double = 10 + 780 * math.random
    val y: Double = 10 + 580 * math.random
    val n: Node = new Node(label, new Point2D.Double(x, y))
    nodes = n :: nodes
    n
  }

  def addEdge(tailLabel: String, headLabel: String, len: Int): Edge = {
    val tail = findNode(tailLabel)
    val head = findNode(headLabel)
    val e: Edge = new Edge(tail, head, len)
    adjacency += ((tail, head) -> e)
    edges = e :: edges
    e
  }
	
  def findNode(label: String): Node = {
    def findNode(label: String, nodes: List[Node]): Node = {
      nodes match {
        case List() => addNode(label)
	case node :: rest if (label.equals(node.label)) => node
	case node :: rest => findNode(label, rest)
      }
    }
    findNode(label, nodes)
  }

  def findEdge(tail: Node, head: Node): Option[Edge] = {
    adjacency.get(Tuple2(tail, head)) match {
      case Some(e) => Some(e)
      case None => adjacency.get(Tuple2(head, tail))
    }
  }
	
  def fixNode(label: String, pos: Point2D): Unit = {
    val n: Node = findNode(label)
    n.pos = pos
    n.fixed = true
  }
	
  def draw(g: Graphics2D, fm: FontMetrics, stress: Boolean): Unit = {
    edges.map(edge => edge.draw(g, stress))
    nodes.map(node => node.draw(g, fm, pick))
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
    nodes.map(node => {
        if (!node.fixed) {
          val x = 10 + (d.width - 20) * math.random
          val y = 10 + (d.height - 20) * math.random
          node.pos = new Point2D.Double(x, y)
        }
    })
  }
	
  def shake(): Unit = {
    nodes.map(node => {
        if (!node.fixed) {
          val x = node.pos.getX + 80 * math.random - 40
          val y = node.pos.getY + 80 * math.random - 40
          node.pos = new Point2D.Double(x, y)
	}			
    })
  }
}
