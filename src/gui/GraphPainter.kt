package gui

import java.awt.*
import java.lang.Math.*
import javax.swing.plaf.basic.BasicGraphicsUtils.drawString

class GraphPainter(val graph: MutableList<MutableList<Double>>) : Painter {

    private var width = 1
    private var height = 1
    var thickness = 1
        set(value) {
            if (value >=1 && value <= 30) {
                field = value
                calcVertexPositions()
            }
        }

    var vertexSize = 30
        set(value){
            if (value >=10 && value <= 100) {
                field = value
                calcVertexPositions()
            }
        }

    override var size: Dimension
        get() = Dimension(width, height)
        set(value){
            width = value.width
            height = value.height
            calcVertexPositions()
        }

    private val minSz: Int
        get() = min(width, height) - vertexSize - thickness

    private val rect: Rectangle
        get() = Rectangle((width - minSz)/2, (height-minSz)/2, minSz, minSz)

    private val radius: Int
        get() = minSz / 2

    private val center: Point
        get() = Point(rect.x + radius, rect.y + radius)

    private val phi: Double
        get() = 2 * PI / graph.size

    private var vertexPositions: MutableList<Point>? = null

    private fun calcVertexPositions(){
        vertexPositions = MutableList<Point>(graph.size) { i ->
            Point((center.x + radius * cos(i * phi)).toInt() ,
                (center.y + radius * sin(i * phi)).toInt()
            )
        }
    }

    override fun paint(g: Graphics){
        paintEdges(g)
        paintVerticies(g)
    }

    private fun paintVerticies(g: Graphics) {
        (g as Graphics2D).apply {
            //rotate(phi, center.x.toDouble(), center.y.toDouble())
            var i = 1
            vertexPositions?.forEach {
                color = Color.WHITE
                fillOval(it.x - vertexSize / 2, it.y - vertexSize / 2, vertexSize, vertexSize)
                color = Color.BLUE
                drawOval(it.x - vertexSize / 2, it.y - vertexSize / 2, vertexSize, vertexSize)
                rotate(PI/2, it.x.toDouble(),it.y.toDouble())
                drawString(i.toString(), it.x-vertexSize/4, it.y + vertexSize/4)
                rotate(-PI/2, it.x.toDouble(),it.y.toDouble())
                i++
            }
        }
    }
    /*private fun paintVertexNumber(g: Graphics/*, i: Int*/){
        (g as Graphics2D).apply {
            rotate(PI/2, )
            var i = 1
            vertexPositions?.forEach {
                g.color = Color.BLUE
                g.drawString(i.toString(), it.x-vertexSize/4, it.y + vertexSize/4)
                i++
            }
        }

    }*/

    private fun paintEdges(g: Graphics) {
        (g as Graphics2D).apply {
            rotate(-PI / 2, center.x.toDouble(), center.y.toDouble())
            stroke = BasicStroke(thickness.toFloat(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)
            setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        }
        graph.forEachIndexed { fromInd, from ->
            from.takeLast(graph.size - fromInd - 1).forEachIndexed { toInd, weight ->
                    if (weight > 1e-20) {
                        vertexPositions?.let { vPos ->
                            val toI = toInd + fromInd + 1
                            g.color = Color.BLACK
                            g.drawLine(vPos[fromInd].x, vPos[fromInd].y, vPos[toI].x, vPos[toI].y)
                            paintEdgesWeight(
                                g,
                                atan(abs(vPos[toI].x - vPos[fromInd].x)/abs(vPos[toI].y - vPos[fromInd].y).toDouble()),
                                weight,
                                ( (vPos[toI].x+vPos[fromInd].x)/2 ).toDouble(),
                                ( (vPos[toI].y+vPos[fromInd].y)/2 ).toDouble()
                            )
                        }
                    }
            }
        }

    }
    fun paintEdgesWeight(g:Graphics, alfa: Double, w: Double, s1: Double, s2: Double){
        (g as Graphics2D).apply(){
            color = Color.BLUE
            rotate( PI/2+alfa, s1, s2)
            g.drawString(w.toString(),s1.toFloat(), s2.toFloat() )
            g.rotate(-(PI/2+alfa), s1, s2)
        }
    }

}