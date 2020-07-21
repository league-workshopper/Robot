import org.jointheleague.graphical.robot.KeyboardAdapter;
import org.jointheleague.graphical.robot.Robot;

public class RobotExample2 {

    public static void main(String[] args) {
        Robot rob = new Robot();
        rob.setSpeed(5);
        rob.miniaturize();
        rob.addKeyboardAdapter(new KeyboardAdapter());
    }
}
