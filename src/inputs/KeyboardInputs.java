package inputs;

import main.GamePanel;

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

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_W:
                gamePanel.getGame().getPlayer().setMovingUp(true);
                break;
            case KeyEvent.VK_A:
                gamePanel.getGame().getPlayer().setMovingLeft(true);
                break;
            case KeyEvent.VK_D:
                gamePanel.getGame().getPlayer().setMovingRight(true);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_W:
                gamePanel.getGame().getPlayer().setMovingUp(false);
                break;
            case KeyEvent.VK_A:
                gamePanel.getGame().getPlayer().setMovingLeft(false);
                break;
            case KeyEvent.VK_D:
                gamePanel.getGame().getPlayer().setMovingRight(false);
                break;
        }
    }
}
