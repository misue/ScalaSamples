// All Rights Reserved. Copyright (C) Kazuo Misue (2010-2011)
package org.misue.scalasample.chart.sample09d

import java.io.File
import scala.io.Source
//import org.misue.scalasample.chart.sample1._
import java.awt.Color

object DataReader {

    def read(filename: String, defaultColor: Color): Chart =
        if (filename != null) read(new File(filename), defaultColor) else null

    def read(file: File, defaultColor: Color): Chart =
        new Chart(defaultColor, Source.fromFile(file).getLines.map(new Item(_)).toList)

}
