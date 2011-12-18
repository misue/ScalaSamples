// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.graphdraw.chart.spiralchart

import scala.swing.Component
import org.misue.graphdraw.chart.Mediator
import org.misue.graphdraw.chart.basic._
import java.util.Calendar
import org.misue.graphdraw.chart.util.DateUtil

class SpiralChartMaker extends ChartMaker {
  override def createChart(graph: Graph, mediator: Mediator): Chart = SpiralChart.create(graph, mediator)
  override def createControl(mediator: Mediator): swing.Component = SpiralChart.createControl(mediator)
}
