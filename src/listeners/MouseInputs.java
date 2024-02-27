package listeners;

import main.GamePanel;
import states.GameState;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Handles mouse inputs for the game.
 * Passes inputs to specified methods based on game state
 */
public class MouseInputs implements MouseListener, MouseMotionListener {

    private final GamePanel gamePanel;
    /**
     * Constructs a MouseInputs object
     *
     * @param gamePanel  GamePanel object
     */
    public MouseInputs(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        switch (GameState.state) {
            case MENU:
                gamePanel.getGame().getMenu().mouseClicked(e);
                break;
            case INGAME:
                gamePanel.getGame().getIngame().mouseClicked(e);
                break;
            case EDITOR:
                gamePanel.getGame().getEditor().mouseClicked(e);
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (GameState.state) {
            case START_MENU:
                break;
            case MENU:
                gamePanel.getGame().getMenu().mousePressed(e);
                break;
            case INGAME:
                gamePanel.getGame().getIngame().mousePressed(e);
                break;
            case EDITOR:
                gamePanel.getGame().getEditor().mousePressed(e);
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (GameState.state) {
            case START_MENU:
                gamePanel.getGame().getStartMenu().mouseReleased(e);
                break;
            case MENU:
                gamePanel.getGame().getMenu().mouseReleased(e);
                break;
            case INGAME:
                gamePanel.getGame().getIngame().mouseReleased(e);
                break;
            case EDITOR:
                gamePanel.getGame().getEditor().mouseReleased(e);
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (GameState.state == GameState.EDITOR) {
            gamePanel.getGame().getEditor().mouseDragged(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (GameState.state) {
            case START_MENU:
                gamePanel.getGame().getStartMenu().mouseMoved(e);
                break;
            case MENU:
                gamePanel.getGame().getMenu().mouseMoved(e);
            case INGAME:
                gamePanel.getGame().getIngame().mouseMoved(e);
                break;
            case EDITOR:
                gamePanel.getGame().getEditor().mouseMoved(e);
                break;
        }
    }
}
