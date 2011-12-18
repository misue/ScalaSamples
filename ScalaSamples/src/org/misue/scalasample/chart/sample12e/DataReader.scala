// All Rights Reserved. Copyright (C) Kazuo Misue (2010-2011)
package org.misue.scalasample.chart.sample12e

import java.io.File
import scala.io.Source

object DataReader {

    def read(filename: String): Chart =
        if (filename != null) read(new File(filename)) else null

    def read(file: File): Chart =
        new Chart(Source.fromFile(file).getLines.map(new Item(_)).toList)

}
