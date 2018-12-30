package org.jointheleague.graphical.robot.curves;

import org.jointheleague.graphical.robot.Drawable;
import org.jointheleague.graphical.robot.Robot;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.util.Observable;

/**
 * A Path that is followed by a Robot. Only the part of the path from
 * the path's start to the robot's current position is drawn.
 */
final public class PartialPath extends Observable implements Drawable {

    private Segment currentSegment;
    private Path2D currentPath = new Path2D.Float();
    private float time = 0F;
    private final PathIterator pathIterator;
    private Robot.Pos startingPoint;
    private final int lineSize;
    private final Color color;
    private final Robot robot;

    /**
     * Constructor
     * @param pathIterator a PathIterator describing the path
     * @param lineSize the line width used to draw the path.
     * @param color the color used to draw the path
     * @param robot a Robot that moves along the path
     */
    public PartialPath(PathIterator pathIterator, int lineSize, Color color, Robot robot) {
        this.pathIterator = pathIterator;
        this.lineSize = lineSize;
        this.color = color;
        this.robot = robot;
        currentSegment = getCurrentSegment();
        assert currentSegment != null;
        double startAngle = currentSegment.getStartAngle();
        if (!Double.isNaN(startAngle)) robot.turn(robot.getAngleToTurn(startAngle));
    }

    private Segment getCurrentSegment() {
        assert !pathIterator.isDone();
        float[] coordinates = new float[6];
        float x = robot.getX();
        float y = robot.getY();
        int type = pathIterator.currentSegment(coordinates);
        switch (type) {
            case PathIterator.SEG_MOVETO:
                startingPoint = new Robot.Pos(coordinates[0], coordinates[1]);
                return new Move(x, y, coordinates);
            case PathIterator.SEG_LINETO:
                return new Line(x, y, coordinates, 0, null);
            case PathIterator.SEG_QUADTO:
                return new Quad(x, y, coordinates, 0, null);
            case PathIterator.SEG_CUBICTO:
                return new Cubic(x, y, coordinates, 0, null);
            case PathIterator.SEG_CLOSE:
                coordinates[0] = startingPoint.getX();
                coordinates[1] = startingPoint.getY();
                return new Close(x, y, coordinates, 0, null);
            default:
                return null;
        }
    }

    public void incrementTime(double speed) {
        time += speed / currentSegment.getSize();
        Robot.Pos pos = currentSegment.getPos(time);
        robot.setPos(pos.getX(), pos.getY());
        double angle = currentSegment.getAngle(time);
        if (!Double.isNaN(angle)) robot.setAngle(Math.toDegrees(angle));
        if (time >= 1F) {
            currentSegment.addTo(currentPath);
            pathIterator.next();
            if (!pathIterator.isDone()) {
                currentSegment = getCurrentSegment();
                double startAngle = currentSegment.getStartAngle();
                if (!Double.isNaN(startAngle)) robot.turn(robot.getAngleToTurn(startAngle));
                time = 0F;
            }
        }
        setChanged();
        notifyObservers();
    }

    public boolean isComplete() {
        return pathIterator.isDone();
    }

    private Path2D getPath2D() {
        if (0.0 < time && time < 1.0) {
            Path2D path = new Path2D.Float(currentPath);
            return currentSegment.addTo(path, time);
        }
        return currentPath;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setStroke(new BasicStroke(lineSize));
        g2.setColor(color);
        g2.draw(getPath2D());
    }
}
