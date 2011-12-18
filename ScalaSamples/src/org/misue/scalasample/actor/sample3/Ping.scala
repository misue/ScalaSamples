package org.misue.scalasample.actor.sample3

import scala.actors.Actor
import scala.actors.Actor._

//case object Ping

class Ping(val id: Int, var posX: Double, var posY: Double) extends Actor {
    
    private var others: List[Ping] = List()
    
    def act(): Unit = {
        val sleepTime: Int = 2000 + (math.random * 1000).toInt
        
        println("Ping" + id + " is running ... " + sleepTime)

        def notifyLater(): Unit = {
            val mainActor = self
            actor {
                Thread.sleep(sleepTime)
                mainActor ! "wakeup"
            }
        }
        
        notifyLater()
        loop {
            react {
                case "wakeup" => {
                    sendReq()
                    notifyLater()
                }
                case "where" => 
                    println("Ping" + id + " is asked where is it.")
                    sender ! (id, posX, posY)
                case (oid: Int, x: Double, y: Double) => 
                    println("Ping" + id + "<-" + oid + "(" + x + "," + y + ")")
            }
        }
    }
    
    def friends_=(list: List[Ping]) {
        others = list.filter(_ != this)
    }
    
    def sendReq(): Unit = {
        println("Ping" + id + " is asking for other positions.")
        others.foreach(_ ! "where")
    }
}
