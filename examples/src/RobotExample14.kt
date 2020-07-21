import org.jointheleague.graphical.robot.Robot
import org.jointheleague.graphical.robot.RobotWindow
import java.awt.Color
import java.awt.geom.PathIterator
import java.io.File
import java.lang.RuntimeException
import java.lang.UnsupportedOperationException

private val floatRegex = """[-+]?\d*\.?\d+""".toRegex()

fun getPathIterator(lines: Sequence<String>): PathIterator = object : PathIterator {

    val iterator = lines
            .filter { !it.isBlank() }
            .map { it.trim() }
            .iterator()
    var done: Boolean = !iterator.hasNext()
    var currentLine = if (iterator.hasNext()) iterator.next() else ""
    var refPointX: Float = 0F
    var refPointY: Float = 0F

    override fun next() {
        if (iterator.hasNext()) {
            currentLine = iterator.next()
        } else {
            done = true
        }
    }

    override fun getWindingRule(): Int = PathIterator.WIND_EVEN_ODD

    override fun currentSegment(coords: FloatArray): Int {

        floatRegex.findAll(currentLine).forEachIndexed { i, matchResult ->
            coords[i] = matchResult.value.toFloat()
        }

        return when (currentLine[0]) {
            'M', 'm' -> {
                refPointX = coords[0]
                refPointY = coords[1]
                PathIterator.SEG_MOVETO
            }
            'L' -> {
                refPointX = coords[0]
                refPointY = coords[1]
                PathIterator.SEG_LINETO
            }
            'l' -> {
                for (i in 0..1) {
                    coords[i] += if (i % 2 == 0) refPointX else refPointY
                }
                refPointX = coords[0]
                refPointY = coords[1]
                PathIterator.SEG_LINETO
            }
            'Q' -> {
                refPointX = coords[2]
                refPointY = coords[3]
                PathIterator.SEG_QUADTO
            }
            'q' -> {
                for (i in 0..3) {
                    coords[i] += if (i % 2 == 0) refPointX else refPointY
                }
                refPointX = coords[2]
                refPointY = coords[3]
                PathIterator.SEG_QUADTO
            }
            'C' -> {
                refPointX = coords[4]
                refPointY = coords[5]
                PathIterator.SEG_CUBICTO
            }
            'c' -> {
                for (i in 0..5) {
                    coords[i] += if (i % 2 == 0) refPointX else refPointY
                }
                refPointX = coords[4]
                refPointY = coords[5]
                PathIterator.SEG_CUBICTO
            }
            'Z', 'z' -> PathIterator.SEG_CLOSE
            else -> throw RuntimeException()
        }
    }

    override fun currentSegment(coords: DoubleArray): Int {
        throw UnsupportedOperationException()
    }

    override fun isDone(): Boolean = done
}

fun main() {

    val rob = Robot()
    RobotWindow.getInstance().setWinColor(Color.WHITE)
    rob.setSpeed(5)
    rob.penWidth = 1
    rob.miniaturize()
    rob.penDown()
    val rect = sequenceOf(
            "M100,100",
            "l300,0",
            "l0,300",
            "l-300,0",
            "Z",
            "M150,150",
            "l0,200",
            "l200,0",
            "l0,-200",
            "Z")
    rob.setPenColor(230, 220, 220)
    rob.followPath(getPathIterator(rect), true)
    rob.penColor = Color.DARK_GRAY
    rob.setSpeed(20)
    File("examples/res/homer-simpson.txt").useLines {
        rob.followPath(getPathIterator(it), true)
    }
    rob.sleep(1000)
    rob.hide()
}

