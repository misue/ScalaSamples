package org.misue.scalasample.actor.sample3

import scala.actors.Actor
import scala.actors.Actor._

object SampleApp3 extends Application {
    val friends = List(
        new Ping(1, 110, 210),
        new Ping(2, 120, 220),
        new Ping(3, 130, 230))
    friends.foreach(_.start)
}
