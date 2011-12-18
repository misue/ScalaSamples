// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample8a

import java.io.File

abstract class Message
//case object Layout extends Message
case object AddItem extends Message
case class ReadFile(file: File) extends Message
case class ReadFile2(filename: String) extends Message
