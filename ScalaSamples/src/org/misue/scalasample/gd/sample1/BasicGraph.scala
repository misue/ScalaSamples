// All Rights Reserved. Copyright (C) Kazuo Misue (2010)
// 2010.06.23-06.28 Spring Embedder Model
package org.misue.scalasample.gd.sample1

import java.awt.Graphics2D
import java.awt.Color
import java.awt.geom.Point2D

class BasicGraph(
    val nodes: List[Int],
    val edges: List[Tuple2[Int, Int]],
    val adj: Array[Array[Int]]) 
    {
    
    val c1: Double = 10.0 // 2.0 by P. Eades
    val c2: Double = 20.0 // 1.0 by P. Eades
    val c3: Double = 1.0 // 1.0 by P. Eades
    val c4: Double = 0.5 // 0.1 by P. Eades
    val thres: Double = 0.001
    val zero: Point2D = new Point2D.Double(0.0, 0.0)

//    private val nodeSize = nodes.size

    private var nodePos = nodes.map(n => new Point2D.Double(randomPos, randomPos))
    println("nodes = " + nodes)
    println("edges = " + edges)
    // end of constructor

    private def randomPos() = math.random * 10

    def draw(g: Graphics2D, width: Int, height: Int): Unit = {
        val x0: Int = width / 2
        val y0: Int = height / 2

        g.setColor(new Color(0, 128, 200))
        for ((n1, n2) <- edges) {
            val p1: Point2D = nodePos(n1)
            val p2: Point2D = nodePos(n2)
            g.drawLine(x0 + p1.getX.toInt, y0 + p1.getY.toInt, x0 + p2.getX.toInt, y0 + p2.getY.toInt)
        }

        nodes.foreach(n => {
            val p: Point2D = nodePos(n)
            val x: Int = x0 - 6 + p.getX.toInt
            val y: Int = y0 - 6 + p.getY.toInt
            g.setColor(new Color(255, 0, 200))
            g.fillOval(x, y, 12, 12)
            g.setColor(new Color(64, 64, 64))
            g.drawString("" + n, x, y)
        })
    }

    def findStableState(): Unit = {
        if (relax() > thres) findStableState()
    }

    def relax(): Double = {
        def maxForce(forces: List[Point2D], fmax: Double): Double = {
            forces match {
                case List() => fmax
                case f :: rest => maxForce(rest, math.max(fmax, math.sqrt(f.getX * f.getX + f.getY * f.getY)))
            }
        }

        val forces: List[Point2D] = nodes.map(n1 => computeForce(n1))
        val max = maxForce(forces, 0.0)
        if (max > thres) {
            nodePos = nodePos.zip(forces).map(pair => {
                val (p, f) = pair
                new Point2D.Double(p.getX + c4 * f.getX, p.getY + c4 * f.getY)
            })
        }
        max
    }

    private def computeForce(n1: Int): Point2D = {
        nodes.map(n2 => {
            if (n1 == n2)
                zero
            else if ((adj(n1)(n2) > 0))
                springForce(nodePos(n1), nodePos(n2))
            else
                repulsiveForce(nodePos(n1), nodePos(n2))
        }).reduceLeft(
            (f1, f2) => new Point2D.Double(f1.getX + f2.getX, f1.getY + f2.getY))
    }

    private def springForce(p1: Point2D, p2: Point2D): Point2D = {
        val d: Double = adjustedDist(p1, p2)
        val f: Double = c1 * math.log(d / c2)
        val fx: Double = f * (p2.getX - p1.getX) / d
        val fy: Double = f * (p2.getY - p1.getY) / d
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

object BasicGraph {

    def create(filename: String): BasicGraph = {
        val nodeSize = filename.toInt
        val nodes = createNodeList(nodeSize)
        val edges = createBinTree(nodeSize)

        val adj = new Array[Array[Int]](nodeSize)
        for (i <- 0 until nodeSize) adj(i) = new Array[Int](nodeSize)
        for ((n1, n2) <- edges) {
            adj(n1)(n2) = 1
            adj(n2)(n1) = 1
        }
        new BasicGraph(nodes, edges, adj)
    }

    private def createNodeList(n: Int): List[Int] = {
        def createIntList(n1: Int, n2: Int): List[Int] = {
            if (n1 > n2)
                List()
            else
                n1 :: createIntList(n1 + 1, n2)
        }
        createIntList(0, n - 1)
    }

    private def createBinTree(n: Int): List[Tuple2[Int, Int]] = {
        if (n <= 0)
            List()
        else
            ((n - 2) / 2, n - 1) :: createBinTree(n - 1)
    }

}

