package org.misue.scalasample.actor.sample2

import scala.actors.Actor
import scala.actors.Actor._

/**
 * Ping pong example.
 *
 * @author  Philipp Haller
 * @version 1.1
 */
object SampleApp1 extends Application {
  val pong = new Pong
  val ping = new Ping(100000, pong)
  ping.start
  pong.start
}
