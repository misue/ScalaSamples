package org.misue.scalasample.gd.simple04tmp

class Vector(val x: Double, val y: Double) {

  def +(v: Vector): Vector = {
      new Vector(x + v.x, y + v.y)
    }
}
