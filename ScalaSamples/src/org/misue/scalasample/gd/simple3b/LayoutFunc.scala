// All Rights Reserved. Copyright (C) Kazuo Misue (2010-2011)
package org.misue.scalasample.gd.simple3b

import java.awt.Dimension
import java.awt.geom.Point2D
import javax.swing.SwingUtilities

class LayoutFunc(graph: Graph) {

    private def nodes: List[Node] = graph.nodes
    private def edges: List[Edge] = graph.edges
    private val c3: Double = 3.0
    private val c4: Double = 1.0 //0.5  // 0.1 by P. Eades
    private val thres: Double = 0.01
    private val zero: Point2D = new Point2D.Double(0.0, 0.0)
    
    private val adjacency = edges.foldLeft[Map[Tuple2[Node, Node], Edge]](Map())((adj, e) => {
        adj + ((e.tail, e.head) -> e, (e.head, e.tail) -> e)
    })

    def update(d: Dimension): Double = {
//        println("relax On EDT:" + SwingUtilities.isEventDispatchThread())
      
        // Compute force for each node
        val forces: List[Point2D] = nodes.map(computeForce)
        // Search max force 
        val fmax = math.sqrt(forces.foldLeft[Double](0f)((fmax2, f) => math.max(fmax2, f.getX * f.getX + f.getY * f.getY)))
        if (fmax > thres) {
            nodes.zip(forces).foreach(pair => {
                val (node, force) = pair
                node.pos = moveNewPos(node, force, d)
            })
        }
        fmax
    }

    private def moveNewPos(n: Node, f: Point2D, d: Dimension): Point2D = {
        val maxMovement: Int = 10
        val x = (n.pos.getX + math.max(-maxMovement, math.min(maxMovement, c4 * f.getX))) match {
            case x if (x < 0) => 0
            case x if (x > d.width) => d.width
            case x => x
        }
        val y = (n.pos.getY + math.max(-maxMovement, math.min(maxMovement, c4 * f.getY))) match {
            case y if (y < 0) => 0
            case y if (y > d.height) => d.height
            case y => y
        }
        new Point2D.Double(x, y)
    }

    private def computeForce(n1: Node): Point2D = {
        nodes.map(n2 => {
            if (n1 == n2)
                zero
            else
                adjacency.get((n1, n2)) match {
                    case Some(e) => springForce(n1.pos, n2.pos, e.length)
                    case None => repulsiveForce(n1.pos, n2.pos)
                }
        }).reduceLeft((f1, f2) => new Point2D.Double(f1.getX + f2.getX, f1.getY + f2.getY))
    }

    private def springForce(p1: Point2D, p2: Point2D, length: Double): Point2D = {
        val d: Double = adjustedDist(p1, p2)
        val f: Double = (length - d) / (d * 3)
        val fx: Double = f * (p1.getX - p2.getX)
        val fy: Double = f * (p1.getY - p2.getY)
        new Point2D.Double(fx, fy)
    }

    private def repulsiveForce(p1: Point2D, p2: Point2D): Point2D = {
        val d: Double = adjustedDist(p1, p2)
        val f: Double = c3 / math.sqrt(d)
        val fx: Double = f * (p1.getX - p2.getX) / d
        val fy: Double = f * (p1.getY - p2.getY) / d
        new Point2D.Double(fx, fy)
    }

    private def adjustedDist(p1: Point2D, p2: Point2D): Double = {
        p1.distance(p2) match {
            case x if (x < 0.01) => 0.01
            case x => x
        }
    }

}
