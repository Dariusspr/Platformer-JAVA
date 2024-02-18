package inputs;

import main.GamePanel;
import states.GameState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static utils.Constants.Player.*;

public class KeyboardInputs implements KeyListener {
    private final GamePanel gamePanel;
    public KeyboardInputs(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        switch (GameState.state) {
            case MENU:
                break;
            case INGAME:
                break;
            case EDITOR:
                break;
        }
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
