// All Rights Reserved. Copyright (C) Kazuo Misue (2010)

package org.misue.scalasample.gd.exercise8

import java.awt.Dimension
import java.awt.geom.Point2D

class LayoutFunc(graph: Graph) {
	
  val c3: Double = 3.0
  val c4: Double = 1.0 //0.5  // 0.1 by P. Eades
  val thres: Double = 0.02
  val zero: Point2D = new Point2D.Double(0.0, 0.0)
  val nodes: List[Node] = graph.nodes
  val edges: List[Edge] = graph.edges
	
//  def layout(d: Dimension): Unit = {
//    computeSpringForce()
//    computeRepulsiveForce()
//    moveNewPos(d)
//  }

  def relax(d: Dimension): Double = {
    def maxForce(forces: List[Point2D], fmax: Double): Double = {
      forces match {
        case List() => fmax
        case f :: rest => maxForce(rest, math.max(fmax, math.sqrt(f.getX * f.getX + f.getY * f.getY)))
      }
    }

    val forces: List[Point2D] = nodes.map(n1 => computeForce(n1))
    val max = maxForce(forces, 0.0)
    if (max > thres) {
      nodes.zip(forces).map(pair => {
          val (n, f) = pair
          n.pos = moveNewPos(n, f, d)
        })
    }
    max
  }

  private def moveNewPos(n: Node, f: Point2D, d: Dimension): Point2D = {
    if (n.fixed) {
      n.pos
    } else {
      val x = n.pos.getX + math.max(-5, math.min(5, c4 * f.getX)) match {
        case x if (n.pos.getX < 0) => 0
        case x if (n.pos.getX > d.width) => d.width
        case x => x
      }
      val y = n.pos.getY + math.max(-5, math.min(5, c4 * f.getY)) match {
        case y if (n.pos.getY < 0) => 0
        case y if (n.pos.getY > d.height) => d.height
        case y => y
      }
      new Point2D.Double(x, y)
    }
  }


  private def computeForce(n1: Node): Point2D = {
    nodes.map(n2 => {
        if (n1 == n2)
          zero
        else
          graph.findEdge(n1, n2) match {
            case Some(e) => springForce(n1.pos, n2.pos, e.length)
            case None => repulsiveForce(n1.pos, n2.pos)
          }
    }).reduceLeft(
      (f1, f2) => new Point2D.Double(f1.getX + f2.getX, f1.getY + f2.getY)
    )
  }
		
//  def computeSpringForce(): Unit = {
//    edges.map(e => {
//        val vx: Double = e.head.x - e.tail.x
//	val vy: Double = e.head.y - e.tail.y
//	var len: Double = math.sqrt(vx * vx + vy * vy)
//	len = if (len == 0) 0.0001 else len
//	val f: Double = (e.length - len) / (len * 3)
//	val dx: Double = f * vx
//	val dy: Double = f * vy
//	e.head.dx += dx;
//	e.head.dy += dy;
//	e.tail.dx -= dx;
//	e.tail.dy -= dy;
//    })
//  }

  private def springForce(p1: Point2D, p2: Point2D, length: Double): Point2D = {
    val d: Double = adjustedDist(p1, p2)
    val f: Double = (length - d) / (d * 3)
    val fx: Double = f * (p1.getX - p2.getX)
    val fy: Double = f * (p1.getY - p2.getY)
    new Point2D.Double(fx, fy)
  }

//  private def springForce(p1: Point2D, p2: Point2D): Point2D = {
//    val d: Double = adjustedDist(p1, p2)
//    val f: Double = c1 * math.log(d / c2)
//    val fx: Double = f * (p2.getX - p1.getX) / d
//    val fy: Double = f * (p2.getY - p1.getY) / d
//    new Point2D.Double(fx, fy)
//  }

  
//  def computeRepulsiveForce(): Unit = {
//    nodes.map(n1 => {
//        var dx: Double = 0
//	var dy: Double = 0
//
//	nodes.map(n2 => {
//            if (n1 != n2) {
//              val vx: Double = n1.x - n2.x
//              val vy: Double = n1.y - n2.y
//              val len2 = vx * vx + vy * vy
//              if (len2 == 0) {
//                dx += math.random
//                dy += math.random
//              } else if (len2 < 100 * 100) {
//                dx += vx / len2
//                dy += vy / len2
//              }
//            }
//	})
//	var dlen: Double = dx * dx + dy * dy
//	if (dlen > 0) {
//          dlen = math.sqrt(dlen) / 2
//          n1.dx += dx / dlen
//          n1.dy += dy / dlen
//	}
//    })
//  }


