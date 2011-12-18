// All Rights Reserved. Copyright (C) Kazuo Misue (2010-2011)
package org.misue.graphdraw.chart.unused

import java.util.Calendar
import org.misue.graphdraw.chart.basic._

class Graph {
  var nodes: List[Node] = List()
  var edges: List[Edge] = List()
  var firstDay: Calendar = null
  var finalDay: Calendar = null

  def findNode(label: String): Option[Node] = findNode(label, nodes)

  private def findNode(label: String, nodes: List[Node]): Option[Node] = {
    nodes match {
      case List() => None
      case node :: rest if (label.equals(node.label)) => Some(node)
      case node :: rest => findNode(label, rest)
    }
  }
  
  private def addNode(label: String): Node = {
    val n: Node = new Node(label)
    nodes = n :: nodes
    n
  }

  def findCreateNode(label: String): Node = {
    findNode(label) match {
      case Some(n) => n
      case None => addNode(label)
    }
  }
  
  def addEdge(tailLabel: String, headLabel: String, date: Calendar): Edge = {
    val tail = findCreateNode(tailLabel)
    val head = findCreateNode(headLabel)    
   // arrowing multiple edges
    val e = new Edge(tail, head, date)
    tail.addTail(e)
    head.addHead(e)
    edges = e :: edges
    val (newFirstDay, newFinalDay) = adjustRange(date, firstDay, finalDay)
    firstDay = newFirstDay
    finalDay = newFinalDay    
    e
  }  

  def adjustRange(date: Calendar, firstDay: Calendar, finalDay: Calendar): Tuple2[Calendar,Calendar] = {
    val day0: Calendar = date.clone.asInstanceOf[Calendar]
    day0.set(Calendar.HOUR_OF_DAY, 0)
    day0.set(Calendar.MINUTE, 0)
    day0.set(Calendar.SECOND, 0)
    val day1: Calendar = day0.clone.asInstanceOf[Calendar]
    day1.add(Calendar.DATE, 1)
    val newFirstDay = if (day0.after(firstDay)) firstDay else day0
    val newFinalDay = if (day1.before(finalDay)) finalDay else day1
    (newFirstDay, newFinalDay)
  }  

}
