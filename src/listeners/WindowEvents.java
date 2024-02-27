package listeners;

import main.GamePanel;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Handles window events for the game window.
 * Used to customize what happens when windowClosing is triggered
 */
public class WindowEvents extends WindowAdapter {

    private final GamePanel gamePanel;
    /**
     * Constructs a WindowEvents
     *
     * @param gamePanel GamePanel object
     */
    public WindowEvents(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    @Override
    public void windowClosing(WindowEvent e) {
        super.windowClosing(e);
        gamePanel.getGame().quit();
    }
}
