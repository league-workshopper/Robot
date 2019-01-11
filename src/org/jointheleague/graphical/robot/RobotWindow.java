package org.jointheleague.graphical.robot;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Singleton class that defines the window in which the Robots move around.
 *
 * @author David Dunn &amp; Erik Colban &copy; 2016
 */
@SuppressWarnings("serial")
public class RobotWindow extends JPanel {

    private static final int WINDOW_HEIGHT = 600;
    private static final int WINDOW_WIDTH = 900;
    private static final Color DEFAULT_WINDOW_COLOR = new Color(0xdcdcdc);
    private static final int MARGIN = 10;
    private static final RobotWindow INSTANCE = new RobotWindow(DEFAULT_WINDOW_COLOR);

    private Color winColor;

    private ArrayList<Robot> robotList;
    private Timer ticker;

    private BufferedImage leagueLogo;
    private boolean usingCustomImage;

    private boolean guiHasBeenBuilt = false;

    private JFrame frame;

    private RobotWindow(Color color) {
        winColor = color;
        robotList = new ArrayList<>();
        try {
            leagueLogo = ImageIO.read(this.getClass().getResourceAsStream("res/league_logo.png"));
        } catch (IOException e) {
            System.err.println("Cannot load background image.");
        }
        usingCustomImage = false;
    }

    /**
     * Returns the single instance of the RobotWindow
     *
     * @return the single instance
     */
    public static RobotWindow getInstance() {
        return INSTANCE;
    }

    private void buildGui() {
        frame = new JFrame();
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        frame.add(this);
        // frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.pack();
        frame.setVisible(true);
        setFocusable(true);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(winColor);
        g2.fillRect(0, 0, getWidth(), getHeight());
        if (usingCustomImage) {
            g2.drawImage(leagueLogo, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, null);
        } else {
            int imgX = getWidth() - leagueLogo.getWidth() - MARGIN;
            int imgY = MARGIN;
            g2.drawImage(leagueLogo, imgX, imgY, null);
        }

        RenderingHints renderingHints = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHints(renderingHints);
        for (Robot robot : robotList) {
            robot.draw(g2);
        }
    }

    /**
     * Adds a robot to the window
     *
     * @param robot the robot
     */
    void addRobot(final Robot robot) {
        final ActionListener tickerListener = robot.getTickerListener();
        try {
            SwingUtilities.invokeAndWait(() -> {
                if (!guiHasBeenBuilt) {
                    buildGui();
                    ticker = new Timer(Robot.TICK_LENGTH, tickerListener);
                    ticker.start();
                    guiHasBeenBuilt = true;
                } else {
                    ticker.addActionListener(tickerListener);
                }
                robotList.add(robot);
                repaint();
            });
        } catch (InvocationTargetException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set the RobotWindow's background Color. This method should be invoked on the EDT only.
     *
     * @param color the background color
     */
    public void setWinColor(Color color) {
        winColor = color;
        repaint();
    }

    /**
     * Set the RobotWindow's background Image. This method should be invoked on the EDT only.
     *
     * @param imageLocation the location of the background image
     */
    public void setBackgroundImage(String imageLocation) {
        try {
            leagueLogo = ImageIO.read(this.getClass().getResourceAsStream("../../../../" + imageLocation));
        } catch (IOException e) {
            System.err.println("Cannot load background image.");
        }
        usingCustomImage = true;
        repaint();
    }


    /**
     * Sets the dimension of the panel containing the robots. This method should be invoked on the EDT only.
     *
     * @param width  the width of the panel
     * @param height the height of the panel
     */
    public void setWindowSize(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        frame.pack();
    }

}
