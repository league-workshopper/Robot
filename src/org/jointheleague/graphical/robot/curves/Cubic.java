package org.jointheleague.graphical.robot.curves;

import org.jointheleague.graphical.robot.Robot;

import java.awt.*;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Path2D;

final public class Cubic implements Drawable, Segment {

    private final float startX;
    private final float startY;
    private final float[] ctrlPoints;
    private final int lineSize;
    private final Color color;
    private final Line line;
    private final Quad quad;

    /**
     * Constructor
     *
     * @param x          x-coordinate of the segment's starting point
     * @param y          y-coordinate of the segment's starting point
     * @param ctrlPoints an array of length at least 6 that
     *                   contains the x- and y-coordinates of the segment's control points.
     * @param lineSize   the line width used to draw the segment
     * @param color      the color used to draw the segment
     */
    public Cubic(float x, float y, float[] ctrlPoints, int lineSize, Color color) {
        this.startX = x;
        this.startY = y;
        this.ctrlPoints = ctrlPoints;
        this.lineSize = lineSize;
        this.color = color;
        this.line = new Line(x, y, ctrlPoints, 0, null);
        this.quad = new Quad(x, y, ctrlPoints, 0, null);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setStroke(new BasicStroke(lineSize));
        g2.setColor(color);
        CubicCurve2D.Float cubic = new CubicCurve2D.Float(
                startX, startY,
                ctrlPoints[0], ctrlPoints[1],
                ctrlPoints[2], ctrlPoints[3],
                ctrlPoints[4], ctrlPoints[5]);
        g2.draw(cubic);
    }

    @Override
    public Segment subSegment(float t) {
        Robot.Pos pos1 = line.getPos(t);
        Robot.Pos pos2 = quad.getPos(t);
        Robot.Pos pos3 = getPos(t);
        float[] ctrlPoints = {
                pos1.getX(), pos1.getY(),
                pos2.getX(), pos2.getY(),
                pos3.getX(), pos3.getY()};
        return new Cubic(startX, startY, ctrlPoints, lineSize, color);
    }

    @Override
    public Robot.Pos getPos(float t) {
        if (t <= 0F) return new Robot.Pos(startX, startY);
        if (1F <= t) return new Robot.Pos(ctrlPoints[4], ctrlPoints[5]);
        final float u = 1 - t;
        final float uu = u * u;
        final float tt = t * t;
        float c0 = uu * u;
        float c1 = 3 * uu * t;
        float c2 = 3 * u * tt;
        float c3 = t * tt;
        return new Robot.Pos(
                c0 * startX + c1 * ctrlPoints[0] + c2 * ctrlPoints[2] + c3 * ctrlPoints[4],
                c0 * startY + c1 * ctrlPoints[1] + c2 * ctrlPoints[3] + c3 * ctrlPoints[5]);
    }

    @Override
    public double getStartAngle() {
        if (ctrlPoints[0] == startX && ctrlPoints[1] == startY) return Double.NaN;
        return Math.atan2(ctrlPoints[0] - startX, -ctrlPoints[1] + startY);
    }

    @Override
    public double getEndAngle() {
        if (ctrlPoints[4] == ctrlPoints[2] && ctrlPoints[5] == ctrlPoints[3]) return Double.NaN;
        return Math.atan2(ctrlPoints[4] - ctrlPoints[2], -ctrlPoints[5] + ctrlPoints[3]);
    }

    @Override
    public double getAngle(float time) {
        if (time <= 0F) return getStartAngle();
        if (1F <= time) return getEndAngle();
        return subSegment(time).getEndAngle();
    }

    @Override
    public float getSize() {
        double a = ctrlPoints[0] - startX;
        double b = ctrlPoints[1] - startY;
        double c = ctrlPoints[2] - ctrlPoints[0];
        double d = ctrlPoints[3] - ctrlPoints[1];
        double e = ctrlPoints[4] - ctrlPoints[2];
        double f = ctrlPoints[5] - ctrlPoints[3];
        return (float) (Math.sqrt(a * a + b * b) + Math.sqrt(c * c + d * d) + Math.sqrt(e * e + f * f));
    }

    @Override
    public Path2D addTo(Path2D path2D) {
        path2D.curveTo(ctrlPoints[0], ctrlPoints[1], ctrlPoints[2], ctrlPoints[3], ctrlPoints[4], ctrlPoints[5]);
        return path2D;
    }

    @Override
    public Path2D addTo(Path2D path2D, float t) {
        Robot.Pos pos1 = line.getPos(t);
        Robot.Pos pos2 = quad.getPos(t);
        Robot.Pos pos3 = getPos(t);
        path2D.curveTo(
                pos1.getX(), pos1.getY(),
                pos2.getX(), pos2.getY(),
                pos3.getX(), pos3.getY());
        return path2D;
    }
}
