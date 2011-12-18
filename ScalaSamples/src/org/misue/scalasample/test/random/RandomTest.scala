package org.misue.scalasample.test.random
import scala.util.Random
import java.util.Calendar


object RandomTest {
    
    val seed = Calendar.getInstance.getTimeInMillis
    val random = new Random(seed)
    val check = new Array[Int](10)
    
    def main(args: Array[String]): Unit = {
        for (index <- 0 until 10) check(index) = 0
        for (count <- 0 until 100000) {
//            val index = (Math.random * 10).toInt
            val index = random.nextInt(10)
            check(index) += 1
        }
        for (index <- 0 until 10) print(index + ":" + check(index) + " ")
        println
    }

}

