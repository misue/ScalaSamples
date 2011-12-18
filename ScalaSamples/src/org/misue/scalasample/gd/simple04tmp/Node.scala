package org.misue.scalasample.gd.simple04tmp

class Node(val name: String) {
    var neighbors: List[Node] = Nil
    var r: Vector = new Vector(0, 0)
    var v: Vector = new Vector(0, 0)
    val epsilon: Double = 0.001

    def getSpringForce(n: Node): Vector = {
        // ばねの力は自然長からの変位に比例 (比例定数 -k, ばねの長さ l)
        val k: Double = 0.1d
        val l: Double = 60.0d
        val dx: Double = r.x - n.r.x
        val dy: Double = r.y - n.r.y
        val d2: Double = dx * dx + dy * dy
        if (d2 < epsilon) {
            // 距離0の時は例外として乱数で決定
            new Vector(Math.random - 0.5d, Math.random - 0.5d)
        } else {
            val d: Double = Math.sqrt(d2)
            val cos: Double = dx / d
            val sin: Double = dy / d
            val dl: Double = d - l
            new Vector(-k * dl * cos, -k * dl * sin)
        }
    }

    def getReplusiveForce(n: Node): Vector = {
        // 反発は距離の2乗に反比例 (比例定数 g)
        val g: Double = 500.0d
        val dx: Double = r.x - n.r.x
        val dy: Double = r.y - n.r.y
        val d2: Double = dx * dx + dy * dy
        if (d2 < epsilon) {
            // 距離0の時は例外として乱数で決定
            new Vector(Math.random - 0.5d, Math.random - 0.5d)
        } else {
            val d: Double = Math.sqrt(d2)
            val cos: Double = dx / d
            val sin: Double = dy / d
            new Vector(g / d2 * cos, g / d2 * sin)
        }
    }

    def getFrictionalForce(): Vector = {
        // 摩擦力は速度に比例 (比例定数 -m)
        val m: Double = 0.3d
        new Vector(-m * v.x, -m * v.y)
    }

    def moveEular(dt: Double, f: Vector): Unit = {
        // 質量は1とする
        r = new Vector(
            r.x + dt * v.x,
            r.y + dt * v.y)
        v = new Vector(
            v.x + dt * f.x,
            v.y + dt * f.y)
    }

}
