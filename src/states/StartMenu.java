package states;

import UI.Button;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static utils.Constants.UI.StartMenu.*;

public class StartMenu extends State implements StateHandler{

    boolean onPlay, onExit;
    private Button playButton;
    private Button exitButton;
    public StartMenu(Game game) {
        super(game);
        playButton = new Button(PLAY_BUTTON_POSX, PLAY_BUTTON_POSY, STARTMENU_BUTTON_WIDTH, STARTMENU_BUTTON_HEIGHT, game.getAssetsManager().getPlayButtonAnimations());
        exitButton = new Button(EXIT_BUTTON_POSX, EXIT_BUTTON_POSY, STARTMENU_BUTTON_WIDTH, STARTMENU_BUTTON_HEIGHT, game.getAssetsManager().getExitButtonAnimations());
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
            game.quit();
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
        else {
            exitButton.buttonUp();
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
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            GameState.setState(GameState.MENU);
        }
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            game.quit();
        }
    }
}
