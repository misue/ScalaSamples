// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.graphdraw.chart.clockchart

import java.util.Calendar
import java.awt.Color
import java.awt.Graphics2D
import org.misue.graphdraw.chart.basic._
//import java.awt.Font
//import java.awt.FontMetrics

class ClockLink(val edge: Edge) extends AbstractLink {
  // Constructor
  edge.link = this
}

object ClockLink {
  def create(edge: Edge): ClockLink = new ClockLink(edge)
}
