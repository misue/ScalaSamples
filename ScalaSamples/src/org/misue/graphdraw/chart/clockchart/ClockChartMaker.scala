// All Rights Reserved. Copyright (C) Kazuo Misue (2010-2011)
package org.misue.graphdraw.chart.clockchart

import java.awt.Color
import java.util.Calendar
import org.misue.graphdraw.chart.basic._
import org.misue.graphdraw.chart.util.DateUtil
import org.misue.graphdraw.chart.Mediator

class ClockChartMaker extends ChartMaker {

  override def createChart(graph: Graph, mediator: Mediator): Chart = ClockChart.create(graph, mediator)
  
//  import scala.swing._
//  import scala.swing.event._
  
//  protected def createColorControl(mediator: Mediator): swing.Component = {
//  
//    val buttonTail = new RadioButton("Tail") {
//      listenTo(this)
//      reactions += {
//        case ButtonClicked(button) => {
//            showNode = showTail
//          }            
//      }
//    }
    
//    val buttonHead = new RadioButton("Head") {
//      listenTo(this)
//      reactions += {
//        case ButtonClicked(button) => {
//            showNode = showHead
//          }
//      }
//    }
    
//    val buttonBoth = new RadioButton("Both") {
//      selected = true
//      listenTo(this)
//      reactions += {
//        case ButtonClicked(button) => {
//            showNode = showTail | showHead
//          }
//      }
//    }
    
//    val buttonFreq = new ToggleButton("Freq.") {
//      selected = false
//      listenTo(this)
//      reactions += {
//        case ButtonClicked(button) => {
//            if (button.selected) {
//              value = freq2value              
//            } else {
//              value = none2value
//            }
//          }
//      }
//    }
    
//    val buttonGroup = new ButtonGroup
//    buttonGroup.buttons += buttonTail
//    buttonGroup.buttons += buttonHead
//    buttonGroup.buttons += buttonBoth
    
    
//    val freqSlider = new Slider {
//        min = 0
//        max = 100
//        value = 100 - (freqConst * 10).toInt
//        majorTickSpacing = 10
//        minorTickSpacing = 5
//        paintTicks = true
//        paintLabels = true
//        labels = Map(0 -> new Label("0"),
//                     50 -> new Label("5"),
//                     100 -> new Label("10"))        
//        listenTo(this)
//        reactions += {
//          case ValueChanged(slider) => {
//                freqConst = (100 - slider.asInstanceOf[Slider].value) / 10.0
//            }
//        }          
//      }
    
//    val panel = new BoxPanel(Orientation.Vertical) {
//      contents += new BoxPanel(Orientation.Horizontal) {
//        contents += new Label("Show:")
//        contents += buttonTail
//        contents += buttonHead
//        contents += buttonBoth
//      }
//      contents += new BoxPanel(Orientation.Vertical) {
//        contents += new BoxPanel(Orientation.Horizontal) {
//          contents += new Label("Value:")
//          contents += buttonFreq
//        }
//        contents += freqSlider
//      }
//    }
//    panel
//  }

//  override def createControl(mediator: Mediator): swing.Component = {
//    new BoxPanel(Orientation.Vertical) { 
////      contents += createColorControl(mediator)
//    }    
//  }

}
