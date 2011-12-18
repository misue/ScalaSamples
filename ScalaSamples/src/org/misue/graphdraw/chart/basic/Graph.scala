// All Rights Reserved. Copyright (C) Kazuo Misue (2010-2011)
package org.misue.graphdraw.chart.basic

import java.util.Calendar

class Graph {
  var nodes: List[Node] = List()
  var edges: List[Edge] = List()
  var firstDate: Calendar = null
  var finalDate: Calendar = null

  def firstDay: Calendar = {
    if (firstDate == null) null else { 
      val day: Calendar = firstDate.clone.asInstanceOf[Calendar]
      day.set(Calendar.HOUR_OF_DAY, 0)
      day.set(Calendar.MINUTE, 0)
      day.set(Calendar.SECOND, 0)
      day
    }
  }

  def finalDay: Calendar = {
    if (finalDate == null) null else {
      val day: Calendar = finalDate.clone.asInstanceOf[Calendar]
      day.add(Calendar.DATE, 1)
      day.set(Calendar.HOUR_OF_DAY, 0)
      day.set(Calendar.MINUTE, 0)
      day.set(Calendar.SECOND, 0)
      day
    }
  }

  private def findNode(label: String): Option[Node] = findNode(label, nodes)

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

  private def findCreateNode(label: String): Node = {
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
    if ((firstDate == null) || firstDate.after(date)) firstDate = date
    if ((finalDate == null) || finalDate.before(date)) finalDate = date
    e
  }

}
