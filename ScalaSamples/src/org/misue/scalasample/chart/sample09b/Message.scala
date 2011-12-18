// All Rights Reserved. Copyright (C) Kazuo Misue (2011)
package org.misue.scalasample.chart.sample09b

import java.io.File
import java.awt.Color
import scala.actors.Actor

abstract class Message
//case object LayoutChart extends Message
case object AddItem extends Message
case class SetItemColor(color: Color) extends Message
case class ReadFile(file: File) extends Message
case class ReadFile2(filename: String) extends Message
case class SendDefaultColor(actor: Actor) extends Message
case class DefaultColor(color: Color) extends Message