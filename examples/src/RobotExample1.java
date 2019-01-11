import org.jointheleague.graphical.robot.Robot;

import java.awt.*;

public class RobotExample1 {

    public static void main(String[] args) {

        Robot rob = new Robot(125, 125);


        rob.setSpeed(10);
        rob.miniaturize();
        rob.penDown();
        rob.setPenColor(Color.RED);
        rob.sleep(1000);

        int[] distances = {260, 150, 260, 150};
        for (int distance : distances) {
            rob.turn(90);
            rob.move(distance);
        }
        rob.turn(90);
        rob.penUp();
        rob.move(185);
        rob.turn(-90);
        rob.setSpeed(2);
        rob.move(-75);
    }
}