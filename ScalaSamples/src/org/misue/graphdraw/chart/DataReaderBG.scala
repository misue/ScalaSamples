// All Rights Reserved. Copyright (C) Kazuo Misue (2010-2011)
package org.misue.graphdraw.chart

import scala.util.matching.Regex
import java.io.File
import java.util.Calendar
import org.misue.graphdraw._
import org.misue.graphdraw.chart.basic.Graph

object DataReaderBG {

  def readData(file: File): Graph = {
    import scala.io.Source
    println("Reading file (with Timestamp): " + file)
    
    val graph = new Graph()
    
    val timestamp3 = new Regex("""(\d\d\d\d)-(\d\d)-(\d\d)T(\d\d):(\d\d):(\d\d)([-+]\d\d:\d\d)""")
    
    def addEdge3(tailLabel: String, headLabel: String, field3: String): Unit = {
      field3 match {
        case timestamp3(yyyy, mm, dd, hour, min, sec, loc) => {
            val cal = Calendar.getInstance
            cal.set(yyyy.toInt, mm.toInt - 1, dd.toInt, hour.toInt, min.toInt, sec.toInt)
            graph.addEdge(tailLabel, headLabel, cal)
        }
        case _ => {
        }
      }      
    }    
    
    val timestamp8 = new Regex("""(\d+):(\d\d):(\d\d)""")
    
    def addEdge8(tailLabel: String, headLabel: String, date: String, time: String): Unit = {
      val day: Int = date.toInt % 100
      val month: Int = date.toInt / 100
      time match {
        case timestamp8(hour, min, sec) => {
            val cal = Calendar.getInstance
            cal.set(2011, month - 1, day, hour.toInt, min.toInt, sec.toInt)
            graph.addEdge(tailLabel, headLabel, cal)
        }
        case _ => {
        }
      }      
    }
    
    

    for (line <- Source.fromFile(file).getLines) {
      val tokens = line.split(",")
      tokens.length match {
        case 3 => addEdge3(tokens(0), tokens(1), tokens(2))
        case 8 => addEdge8(tokens(0), tokens(7), tokens(2), tokens(3))
        case _ =>
      }
    }
    graph.edges = graph.edges.reverse
    println("Reading file finished.")
    graph
  }
  
}
