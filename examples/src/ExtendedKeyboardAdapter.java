import org.jointheleague.graphical.robot.KeyboardAdapter;

import java.awt.event.KeyEvent;

public class ExtendedKeyboardAdapter extends KeyboardAdapter {


//    public ExtendedKeyboardAdapter() {
//        super();
//    }


    @Override
    public void keyTyped(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'm':
                robot.miniaturize();
                break;
            case 'M':
                robot.expand();
                break;
            case 'u':
                robot.penUp();
                break;
            case 'd':
                robot.penDown();
                break;
            default:
                super.keyTyped(e);
        }

    }

}
