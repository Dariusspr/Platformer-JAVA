package listeners;

import main.GamePanel;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowEvents extends WindowAdapter {

    private GamePanel gamePanel;
    public WindowEvents(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    @Override
    public void windowClosing(WindowEvent e) {
        super.windowClosing(e);
        gamePanel.getGame().quit();
    }
}
