// All Rights Reserved. Copyright (C) Kazuo Misue (2010-2011)
package org.misue.scalasample.gd.simple04

import java.awt.Dimension
import java.awt.geom.Point2D
import javax.swing.SwingUtilities

class LayoutFunc(graph: Graph) {

    private def nodes: List[Node] = graph.nodes
    private def edges: List[Edge] = graph.edges
//    private val c3: Double = 3.0
//    private val c4: Double = 1.0 //0.5  // 0.1 by P. Eades
//    private val thres: Double = 0.01
//    private val zero: Point2D = new Point2D.Double(0.0, 0.0)
    private val epsilon: Double = 0.001
    
//    private val adjacency = edges.foldLeft[Map[Tuple2[Node, Node], Edge]](Map())((adj, e) => {
//        adj + ((e.tail, e.head) -> e, (e.head, e.tail) -> e)
//    })
    
    
    def circularize(nodeList: List[Node], r: Double): Unit = {
        val count: Int = nodeList.size
        val dtheta: Double = 2.0d * Math.Pi / count
        var theta: Double = 0.0d

        nodeList.foreach((n) => {
            n.r = new Point2D.Double(
                200+ r * Math.cos(theta),
                200+ r * Math.sin(theta))
            n.v = new Point2D.Double(0.0, 0.0)
            theta += dtheta
        })
    }

    def update(d: Dimension): Unit = {
//        println("relax On EDT:" + SwingUtilities.isEventDispatchThread())
      
//        // Compute force for each node
//        val forces: List[Point2D] = nodes.map(computeForce)
//        // Search max force 
//        val fmax = math.sqrt(forces.foldLeft[Double](0f)((fmax2, f) => math.max(fmax2, f.getX * f.getX + f.getY * f.getY)))
//        if (fmax > thres) {
//            nodes.zip(forces).foreach(pair => {
//                val (node, force) = pair
//                node.pos = moveNewPos(node, force, d)
//            })
//        }
//        fmax
        moveAll()
    }

//    private def moveNewPos(n: Node, f: Point2D, d: Dimension): Point2D = {
//        val maxMovement: Int = 10
//        val x = (n.pos.getX + math.max(-maxMovement, math.min(maxMovement, c4 * f.getX))) match {
//            case x if (x < 0) => 0
//            case x if (x > d.width) => d.width
//            case x => x
//        }
//        val y = (n.pos.getY + math.max(-maxMovement, math.min(maxMovement, c4 * f.getY))) match {
//            case y if (y < 0) => 0
//            case y if (y > d.height) => d.height
//            case y => y
//        }
//        new Point2D.Double(x, y)
//    }

//    private def computeForce(n1: Node): Point2D = {
//        nodes.map(n2 => {
//            if (n1 == n2)
//                zero
//            else
//                adjacency.get((n1, n2)) match {
//                    case Some(e) => springForce(n1.pos, n2.pos, e.length)
//                    case None => repulsiveForce(n1.pos, n2.pos)
//                }
//        }).reduceLeft((f1, f2) => new Point2D.Double(f1.getX + f2.getX, f1.getY + f2.getY))
//    }

//    private def springForce(p1: Point2D, p2: Point2D, length: Double): Point2D = {
//        val d: Double = adjustedDist(p1, p2)
//        val f: Double = (length - d) / (d * 3)
//        val fx: Double = f * (p1.getX - p2.getX)
//        val fy: Double = f * (p1.getY - p2.getY)
//        new Point2D.Double(fx, fy)
//    }

//    private def repulsiveForce(p1: Point2D, p2: Point2D): Point2D = {
//        val d: Double = adjustedDist(p1, p2)
//        val f: Double = c3 / math.sqrt(d)
//        val fx: Double = f * (p1.getX - p2.getX) / d
//        val fy: Double = f * (p1.getY - p2.getY) / d
//        new Point2D.Double(fx, fy)
//    }

//    private def adjustedDist(p1: Point2D, p2: Point2D): Double = {
//        p1.distance(p2) match {
//            case x if (x < 0.01) => 0.01
//            case x => x
//        }
//    }
    
    
    def moveAll(): Unit = {

        val dt: Double = 0.5d//0.1d
        nodes.foreach((n1) => {

            var f: Point2D = new Point2D.Double(0, 0)
            
            edges.foreach((edge) => {
                val p: Point2D = if (edge.tail == n1) getSpringForce(n1, edge.head)
                else if (edge.head == n1) getSpringForce(n1, edge.tail)
                else new Point2D.Double(0, 0)
                f = new Point2D.Double(f.getX + p.getX, f.getY + p.getY)
            })

            nodes.foreach((n2) => {
                if (n1 != n2) {
                    val p = getReplusiveForce(n1, n2)
                    f = new Point2D.Double(f.getX + p.getX, f.getY + p.getY)
                }
            })
            
            val p: Point2D = getFrictionalForce(n1)
            f = new Point2D.Double(f.getX + p.getX, f.getY + p.getY)
            
            moveEular(n1, dt, f)
        })
    }
    
    
    def getSpringForce(n1: Node, n2: Node): Point2D = {
        // ばねの力は自然長からの変位に比例 (比例定数 -k, ばねの長さ l)
        val k: Double = 0.1d
        val l: Double = 60.0d
        val dx: Double = n1.r.getX - n2.r.getX
        val dy: Double = n1.r.getY - n2.r.getY
        val d2: Double = dx * dx + dy * dy
        if (d2 < epsilon) {
            new Point2D.Double(Math.random - 0.5d, Math.random - 0.5d)
        } else {
            val d: Double = Math.sqrt(d2)
            val cos: Double = dx / d
            val sin: Double = dy / d
            val dl: Double = d - l
            new Point2D.Double(-k * dl * cos, -k * dl * sin)
        }
    }

    def getReplusiveForce(n1: Node, n2: Node): Point2D = {
        // 反発は距離の2乗に反比例 (比例定数 g)
        val g: Double = 500.0d
        val dx: Double = n1.r.getX - n2.r.getX
        val dy: Double = n1.r.getY - n2.r.getY
        val d2: Double = dx * dx + dy * dy
        if (d2 < epsilon) {
            new Point2D.Double(Math.random - 0.5d, Math.random - 0.5d)
        } else {
            val d: Double = Math.sqrt(d2)
            val cos: Double = dx / d
            val sin: Double = dy / d
            new Point2D.Double(g / d2 * cos, g / d2 * sin)
        }
    }
    
    
    def getFrictionalForce(n1: Node): Point2D = {
        // 摩擦力は速度に比例 (比例定数 -m)
        val m: Double = 0.3d
        new Point2D.Double(-m * n1.v.getX, -m * n1.v.getY)
    }

    def moveEular(n1: Node, dt: Double, f: Point2D): Unit = {
        // 質量は1とする
        n1.r = new Point2D.Double(n1.r.getX + dt * n1.v.getX, n1.r.getY + dt * n1.v.getY)
        n1.v = new Point2D.Double(n1.v.getX + dt * f.getX, n1.v.getY + dt * f.getY)
    }

}
