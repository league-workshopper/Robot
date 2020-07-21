import org.junit.jupiter.api.Test
import java.awt.geom.PathIterator
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RobotExample14KtTest {

    @Test
    fun testGetPathIterator() {
        val input = sequenceOf(
                "",
                "  M 1.2 3.2",
                "    ",
                "L4-5.6",
                "C7.910,11.2 3,4,5.6 7.8    ",
                " c -3.4-5.6, 7 8,9.1   11.2",
                "\t\n  ",
                "Z",
                "   ",
                "")
        val pathIterator = getPathIterator(input)
        val coords = FloatArray(6)
        // First segment -- "  M 1.2 3.2"
        assertFalse(pathIterator.isDone)
        assertEquals(PathIterator.SEG_MOVETO, pathIterator.currentSegment(coords))
        assertEquals(1.2F, coords[0])
        assertEquals(3.2F, coords[1])
        // Second segment -- "L4-5.6"
        pathIterator.next()
        assertFalse(pathIterator.isDone)
        assertEquals(PathIterator.SEG_LINETO, pathIterator.currentSegment(coords))
        assertEquals(4F, coords[0])
        assertEquals(- 5.6F, coords[1])
        // Third segment -- "C7.910,11.2 3,4,5.6 7.8    "
        pathIterator.next()
        assertFalse(pathIterator.isDone)
        assertEquals(PathIterator.SEG_CUBICTO, pathIterator.currentSegment(coords))
        assertEquals(7.91F, coords[0])
        assertEquals(11.2F, coords[1])
        assertEquals(3F, coords[2])
        assertEquals(4F, coords[3])
        assertEquals(5.6F, coords[4])
        assertEquals(7.8F, coords[5])
        // Fourth segment -- " c -3.4-5.6, 7 8,9.1   11.2"
        pathIterator.next()
        assertFalse(pathIterator.isDone)
        assertEquals(PathIterator.SEG_CUBICTO, pathIterator.currentSegment(coords))
        assertEquals(5.6F - 3.4F, coords[0])
        assertEquals(7.8F - 5.6F, coords[1])
        assertEquals(5.6F + 7F, coords[2])
        assertEquals(7.8F + 8F, coords[3])
        assertEquals(5.6F + 9.1F, coords[4])
        assertEquals(7.8F + 11.2F, coords[5])
        // Fifth segment -- "Z"
        pathIterator.next()
        assertFalse(pathIterator.isDone)
        assertEquals(PathIterator.SEG_CLOSE, pathIterator.currentSegment(coords))
        // End of pathIterator
        pathIterator.next()
        assertTrue(pathIterator.isDone)
    }
}