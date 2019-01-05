# Project Description

This project allows novice programmers to experience a quick and easy start to programming. An example of a very simple program is as follows:

    import org.jointheleague.graphical.robot.Robot;
    
    public class RobotExample1
    {
    
	    public static void main(String[] args) throws InterruptedException
	    {
		    Robot rob = new Robot();
		    rob.setSpeed(5);
		    rob.penDown();
		    rob.move(100);
		    rob.turn(90);
	    }
    }


This program will bring up a window with the image of a robot in it. The robot moves 100 points while drawing a line, turns 90 degrees, and stops. With small variations, the programmer can make the robot make more intricate movements and draw more intricate patterns. `RobotExample1.java` and `RobotExample4.java` show examples slightly more complex. These examples also illustrate the use of for-loops.

A robot can also be controlled via the keys on the keyboard by adding a `KeyboardAdapter`. `RobotExample2.java` is a very small program, which lets the user control the robot via up, down, left and right arrow key presses. `ExtendedKeyboardAdapter.java` shows an extension of `KeyboardAdapter` that adds further commands that can be invoked through the key presses. In `RobotExample3.java`, the use of `ExtendedKeyboardAdapter` is illustrated. This example also shows how to use any image as the robot image.

More than one robot can be added to the window, which is illustrated by `RobotExample5.java`. This example also shows how to make the robots run simultaneously by letting the robots run in different threads. 

`RobotExample6.java` shows how to control two robots separately from the keyboard using `ShiftKeyboardAdapter`s. When the user holds down the shift key, one robot reacts to the key events, otherwise the other robot reacts to the key events.

If a user wants to teach a `Robot` new tricks, it is possible to extend the `Robot` class and add new methods and/or override existing ones. This is illustrated by `MyRobot`, which can draw regular polygons. The usage is shown in `RobotExample8.java`. Alternatively to extension, composition may be used. The class `Driver` has a `Robot` instance and commands to the `Driver` instance are delegated to the `Robot` instance. The usage is illustrated by `RobotExample9.java`. As one can see, `RobotExample8.java` and `RobotExample9.java` are almost identical. 

# Importing the jar file

Download this [jar file](https://github.com/ecolban/Robot/blob/master/jar/robot.jar?raw=true) and add it to the build path of your project. This jar file also contains the API documentation in the folder named `doc`.





 


