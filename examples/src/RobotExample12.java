import org.jointheleague.graphical.robot.Robot;
import org.jointheleague.graphical.robot.RobotWindow;

import java.awt.*;
import java.awt.geom.Ellipse2D;

;

public class RobotExample12 {

    public static void main(String[] args) {

        Robot rob = new Robot();
        RobotWindow window = RobotWindow.getInstance();
        int w = window.getWidth();
        int h = window.getHeight();
        float d = Math.min(w, h) - 100;
        Shape ellipse = new Ellipse2D.Float((w - d) / 2F, ( h - d) / 2F, d, d);
        rob.setSpeed(10);
        rob.setPenWidth(5);
        rob.setRandomPenColor();
        rob.penDown();
        rob.sleep(500);
        rob.followPath(ellipse.getPathIterator(null));
        rob.moveTo(w / 2F, h / 2F, false, false);
    }

}