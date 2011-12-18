// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.gd.sample2backup

import java.awt.Graphics2D
//import scala.actors.Actor
import java.awt.geom.Point2D

class Graph(val nodeSet: List[Node], val edgeSet: List[Edge]) {

//    def draw(g2: Graphics2D, posList: List[Tuple2[Double, Double]]): Unit = {
//        nodeSet.zip(posList).foreach((pair) => pair._1.draw(g2, pair._2))
//    }
    def draw(g2: Graphics2D, posList: List[Point2D]): Unit = {
//        nodeSet.zip(posList).foreach((pair) => pair._1.draw(g2, pair._2))
        val map: Map[Node,Point2D] = nodeSet.zip(posList).toMap[Node,Point2D]
        for ((node, pos) <- map) node.draw(g2, pos)
        edgeSet.foreach(_.draw(g2, map))
    }

}

object Graph {
    def create(nodeSet: List[Node], edgeSet: List[Edge]): Graph =
        new Graph(nodeSet, edgeSet)
}
