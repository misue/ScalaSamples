// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample09a

import java.io.File
import java.awt.Color

abstract class Message
//case object LayoutChart extends Message
case object AddItem extends Message
case class SetItemColor(color: Color) extends Message
case class ReadFile(file: File) extends Message
case class ReadFile2(filename: String) extends Message
case object SendDefaultColor extends Message