// All Rights Reserved. Copyright (C) Kazuo Misue (2010)
package org.misue.graphdraw.chart

import javax.swing.JFileChooser
import scala.swing.RadioButton
import swing._
import swing.event._
import org.misue.graphdraw.chart.util.DateUtil

class ChartFrame(val mediator: Mediator) extends MainFrame {

  preferredSize = new Dimension(800, 600)

  val menuFile = new Menu("File")
  menuFile.contents += new MenuItem(Action("Open...") {
    mediator.openFile()
  })

  menuFile.contents += new MenuItem(Action("Save Image ...") {
    //      mediator.saveImage()
  })

  val menuInfo = new Menu("Info")
  menuInfo.contents += new CheckMenuItem("") {
    action = new Action("Show Info") {
      override def apply(): Unit = {
        mediator.showInfo()
      }
    }
  }

  menuBar = new MenuBar {
    contents += menuFile
    contents += menuInfo
  }

  val splitPane = new SplitPane(Orientation.Vertical) {
    oneTouchExpandable = true
    continuousLayout = false
    dividerSize = 5
    dividerLocation = 550
  }
  contents = splitPane

  splitPane.rightComponent = new BoxPanel(Orientation.Vertical) {

    val timeSlider = new Slider {
      listenTo(this)
      reactions += {
        case ValueChanged(slider) => {
          mediator.dateval = (slider.asInstanceOf[Slider].value)
        }
      }
    }
    mediator.entryTimeSlider(timeSlider)
    contents += mediator.panel
    contents += timeSlider

  }


  splitPane.leftComponent = new BoxPanel(Orientation.Vertical) {

    contents += new BoxPanel(Orientation.Horizontal) {

      contents += new Button {
        text = "<"
        listenTo(this)
        reactions += {
          case ButtonClicked(button) => mediator.playBackward()
        }
      }

      contents += new Button {
//        selected = true
        text = "P"
        listenTo(this)
        reactions += {
          case ButtonClicked(button) => mediator.playPause()
        }
      }

      contents += new Button {
        text = ">"
        listenTo(this)
        reactions += {
          case ButtonClicked(button) => mediator.playForward()
        }
      }

    }
    
    // Period Buttons
    val buttonOneHour = new RadioButton("Hour") {
      selected = mediator.periodCode == DateUtil.oneHour
      listenTo(this)
      reactions += {
        case ButtonClicked(button) => {
            mediator.periodCode = DateUtil.oneHour
          }
      }
    }
    
    val buttonOneDay = new RadioButton("Day") {
      selected = mediator.periodCode == DateUtil.oneDay
      listenTo(this)
      reactions += {
        case ButtonClicked(button) => {
            mediator.periodCode = DateUtil.oneDay
          }
      }
    }
    
    val buttonOneWeek = new RadioButton("Week") {
      selected = mediator.periodCode == DateUtil.oneWeek
      listenTo(this)
      reactions += {
        case ButtonClicked(button) => {
            mediator.periodCode = DateUtil.oneWeek
          }
      }
    }

    val buttonOneMonth = new RadioButton("Month") {
      selected = mediator.periodCode == DateUtil.oneMonth
      listenTo(this)
      reactions += {
        case ButtonClicked(button) => {
            mediator.periodCode = DateUtil.oneMonth
          }
      }
    }

    val buttonPeriodGroup = new ButtonGroup
    buttonPeriodGroup.buttons += buttonOneHour
    buttonPeriodGroup.buttons += buttonOneDay
    buttonPeriodGroup.buttons += buttonOneWeek
    buttonPeriodGroup.buttons += buttonOneMonth    
    
    contents += new BoxPanel(Orientation.Vertical) {
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += new Label("Period:")
        contents += buttonOneHour
        contents += buttonOneDay
        contents += buttonOneWeek
        contents += buttonOneMonth
      }
    }

    // Time-Window Size Slider
    contents += new BoxPanel(Orientation.Vertical) {
      contents += new Label("Time Window Size")
      contents += new Slider {
        min = 1
        max = 8
        value = mediator.tWinSizeCode
        majorTickSpacing = 1
        //        minorTickSpacing = 1
        paintTicks = true
        paintLabels = true
        snapToTicks = true
        labels = DateUtil.tWinSizeLabels
        listenTo(this)
        reactions += {
          case ValueChanged(slider) => {
            mediator.tWinSizeCode = slider.asInstanceOf[Slider].value
          }
        }
      }
    }

    // Time-Step Slider
    contents += new BoxPanel(Orientation.Vertical) {
      contents += new Label("Time Step")
      contents += new Slider {
        min = 1
        max = 8
        value = mediator.timeStepCode
        majorTickSpacing = 1
        //        minorTickSpacing = 1
        paintTicks = true
        paintLabels = true
        snapToTicks = true
        labels = DateUtil.incStepLabels
        listenTo(this)
        reactions += {
          case ValueChanged(slider) => {
            mediator.timeStepCode = slider.asInstanceOf[Slider].value
          }
        }
      }
    }
    
