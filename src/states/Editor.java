package states;

import levels.Level;
import levels.LevelHandler;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Editor extends State implements StateHandler{


    private LevelHandler levelHandler;
    public Editor(Game game, LevelHandler levelHandler) {
        super(game);
        this.levelHandler = levelHandler;
    }
    @Override
    public void render(Graphics g) {
        levelHandler.renderCustomOffset(g, 0);
    }

    @Override
    public void update() {
        levelHandler.update();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_F1: {
                GameState.setState(GameState.INGAME);
                break;
            }
        }
    }
}
