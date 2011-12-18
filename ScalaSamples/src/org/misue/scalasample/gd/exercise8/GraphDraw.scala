// All Rights Reserved. Copyright (C) Kazuo Misue (2010)
// 2010.06.26 Spring Model with Mouse-event handling and Buttons
// 2010.06.36 This can read CSV files.
package org.misue.scalasample.gd.exercise8

import swing._
import java.util._

object GraphDraw extends SwingApplication {

  val mediator: Mediator = new Mediator

  override def startup(args: Array[String]) {

    val panel = top

    if (panel.size == new Dimension(0,0)) panel.pack()
      panel.visible = true
      println("startup done.")
      val timer: javax.swing.Timer =
        new javax.swing.Timer(10,
          new java.awt.event.ActionListener {
            override def actionPerformed(evt: java.awt.event.ActionEvent) {
//              panel.repaint()
              mediator.relax()
            }
          })
      timer.start()
    }

  def top = new MainFrame {

    import swing.event._

    val menuFile = new Menu("File")
    menuFile.contents += new MenuItem(Action("Open...") {
        mediator.openFile()
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

    contents = new BoxPanel(Orientation.Vertical) {

      contents += mediator.panel

      contents += new BoxPanel(Orientation.Horizontal) {
        contents += new Button {
          text = "Scramble"
          listenTo(this)
          reactions += {
            case ButtonClicked(button) => mediator.scramble()
          }
        }

        contents += new Button {
          text = "Shake"
          listenTo(this)
          reactions += {
            case ButtonClicked(button) => mediator.shake()
          }
        }
      }
    }
  }

}


//class GraphDraw extends Applet {
//abstract class GraphDraw extends SwingApplication {

//	val panel0: JPanel = new JPanel()
//	panel0.setLayout(new BorderLayout())
//	val panel: GraphPanel = new GraphPanel(this)
//	panel0.add(panel)
//	var graph: Graph = panel.getGraph()
//
//	var controlPanel: Panel
////	val scramble = new Button("Scramble")
//	val shake = new Button("Shake")
//	val stress = new Checkbox("Stress")
//
//	override def init(): Unit = {
//
//		setLayout(new BorderLayout());
//
//		panel = new GraphPanel(this);
//		graph = panel.getGraph()
//
//		add("Center", panel);
//		controlPanel = new Panel();
//		add("South", controlPanel);
//
//		val scramble = new Button("Scramble") {
//			graph.scramble(getSize())
//		}
//
//		controlPanel.add(scramble);
//		scramble.addActionListener(new ActionCommand() {
//			def actionPerformed(e: ActionEvent): Unit = {
//				graph.scramble(getSize())
//			}
//		})
//
//		controlPanel.add(shake);
//		shake.addActionListener(new ActionCommand() {
//			public void actionPerformed(ActionEvent e) {
//				graph.shake();
//			}
//		});
//
//		controlPanel.add(stress);
//		stress.addItemListener(new ItemCommand() {
//			public void itemStateChanged(ItemEvent e) {
//				panel.stress = e.getStateChange() == ItemEvent.SELECTED;
//			}
//		});
//
//		String edges = getParameter("edges");
//		for (StringTokenizer t = new StringTokenizer(edges, ","); t.hasMoreTokens();) {
//			String str = t.nextToken();
//			int i = str.indexOf('-');
//			if (i > 0) {
//				int len = 50;
//				int j = str.indexOf('/');
//				if (j > 0) {
//					len = Integer.valueOf(str.substring(j + 1)).intValue();
//					str = str.substring(0, j);
//				}
//				graph.addEdge(str.substring(0, i), str.substring(i + 1), len);
//			}
//		}
//		Dimension d = getSize();
//		String center = getParameter("center");
//		if (center != null) {
//			graph.fixNode(center, d.width / 2, d.height / 2);
//		}
//	}
//
//	override def destroy(): Unit = {
//		remove(panel)
//		remove(controlPanel)
//	}
//
//	override def start(): Unit = panel.start()
//
//	override def stop(): Unit = panel.stop()
//
//	def getAppletInfo(): String = "Title: GraphDiff \nAuthor: Kazuo MISUE"
//
////	def getParameterInfo(): List[String] = List("edges",
////						"delimited string",
////						"A comma-delimited list of all the edges.  It takes the form of 'C-N1,C-N2,C-N3,C-NX,N1-N2/M12,N2-N3/M23,N3-NX/M3X,...' where C is the name of center node (see 'center' parameter) and NX is a node attached to the center node.  For the edges connecting nodes to each other (and not to the center node) you may (optionally) specify a length MXY separated from the edge name by a forward slash." },
////				{ "center", "string", "The name of the center node." } };
////		return info;
////	}
//
//	//====================================================================
//	// Inner Class: Command
//	//====================================================================
//
//	abstract class ActionCommand with ActionListener {
//
//		public ActionCommand() {
//		}
//
//		public abstract void actionPerformed(ActionEvent evt);
//	}
//
//	abstract class ItemCommand with ItemListener {
//
//		public ItemCommand() {
//		}
//
//		public abstract void itemStateChanged(ItemEvent e);
//	}
//}
