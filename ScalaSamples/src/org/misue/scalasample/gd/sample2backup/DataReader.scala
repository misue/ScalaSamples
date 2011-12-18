// All Rights Reserved. Copyright (C) Kazuo Misue (2010-2011)
package org.misue.scalasample.gd.sample2backup

import java.io.File
import scala.io.Source

object DataReader {

    def read(filename: String): Graph =
        if (filename != null) read(new File(filename)) else null

    def read(file: File): Graph = {
        val labelPairs: List[List[String]] = Source.fromFile(file).getLines.map((line) => {
            val tokens = line.split(",")
            tokens.length match {
                case 2 => List(tokens(0), tokens(1))
                case _ => List()
            }
        }).toList.filterNot(_.isEmpty)
        val nodeMap = labelPairs.flatten.foldLeft[Map[String,Node]](Map())((map, label) => {
            map.get(label) match {
                case Some(node) => map
                case None => map + (label -> new Node(label))
            }
        })
        val nodeSet: List[Node] = nodeMap.values.toList
        val edgeSet: List[Edge] = labelPairs.map((pair) => new Edge(nodeMap.get(pair.head).get, nodeMap.get(pair.last).get))        
        new Graph(nodeSet, edgeSet)
    }

}