    // Visible Nodes Slider
    contents += new BoxPanel(Orientation.Vertical) {
      contents += new Label("Visible Nodes (min freq.)")
      contents += new Slider {
        min = 0
        max = 100
        value = mediator.visibleMinFreq
        majorTickSpacing = 10
        minorTickSpacing = 5
        paintTicks = true
        paintLabels = true
//        snapToTicks = true
//        labels = DateUtil.incStepLabels
        listenTo(this)
        reactions += {
          case ValueChanged(slider) => {
            mediator.visibleMinFreq = slider.asInstanceOf[Slider].value
          }
        }
      }
    }    
    
    // Visible Labels Slider
    contents += new BoxPanel(Orientation.Vertical) {
      contents += new Label("Visible Labels (min freq.)")
      contents += new Slider {
        min = 0
        max = 20
        value = mediator.labelMinFreq
        majorTickSpacing = 5
        minorTickSpacing = 1
        paintTicks = true
        paintLabels = true
//        snapToTicks = true
//        labels = DateUtil.incStepLabels
        listenTo(this)
        reactions += {
          case ValueChanged(slider) => {
            mediator.labelMinFreq = slider.asInstanceOf[Slider].value
          }
        }
      }
    }    

    //    val buttonGroup = new ButtonGroup
    //    contents += new BoxPanel(Orientation.Horizontal) {
    //      val radioButtonA = new RadioButton("a") { selected = true }
    //      val radioButtonB = new RadioButton("b")
    //      val radioButtonC = new RadioButton("c")
    //      contents += radioButtonA
    //      contents += radioButtonB
    //      contents += radioButtonC
    //      buttonGroup.buttons += radioButtonA
    //      buttonGroup.buttons += radioButtonB
    //      buttonGroup.buttons += radioButtonC    
    //    }

    //    val panel = new BoxPanel(Orientation.Vertical) {
    //      contents += new Label("Sampling")        
    //      contents += new Slider {
    //        min = 1
    //        max = 6
    //        value = mediator.sampling
    //        majorTickSpacing = 1
    ////        minorTickSpacing = 1
    //        paintTicks = true
    //        paintLabels = true
    //        snapToTicks = true
    //        labels = Map(1 -> new Label("1/2"),
    //                     2 -> new Label("1h"),
    //                     3 -> new Label("3h"),
    //                     4 -> new Label("6h"),
    //                     5 -> new Label("12h"),
    //                     6 -> new Label("24h"))        
    //        listenTo(this)
    //        reactions += {
    //          case ValueChanged(slider) => {
    //                mediator.sampling = slider.asInstanceOf[Slider].value
    //            }
    //        }          
    //      }
    //    }
    //    
    //    val tabbedpane = new TabbedPane {
    //      //these next two lines refer to the TabbedPane singleton object, not the TabbedPane class
    //      pages += new TabbedPane.Page("Page 1", panel)
    ////      pages += new TabbedPane.Page("Page 2", panel)
    //    }
    //    
    //    contents += tabbedpane  
    
    val buttonTail = new RadioButton("Tail") {
      selected = mediator.isTailShown & !mediator.isHeadShown
      listenTo(this)
      reactions += {
        case ButtonClicked(button) => {
            mediator.setShowOnlyTail
          }            
      }
    }
    
    val buttonHead = new RadioButton("Head") {
      selected = mediator.isHeadShown & !mediator.isTailShown
      listenTo(this)
      reactions += {
        case ButtonClicked(button) => {
            mediator.setShowOnlyHead
          }
      }
    }
    
    val buttonBoth = new RadioButton("Both") {
      selected = mediator.isTailShown & mediator.isHeadShown
//      selected = true
      listenTo(this)
      reactions += {
        case ButtonClicked(button) => {
            mediator.setShowBothNode
          }
      }
    }    

    val buttonGroup = new ButtonGroup
    buttonGroup.buttons += buttonTail
    buttonGroup.buttons += buttonHead
    buttonGroup.buttons += buttonBoth
    
    contents += new BoxPanel(Orientation.Vertical) {
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += new Label("Show:")
        contents += buttonTail
        contents += buttonHead
        contents += buttonBoth
      }
    }
    
    // Animation frames
    contents += new BoxPanel(Orientation.Vertical) {
      contents += new Label("Animation Frames")
      contents += new Slider {
        min = 0
        max = 60
        value = mediator.intervals
        majorTickSpacing = 10
        minorTickSpacing = 1
        paintTicks = true
        paintLabels = true
//        snapToTicks = true
//        labels = DateUtil.incStepLabels
        listenTo(this)
        reactions += {
          case ValueChanged(slider) => {
            mediator.intervals = slider.asInstanceOf[Slider].value
          }
        }
      }
    }   
    
    contents += mediator.createChartMakerControl()
  }

  class MySlider extends Slider {
    min = 0
    max = 100
    majorTickSpacing = 25
    minorTickSpacing = 5
    paintTicks = true
    paintLabels = true
  }

}

