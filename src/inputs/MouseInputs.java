package inputs;

import main.GamePanel;
import states.GameState;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInputs implements MouseListener, MouseMotionListener {

    private GamePanel gamePanel;
    public MouseInputs(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        switch (GameState.state) {
            case MENU:
                break;
            case INGAME:
                gamePanel.getGame().getIngame().mouseClicked(e);
                break;
            case EDITOR:
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (GameState.state) {
            case START_MENU:
                break;
            case INGAME:
                gamePanel.getGame().getIngame().mousePressed(e);
                break;
            case EDITOR:
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (GameState.state) {
            case START_MENU:
                gamePanel.getGame().getStartMenu().mouseReleased(e);
                break;
            case INGAME:
                gamePanel.getGame().getIngame().mouseReleased(e);
                break;
            case EDITOR:
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

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (GameState.state) {
            case START_MENU:
                gamePanel.getGame().getStartMenu().mouseMoved(e);
                break;
            case INGAME:
                gamePanel.getGame().getIngame().mouseMoved(e);
                break;
            case EDITOR:
                break;
        }
    }
}
