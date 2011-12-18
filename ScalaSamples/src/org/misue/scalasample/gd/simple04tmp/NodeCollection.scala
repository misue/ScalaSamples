package org.misue.scalasample.gd.simple04tmp

class NodeCollection() {
    var map: Map[String, Node] = Map()

    def add(names: Array[String]): Unit = {
        map = names.foldLeft[Map[String, Node]](map)((m, label) => {
            m + (label -> new Node(label))
        })
    }

    def link(links: Array[String]): Unit = {
        links.foreach((link) => {
            val tokens = link.split(",")
            val node1: Node = map.get(tokens(0)).get
            val node2: Node = map.get(tokens(1)).get
            node1.neighbors = node2 :: node1.neighbors
            node2.neighbors = node1 :: node2.neighbors
        })
    }

    def moveAll(): Unit = {

        val dt: Double = 0.1d
        map.values.foreach((n) => {

            var f: Vector = new Vector(0, 0)
            n.neighbors.foreach((n1) => {
                f += n.getSpringForce(n1)
            })
            map.values.foreach((n1) => {
                if (n != n1) {
                    f += n.getReplusiveForce(n1)
                }
            })
            f += n.getFrictionalForce()
            n.moveEular(dt, f)
        })
    }

}
