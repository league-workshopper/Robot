import org.jointheleague.graphical.robot.Robot;

public class RobotExample9bis {

    public static void main(String[] args) {
        DriverKt rob = new DriverKt(new Robot());
        rob.setSpeed(10);
        rob.turn(45);
        rob.penDown();
        for (int i : new int[]{3, 4, 5, 6, 8, 10}) {
            rob.setPos(450, 300);
            rob.setRandomPenColor();
            rob.drawPolygon(i, 200);
        }
        rob.hide();
    }
}
