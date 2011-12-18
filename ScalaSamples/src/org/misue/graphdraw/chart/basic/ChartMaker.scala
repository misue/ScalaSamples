// All Rights Reserved. Copyright (C) Kazuo Misue (2010)
package org.misue.graphdraw.chart.basic

import java.util.Calendar
import scala.swing._  
import org.misue.graphdraw.chart.Mediator

trait ChartMaker {
  def createChart(graph: Graph, mediaotr: Mediator): Chart
  def createControl(mediator: Mediator): swing.Component = new BoxPanel(Orientation.Vertical) { }
}
