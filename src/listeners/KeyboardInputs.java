package listeners;

import main.GamePanel;
import states.GameState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Handles keyboard inputs for the game.
 * Passes inputs to specified methods based on game state
 */
public class KeyboardInputs implements KeyListener {
    private final GamePanel gamePanel;

    /**
     * Constructs a KeyboardInputs object
     *
     * @param gamePanel  GamePanel object
     */
    public KeyboardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (GameState.state) {
            case MENU:
                gamePanel.getGame().getMenu().keyPressed(e);
                break;
            case INGAME:
                gamePanel.getGame().getIngame().keyPressed(e);
                break;
            case EDITOR:
                gamePanel.getGame().getEditor().keyPressed(e);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (GameState.state) {
            case START_MENU:
                gamePanel.getGame().getStartMenu().keyReleased(e);
            case MENU:
                gamePanel.getGame().getMenu().keyReleased(e);
                break;
            case INGAME:
                gamePanel.getGame().getIngame().keyReleased(e);
                break;
            case EDITOR:
                gamePanel.getGame().getEditor().keyReleased(e);
                break;
        }
    }
}
