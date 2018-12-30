package examples;

import org.jointheleague.graphical.robot.Robot;
import org.jointheleague.graphical.robot.RobotWindow;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.CountDownLatch;

public class RobotExample13 {

    public static void main(String[] args) {

        char[] text = "Amazing ROBOTS!".toCharArray();
        Robot[] robots = new Robot[text.length];
        int numRobots =0;
        for (int i = 0; i < robots.length; i++) {
            if (text[i] == ' ') continue;
            numRobots++;
            robots[i] = new Robot(100 + 50 * i, 300);
            robots[i].miniaturize();
        }
        RobotWindow window = RobotWindow.getInstance();
        window.setWinColor(Color.WHITE);
        final Font font = new Font("Times New Roman", Font.PLAIN, 96);
//        final Font font = new Font("Helvetica", Font.PLAIN, 96);
        final Graphics2D g2 = (Graphics2D) window.getGraphics();
        final FontRenderContext frc = g2.getFontRenderContext();
        final GlyphVector glyphVector =
                font.layoutGlyphVector(frc, text, 0, text.length, Font.LAYOUT_LEFT_TO_RIGHT);
        final Rectangle2D textBounds = glyphVector.getLogicalBounds();
        final Dimension dimension = window.getSize();
        final float leftMargin = (float) ((dimension.getWidth() - textBounds.getWidth()) / 2);
        final float topMargin = (float) ((dimension.getHeight() - textBounds.getHeight()) / 2);
        final CountDownLatch latch = new CountDownLatch(numRobots);
        for (int i = 0; i < text.length; i++) {
            if (text[i] == ' ') continue;
            Shape glyphShape = glyphVector.getGlyphOutline(i, leftMargin, topMargin);
            final PathIterator pathIterator = glyphShape.getPathIterator(null);
            Robot rob = robots[i];
            rob.setSpeed(2);
            rob.setPenWidth(1);
            rob.setRandomPenColor();
            rob.penDown();
            new Thread(() -> {
                rob.followPath(pathIterator);
                Rectangle bounds = glyphShape.getBounds();
                rob.penUp();
                rob.moveTo(bounds.x + bounds.width / 2F, 300, false, false);
                rob.moveTo(0, -1, true, false);
                rob.sleep(300);
                latch.countDown();
                try {
                    latch.await();
                } catch (InterruptedException ignore) {
                }
                rob.hide();
            }).start();
        }
    }
}