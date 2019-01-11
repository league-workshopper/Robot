// No imports

public class RobotExample8 {

    public static void main(String[] args) {
        MyRobot rob = new MyRobot();
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
