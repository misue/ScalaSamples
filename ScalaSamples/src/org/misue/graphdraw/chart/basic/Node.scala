// All Rights Reserved. Copyright (C) Kazuo Misue (2010-2011)
package org.misue.graphdraw.chart.basic

import java.util.Calendar

class Node(val label: String) {
  var outEdges: List[Edge] = List()
  var inEdges: List[Edge] = List()
  var mark: AbstractMark = null

  private def addEdge(edge: Edge, edgeList: List[Edge]): List[Edge] = {
    edgeList match {
      case List() => List(edge)
      case edge1 :: rest if (edge1.date.before(edge.date)) => edge1 :: addEdge(edge, rest)
      case edge1 :: rest => edge :: edgeList
    }
  }
  
  def addTail(edge: Edge): Unit = { outEdges = addEdge(edge, outEdges) }
  def addHead(edge: Edge): Unit = { inEdges = addEdge(edge, inEdges) } 
  
}