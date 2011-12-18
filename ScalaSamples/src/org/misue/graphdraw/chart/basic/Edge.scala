// All Rights Reserved. Copyright (C) Kazuo Misue (2010)
package org.misue.graphdraw.chart.basic

import java.util.Calendar
//import java.awt.Graphics2D
//import java.awt.Color
//import org.misue.graphdraw.chart.util.DateUtil

class Edge(val tail: Node, val head: Node, val date: Calendar) {  
  var link: AbstractLink = null
}
