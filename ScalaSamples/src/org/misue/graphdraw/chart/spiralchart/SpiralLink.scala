// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.graphdraw.chart.spiralchart

import java.util.Calendar
import java.awt.Color
import java.awt.Graphics2D
import org.misue.graphdraw.chart.basic._
//import java.awt.Font
//import java.awt.FontMetrics

class SpiralLink(val edge: Edge) extends AbstractLink {
  // Constructor
  edge.link = this
}

object SpiralLink {
  def create(edge: Edge): SpiralLink = new SpiralLink(edge)
}
