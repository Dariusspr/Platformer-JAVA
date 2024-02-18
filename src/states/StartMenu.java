package states;

import UI.PlayButton;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static utils.Constants.UI.*;
import static utils.Constants.UI.StartMenu.*;

public class StartMenu extends State implements StateHandler{

    boolean hoverOverPlay;
    private PlayButton playButton;
    public StartMenu(Game game) {
        super(game);
        playButton = new PlayButton(PLAY_BUTTON_POSX, PLAY_BUTTON_POSY, BUTTON_ANIM_WIDTH, BUTTON_ANIM_HEIGHT, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
    }


    @Override
    public void render(Graphics g) {
            playButton.render(g);
    }


    @Override
    public void update() {
        if (!hoverOverPlay)
            playButton.update();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (hoverOverPlay) {
            GameState.setState(GameState.MENU);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if((hoverOverPlay = playButton.onButton(e.getX(), e.getY()))) {
            playButton.buttonDown();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