  private def repulsiveForce(p1: Point2D, p2: Point2D): Point2D = {
    val d: Double = adjustedDist(p1, p2)
    val f: Double = c3 / math.sqrt(d)
    val fx: Double = f * (p1.getX - p2.getX) / d
    val fy: Double = f * (p1.getY - p2.getY) / d
    new Point2D.Double(fx ,fy)
  }
	
//  private def moveNewPos(d: Dimension): Unit = {
//    nodes.map(n => {
//        if (!n.fixed) {
//          n.x += math.max(-5, math.min(5, n.dx))
//          n.y += math.max(-5, math.min(5, n.dy))
//        }
//	if (n.x < 0) {
//          n.x = 0
//	} else if (n.x > d.width) {
//          n.x = d.width
//	}
//	if (n.y < 0) {
//          n.y = 0
//	} else if (n.y > d.height) {
//          n.y = d.height
//	}
//        n.dx /= 2
//	n.dy /= 2
//    })
//  }

//  def findStableState(): Unit = {
//    if (relax() > thres) findStableState()
//  }
//
//  def relax(): Double = {
//    def maxForce(forces: List[Point2D], fmax: Double): Double = {
//      forces match {
//        case List() => fmax
//        case f :: rest => maxForce(rest, math.max(fmax, math.sqrt(f.getX * f.getX + f.getY * f.getY)))
//      }
//    }
//
//    val forces: List[Point2D] = nodes.map(n1 => computeForce(n1))
//    val max = maxForce(forces, 0.0)
//    if (max > thres) {
//      nodePos = nodePos.zip(forces).map(pair => {
//          val (p, f) = pair
//          new Point2D.Double(p.getX + c4 * f.getX, p.getY + c4 * f.getY)
//        })
//    }
//    max
//  }
//
//  private def computeForce(n1: Int): Point2D = {
//    nodes.map(n2 => {
//        if (n1 == n2)
//          zero
//        else if ((adj(n1)(n2) > 0))
//          springForce(nodePos(n1), nodePos(n2))
//        else
//          repulsiveForce(nodePos(n1), nodePos(n2))
//    }).reduceLeft(
//      (f1, f2) => new Point2D.Double(f1.getX + f2.getX, f1.getY + f2.getY)
//    )
//  }
//
//  private def springForce(p1: Point2D, p2: Point2D): Point2D = {
//    val d: Double = adjustedDist(p1, p2)
//    val f: Double = c1 * math.log(d / c2)
//    val fx: Double = f * (p2.getX - p1.getX) / d
//    val fy: Double = f * (p2.getY - p1.getY) / d
//    new Point2D.Double(fx, fy)
//  }
//
//  private def repulsiveForce(p1: Point2D, p2: Point2D): Point2D = {
//    val d: Double = adjustedDist(p1, p2)
//    val f: Double = c3 / math.sqrt(d)
//    val fx: Double = f * (p1.getX - p2.getX) / d
//    val fy: Double = f * (p1.getY - p2.getY) / d
//    new Point2D.Double(fx ,fy)
//  }
//
  private def adjustedDist(p1: Point2D, p2: Point2D): Double = {
    p1.distance(p2) match {
        case x if (x < 0.01) => 0.01
 	case x => x
    }
  }

}
