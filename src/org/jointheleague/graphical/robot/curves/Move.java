package org.jointheleague.graphical.robot.curves;

import org.jointheleague.graphical.robot.Robot;

import java.awt.geom.Path2D;

final public class Move implements Segment {

    private final float startX;
    private final float startY;
    private final float[] ctrlPoints;

    /**
     * Constructor. This segment is not drawn.
     *
     * @param x          x-coordinate of the segment's starting point
     * @param y          y-coordinate of the segment's starting point
     * @param ctrlPoints an array of length at least 2 that
     *                   contains the x- and y-coordinates of the segment's control points.
     *
     */
    public Move(float x, float y, float[] ctrlPoints) {
        this.startX = x;
        this.startY = y;
        this.ctrlPoints = ctrlPoints;
    }

    @Override
    public Segment subSegment(float t) {
        Robot.Pos pos = getPos(t);
        return new Move(startX, startY, new float[]{pos.getX(), pos.getY()});
    }

    @Override
    public Robot.Pos getPos(float t) {
        if (t <= 0.0F) return new Robot.Pos(startX, startY);
        if (1.0F <= t) return new Robot.Pos(ctrlPoints[0], ctrlPoints[1]);
        float u = 1F - t;
        return new Robot.Pos(
                u * startX + t * ctrlPoints[0],
                u * startY + t * ctrlPoints[1]);
    }

    @Override
    public double getStartAngle() {
        if (ctrlPoints[0] == startX && ctrlPoints[1] == startY) return Double.NaN;
        return Math.atan2(ctrlPoints[0] - startX, -ctrlPoints[1] + startY);
    }

    @Override
    public double getEndAngle() {
        return getStartAngle();
    }

    @Override
    public double getAngle(float t) {
        return getStartAngle();
    }

    @Override
    public float getSize() {
        double a = ctrlPoints[0] - startX;
        double b = ctrlPoints[1] - startY;
        return (float) Math.sqrt(a * a + b * b);
    }

    @Override
    public Path2D addTo(Path2D path2D) {
        path2D.moveTo(ctrlPoints[0], ctrlPoints[1]);
        return path2D;
    }

    @Override
    public Path2D addTo(Path2D path2D, float t) {
        final Robot.Pos pos1 = getPos(t);
        path2D.moveTo(pos1.getX(), pos1.getY());
        return path2D;
    }
}
