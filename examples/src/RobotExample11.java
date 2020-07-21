import org.jointheleague.graphical.robot.Robot;

import java.util.concurrent.CountDownLatch;

public class RobotExample11 {

    private static final int NUM_ROBOTS = 7;
    private CountDownLatch latch = new CountDownLatch(NUM_ROBOTS);

    public static void main(String[] args) {

        RobotExample11 ex = new RobotExample11();
        for (int x = 1; x <= NUM_ROBOTS; x++ ) {
            Robot rob = new Robot(50 * x, 300);
            rob.setSpeed(20 - x);
            new Thread(ex.getRunnable(rob)).start();
        }
    }

    private Runnable getRunnable(Robot rob) {
        return () -> {
            rob.miniaturize();
            rob.setRandomPenColor();
            rob.setPenWidth(3);
            rob.sparkle();
            rob.sleep(1000);

            rob.penDown();
            rob.quadTo(350, -300, 500, 0, true);
            rob.cubicTo(-1000, 300, 200, 300, -500, 0, true);
            rob.unSparkle();
            rob.sleep(500);
            latch.countDown();
            try {
                latch.await();
            } catch (InterruptedException ignore) {
            }
            rob.hide();
        };
    }
}