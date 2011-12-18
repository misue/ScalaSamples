// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample6a

import swing._
import java.util._
import java.io.File

class SimpleApplet6 extends Applet {
    
        val cmdname = "SampleApplet #6a"
    val canvasSize: Dimension = new Dimension(800, 600)
    
    object ui extends UI with Reactor {
        //        def init() = {
        //            val button = new Button("Press here!")
        //            val text = new TextArea("Java Version: " +
        //                System.getProperty("java.version") + "\n")
        //            listenTo(button)
        //            reactions += {
        //                case ButtonClicked(_) => text.text += "Button Pressed!\n"
        //                case _ =>
        //            }
        //            contents = new BoxPanel(Orientation.Vertical) { contents.append(button, text) }
        //        }
            
//      this.setSize((int)canvasSize.getWidth(), (int)canvasSize.getHeight());
//		
//		ChartPanel canvas = new ChartPanel();
//		this.getContentPane().add(canvas);
//		Mediator mediator = new Mediator(canvas, canvasSize);
//		
//		initFilename = getParameter("file");
//		if (initFilename != null) mediator.readData(initFilename);
//
//		// Set up UI
//		JMenuBar menuBar = new JMenuBar();
//		this.setJMenuBar(menuBar);	
//		FileMenuFactory menuFactory = new FileMenuFactory(mediator);
//		JMenu menu = menuFactory.createMenu("File");
//		menuBar.add(menu);
            
            
        def init(): Unit = {
            val chartPanel = new ChartPanel()
            chartPanel.preferredSize = canvasSize
            val mediator = new Mediator(chartPanel, canvasSize)
            mediator.start()
            val panel = top(mediator, chartPanel)
//            contents = panel
            if (panel.size == new Dimension(0, 0)) panel.pack()
            panel.visible = true
//            if (args.length > 0) mediator ! ("read", args(0))
        }

        def top(mediator: Mediator, panel: Component) = new MainFrame() {
            title = cmdname

            // Setup Menu
            val fileMenuFactory = new FileMenuFactory(mediator)

            menuBar = new MenuBar {
                contents += fileMenuFactory.createMenu("File")
            }

            contents = panel
        }
    }
} 




//object SampleApplet6 extends Applet {
//    val cmdname = "SampleApplet #6a"
//    val canvasSize: Dimension = new Dimension(800, 600)
//
//    override def startup(args: Array[String]): Unit = {
//        val chartPanel = new ChartPanel()
//        chartPanel.preferredSize = canvasSize
//        val mediator = new Mediator(chartPanel, canvasSize)
//        mediator.start()
//        val panel = top(mediator, chartPanel)
//        if (panel.size == new Dimension(0, 0)) panel.pack()
//        panel.visible = true
//        if (args.length > 0) mediator ! ("read", args(0))
//    }
//
//    def top(mediator: Mediator, panel: Component) = new MainFrame() {
//        title = cmdname
//
//        // Setup Menu
//        val fileMenuFactory = new FileMenuFactory(mediator)
//
//        menuBar = new MenuBar {
//            contents += fileMenuFactory.createMenu("File")
//        }
//
//        contents = panel
//    }
//}
