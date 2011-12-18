package org.misue.scalasample.gd.simple04tmp

object Program {

    def main(args: Array[String]): Unit = {

        val nodeSet: NodeCollection = createGraph()
        val nodeList: List[Node] = nodeSet.map.values.toList
        circularize(nodeList, 200.0)

        for (i <- 0 to 1000) nodeSet.moveAll()

        nodeList.foreach((n) => {
            println(n.name + " (" + n.r.x + ", " + n.r.y + ")")
        })
    }

    def createGraph(): NodeCollection = {
        val nodeSet = new NodeCollection()
        nodeSet.add(Array("a", "a'", "b", "b'", "c", "c'",
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
            "20", "21", "22", "23", "24", "25", "26", "27", "28", "29"))

        nodeSet.link(Array("0,1", "0,10", "0,21", "1,2", "1,28",
            "2,3", "2,6", "3,4", "3,b'", "4,5",
            "4,8", "5,6", "5,7", "6,11", "7,14",
            "7,c'", "8,9", "8,10", "9,16", "9,a'",
            "10,29", "11,14", "11,28", "12,13", "12,20",
            "12,27", "13,14", "13,19", "15,16", "15,18",
            "15,29", "16,17", "17,18", "17,c", "18,26",
            "19,22", "19,28", "20,22", "20,25", "21,24",
            "21,29", "22,23", "23,24", "23,a", "24,25",
            "25,26", "26,27", "27,b"))
        nodeSet
    }

    def circularize(nodeList: List[Node], r: Double): Unit = {
        val count: Int = nodeList.size
        val dtheta: Double = 2.0d * Math.Pi / count
        var theta: Double = 0.0d

        nodeList.foreach((n) => {
            n.r = new Vector(
                r * Math.cos(theta),
                r * Math.sin(theta))
            n.v = new Vector(0.0, 0.0)
            theta += dtheta
        })
    }
}
