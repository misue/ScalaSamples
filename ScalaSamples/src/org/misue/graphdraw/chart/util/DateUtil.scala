// All Rights Reserved. Copyright (C) Kazuo Misue (2010)
package org.misue.graphdraw.chart.util

import java.util.Calendar
import swing._


object DateUtil {
  
  // Period Code
  val oneHour: Int = 1
  val oneDay: Int = 2
  val oneWeek: Int = 3
  val oneMonth: Int = 4
  
  val prec: Int = 1000 * 60 * 30
  def date2int(date: Calendar): Int = (date.getTimeInMillis / prec).toInt  
  
  def inRange(date: Calendar, beginDate: Calendar, endDate: Calendar): Boolean = 
    ((beginDate == null) || date.after(beginDate)) && ((endDate == null) || !date.after(endDate))
  
  def inRange(date: Calendar, dateRange: Tuple2[Calendar, Calendar]): Boolean = 
    inRange(date, dateRange._1, dateRange._2)

  
  // Time Window Size
  val tWinSizeLabels = Map(
          1 -> new Label("1h"),
          2 -> new Label("3h"),
          3 -> new Label("6h"),
          4 -> new Label("12h"),
          5 -> new Label("1d"),
          6 -> new Label("1w"),
          7 -> new Label("1m"),
          8 -> new Label("inf."))
  
  def firstDate(lastDate: Calendar, tWinSizeCode: Int): Calendar = {
    val date = lastDate.clone.asInstanceOf[Calendar]
    tWinSizeCode match {
      case 1 => date.add(Calendar.HOUR_OF_DAY, -1)
      case 2 => date.add(Calendar.HOUR_OF_DAY, -3)
      case 3 => date.add(Calendar.HOUR_OF_DAY, -6)
      case 4 => date.add(Calendar.HOUR_OF_DAY, -12)
      case 5 => date.add(Calendar.DAY_OF_MONTH, -1) //date.add(Calendar.HOUR_OF_DAY, -24)
      case 6 => date.add(Calendar.DAY_OF_MONTH, -7) //date.add(Calendar.HOUR_OF_DAY, -48)
      case 7 => date.add(Calendar.MONTH, -1)
//      case 7 => date.add(Calendar.HOUR_OF_DAY, -168)
//      case 7 => null
      case _ => null
    }
    if (tWinSizeCode < 8) date else null
  }
  
  val incStepLabels = Map(
          1 -> new Label("5m"),
          2 -> new Label("10m"),
          3 -> new Label("30m"),
          4 -> new Label("1h"),
          5 -> new Label("3h"),
          6 -> new Label("6h"),
          7 -> new Label("12h"),
          8 -> new Label("24h"))
  
  def incDate(date: Calendar, timeStepCode: Int, direction: Int): Calendar = {
    val newDate = date.clone.asInstanceOf[Calendar]
//    println("before[" + timeStepCode + "]=" + String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM", newDate))
    if (direction != 0) {
      timeStepCode match {
        case 1 => newDate.add(Calendar.MINUTE, 5 * direction)
        case 2 => newDate.add(Calendar.MINUTE, 10 * direction)
        case 3 => newDate.add(Calendar.MINUTE, 30 * direction)
        case 4 => newDate.add(Calendar.HOUR_OF_DAY, 1 * direction)
        case 5 => newDate.add(Calendar.HOUR_OF_DAY, 3 * direction)
        case 6 => newDate.add(Calendar.HOUR_OF_DAY, 6 * direction)
        case 7 => newDate.add(Calendar.HOUR_OF_DAY, 12 * direction)
        case 8 => newDate.add(Calendar.HOUR_OF_DAY, 24 * direction)
        case _ =>
      }
//      println(" after[" + timeStepCode + "]=" + String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM", newDate))

    }
    newDate
  }  

}
