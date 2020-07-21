package org.jointheleague.graphical.robot.curves;

import com.sun.istack.internal.NotNull;
import org.jointheleague.graphical.robot.Robot;

import java.awt.geom.Path2D;

public interface Segment {

    /**
     * Return the sub-segment of this segment that starts at <code>0</code> and ends at <code>t</code>.
     * For <code>t &le; 0</code>, returns a segment of size 0, and for <code>t &ge; 1F</code>,
     * returns this.
     *
     * @param t a float value, normally between 0 and 1
     * @return A sub-segment of this segment
     */
    Segment subSegment(float t);

    /**
     * Return the position on the segment at time <code>t</code>. If <code>t &le; 0</code>,
     * the start position of this segment is returned, and if <code>t &ge; 1</code>, the end
     * position of this segment is returned.
     *
     * @param t a float value, normally between 0 and 1
     * @return A position on this segment
     */
    Robot.Pos getPos(float t);

    /**
     * @return the angle in radians of the segment at the start
     */
    @NotNull
    double getStartAngle();

    /**
     * @return the angle in radians of the segment at the end
     */
    double getEndAngle();

    /**
     * Gets the angle of the segment in radians at the position specified by <code>time</code>
     *
     * @param time the time that specifies a position on the segment
     * @return the angle of the segment at <code>time</code>
     */
    double getAngle(float time);

    /**
     * The time a robot uses to trace a segment at a given speed is
     * proportional to the segment's size.
     *
     * @return an estimate of the length of the segment
     */
    float getSize();

    /**
     * Adds the Segment to the end of a Path2D
     *
     * @param path2D a Path2D to which this segment is added.
     * @return a Path2D
     */
    Path2D addTo(Path2D path2D);

    /**
     * Adds a sub-segment of this segment to the end of a Path2D
     *
     * @param path2D a Path2D to which the sub-segment is added
     * @param t      a float value, normally between 0 and 1, that
     *               specifies the sub-segment of this segment that is added
     * @return a Path2D
     * @see #subSegment(float)
     */
    Path2D addTo(Path2D path2D, float t);

}
