package org.misue.scalasample.actor.sample2

import scala.actors.Actor
import scala.actors.Actor._

case object Pong

class Pong extends Actor {
    def act() {
        var pongCount = 0
        loop {
            react {
                case Ping =>
                    if (pongCount % 1000 == 0)
                        println("Pong: ping " + pongCount)
                    sender ! Pong
                    pongCount += 1
                case Stop =>
                    println("Pong: stop")
                    exit()
            }
        }
    }
}
