package org.jointheleague.graphical.robot;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * <p>
 * This class is used to control a robot through key presses. The up, down,
 * left, and right arrow keys are used to make the robot advance, go backwards,
 * turn left, and turn right respectively. The {@link KeyEvent}s that are used
 * to control the Robot and the ways that those KeyEvents control the Robot can
 * be changed by extending this class and overriding the
 * {@link #keyPressed(KeyEvent)}, {@link #keyReleased(KeyEvent)}, and
 * {@link #keyTyped(KeyEvent)} methods.
 * </p>
 * <p>
 * It is possible to add multiple KeyboardAdapters each controlling a different
 * Robot. To be able to distinguish which {@link KeyEvent}s control which
 * Robots, the set of KeyEvents that each KeyboardAdapter reacts to should be
 * distinct. For example, one KeyboardAdapter could react only to KeyEvents
 * where the Shift key is not depressed, and another could react only to events
 * where the Shift key is depressed. It is, nevertheless, also possible to have
 * all KeyboardAdapters react to a same KeyEvent. For example, when the user
 * types 'm', each KeyboardAdapter could react by miniaturizing the Robot that
 * it controls.
 * </p>
 * <p>
 * A KeyboardAdapter controls the Robot from its own thread. It is not possible
 * to have more than one KeyboardAdapter controlling the <em>same</em> Robot.
 * </p>
 * <p>
 * Example of how to use:
 * </p>
 * 
 * <pre>
 * {@code
 * public static void main(String[] args) throws InterruptedException
 * {
 *	Robot rob = new Robot();
 *	rob.addKeyboardAdapter(new KeyboardAdapter());
 * }
 * }
 * </pre>
 * 
 * 
 * @author Erik Colban &copy; 2016
 *
 */
public class KeyboardAdapter implements KeyListener, Runnable {

	/**
	 * The Robot instance that the KeyboardAdapter is attached to.
	 */
	protected Robot robot;
	private volatile boolean movingForward = false;
	private volatile boolean movingBackward = false;
	private volatile boolean turningLeft = false;
	private volatile boolean turningRight = false;

	/**
	 * Constructor.
	 */
	public KeyboardAdapter() {
	}
	
	public void setRobot(Robot robot) {
		this.robot = robot;
		new Thread(this).start();
	}

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {

			try {
				if (movingForward && !movingBackward) {
					robot.microMove(1);
				} else if (movingBackward && !movingForward) {
					robot.microMove(-1);
				} else if (turningRight && !turningLeft) {
					robot.microTurn(1);
				} else if (turningLeft && !turningRight) {
					robot.microTurn(-1);
				} else {
				    robot.doNothing();
                }
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * Sets or unsets the Robot in a forward motion.
	 * 
	 * @param movingForward
	 *            true to set the Robot in forward motion, false to cancel
	 *            forward motion.
	 */
	protected void setMovingForward(boolean movingForward) {
		this.movingForward = movingForward;
	}

	/**
	 * Sets or unsets the Robot in a backward motion.
	 * 
	 * @param movingBackward
	 *            true to set the Robot in backward motion, false to cancel
	 *            backward motion.
	 */
	protected void setMovingBackward(boolean movingBackward) {
		this.movingBackward = movingBackward;
	}

	/**
	 * Sets or unsets the Robot in a left turning motion.
	 * 
	 * @param turningLeft
	 *            true to set the Robot in a left turning motion, false to
	 *            cancel the turning motion.
	 */
	protected void setTurningLeft(boolean turningLeft) {
		this.turningLeft = turningLeft;
	}

	/**
	 * Sets or unsets the Robot in a left turning motion.
	 * 
	 * @param turningRight
	 *            true to set the Robot in a right turning motion, false to
	 *            cancel the turning motion.
	 */
	protected void setTurningRight(boolean turningRight) {
		this.turningRight = turningRight;
	}

	/**
	 * No actions are specified, but that can be overridden in extensions of
	 * this class.
	 */
	@Override
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * Implements the natural movements associated with the up, down, left, and
	 * right arrow keys.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			movingForward = true;
			break;
		case KeyEvent.VK_DOWN:
			movingBackward = true;
			break;
		case KeyEvent.VK_LEFT:
			turningLeft = true;
			break;
		case KeyEvent.VK_RIGHT:
			turningRight = true;
			break;
		default:
		}

	}

	/**
	 * Implements the natural movements associated with the up, down, left, and
	 * right arrow keys.
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			movingForward = false;
			break;
		case KeyEvent.VK_DOWN:
			movingBackward = false;
			break;
		case KeyEvent.VK_LEFT:
			turningLeft = false;
			break;
		case KeyEvent.VK_RIGHT:
			turningRight = false;
			break;
		default:
		}
	}

}
