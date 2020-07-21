import org.jointheleague.graphical.robot.Robot;
import org.jointheleague.graphical.robot.RobotWindow;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

public class RobotExample10 {

    private static final int NUM_ROBOTS = 10;
    private int totalDist;
    private long startTime;
    private long winnerTime;
    private CountDownLatch startLatch;

    private void startRace() throws InterruptedException {
        startLatch = new CountDownLatch(1);
        for (int i = 0; i < NUM_ROBOTS; i++) {
            final Robot robot = new Robot(15, 50 * (i + 1));
            robot.miniaturize();
            new Thread(getRunnable(robot)).start();
        }
        totalDist = RobotWindow.getInstance().getWidth() - 26;
        Thread.sleep(1000);
        startLatch.countDown();
        // Race started!!
        startTime = System.currentTimeMillis();
    }

    private Runnable getRunnable(final Robot robot) {
        return () -> {
            robot.setSpeed(5);
            robot.turn(90);
            int speed = 3;
            ThreadLocalRandom rng = ThreadLocalRandom.current();
            try {
                startLatch.await();
            } catch (InterruptedException ignore) {
            }
            // Race started!!
            for (int dst = 0; dst < totalDist; dst += 40) {
                speed += rng.nextInt(3) - 1;
                speed = Math.min(Math.max(2, speed), 4);
                robot.setSpeed(speed);
                robot.move(Math.min(40, totalDist - dst));
            }
            // Finish line reached!
            synchronized (RobotExample10.this) {
                System.out.println(getTimeResult());
            }
        };
    }

    private String getTimeResult() {
        final String robotName = Thread.currentThread().getName().replace("Thread", "Robot");
        long time = System.currentTimeMillis() - startTime - winnerTime;
        final long seconds = time / 1000;
        final long millis = time % 1000;

        if (winnerTime == 0L) {
            winnerTime = time;
            return String.format("%-9s  %2d.%03d", robotName + ":", seconds, millis);
        } else {
            return String.format("%-9s +%2d.%03d", robotName + ":", seconds, millis);
        }
    }

    public static void main(String... args) throws InterruptedException {
        new RobotExample10().startRace();
    }
}
