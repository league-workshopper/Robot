import org.jointheleague.graphical.robot.Robot
import org.jointheleague.graphical.robot.RobotInterface
import java.util.*

class DriverKt(private val robot: Robot) : RobotInterface by robot {

    override fun setRandomPenColor() {
        val shade = 5 * Random().nextInt(50)
        robot.setPenColor(shade, shade, shade)
    }

    /**
     * Draws a regular polygon with center at the robot's current position
     *
     * @param numSides the number of sides of the polygon
     * @param radius   the radius of the circumscribed circle.
     */
    fun drawPolygon(numSides: Int, radius: Int) {
        val angle = 360.0 / numSides
        val sin = Math.sin(Math.toRadians(angle / 2.0))
        val sideLen = (2.0 * radius.toDouble() * Math.abs(sin)).toInt()
        penUp()
        move(radius)
        turn(90.0 + angle / 2.0)
        penDown()
        move(sideLen)
        for (i in 1 until numSides) {
            turn(angle)
            move(sideLen)
        }
        penUp()
        turn(-90.0 + angle / 2.0)
    }

}
