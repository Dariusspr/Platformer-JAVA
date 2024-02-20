package states;

import UI.ExitButton;
import UI.PlayButton;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static utils.Constants.UI.StartMenu.*;

public class StartMenu extends State implements StateHandler{

    boolean onPlay, onExit;
    private PlayButton playButton;
    private ExitButton exitButton;
    public StartMenu(Game game) {
        super(game);
        playButton = new PlayButton(PLAY_BUTTON_POSX, PLAY_BUTTON_POSY, STARTMENU_BUTTON_WIDTH, STARTMENU_BUTTON_HEIGHT);
        exitButton = new ExitButton(EXIT_BUTTON_POSX, EXIT_BUTTON_POSY, STARTMENU_BUTTON_WIDTH, STARTMENU_BUTTON_HEIGHT);
    }


    @Override
    public void render(Graphics g) {
            playButton.render(g);
            exitButton.render(g);
    }


    @Override
    public void update() {
        if (!onPlay)
            playButton.update();
        if (!onExit)
            exitButton.update();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (onPlay) {
            GameState.setState(GameState.MENU);
        }
        if (onExit) {
            // TODO: exit
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if((onPlay = playButton.onButton(e.getX(), e.getY()))) {
            playButton.buttonDown();
        }
        if((onExit = exitButton.onButton(e.getX(), e.getY()))) {
            exitButton.buttonDown();
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
