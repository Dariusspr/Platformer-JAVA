package states;

import UI.Banner;
import UI.EditButton;
import UI.PlayButton;
import levels.Level;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static utils.Constants.UI.BUTTON_ANIM_HEIGHT;
import static utils.Constants.UI.BUTTON_ANIM_WIDTH;
import static utils.Constants.UI.Menu.*;


public class Menu extends State implements  StateHandler{

    private Level[] levels;
    private Banner[] nameBanners;
    private Banner[] timeBanners;
    private Banner empty;
    private int currentLevel = 0;
    PlayButton playButton;
    EditButton editButton;
    private boolean hoverOnPlay, hoverOnEdit;
    public Menu(Game game) {
        super(game);
        levels = game.getIngame().getLevelHandler().getAllLevels();
        setUpBanners();
        playButton = new PlayButton(PLAY_BUTTON_POSX, PLAY_BUTTON_POSY, BUTTON_ANIM_WIDTH, BUTTON_ANIM_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);
        editButton = new EditButton(EDIT_BUTTON_POSX, EDIT_BUTTON_POSY, BUTTON_ANIM_WIDTH, BUTTON_ANIM_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    private void setUpBanners() {
        empty = new Banner("No levels", NAME_BANNER_X, NAME_BANNER_Y, BANNER_WIDTH, BANNER_HEIGHT);
        nameBanners = new Banner[levels.length];
        timeBanners = new Banner[levels.length];
        for (int i = 0; i < levels.length; i++) {
            nameBanners[i] = new Banner(levels[i].getLevelName(), NAME_BANNER_X, NAME_BANNER_Y, BANNER_WIDTH, BANNER_HEIGHT);
            timeBanners[i] = new Banner(Float.toString(levels[i].getLevelBestTime()), TIME_BANNER_X, TIME_BANNER_Y, BANNER_WIDTH, BANNER_HEIGHT);
        }
    }

    @Override
    public void render(Graphics g) {
        if (levels.length != 0) {
            nameBanners[currentLevel].render(g);
            timeBanners[currentLevel].render(g);
        }
        else {
            empty.render(g);
        }

        playButton.render(g);
        editButton.render(g);
    }

    @Override
    public void update() {
        if(!hoverOnPlay) {
            playButton.update();
        }
        if (!hoverOnEdit) {
            editButton.update();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (hoverOnPlay) {
            game.getIngame().getLevelHandler().setCurrentLevel(currentLevel);
            GameState.setState(GameState.INGAME);
        }
        if (hoverOnEdit) {
            game.getIngame().getLevelHandler().setCurrentLevel(currentLevel);
            GameState.setState(GameState.EDITOR);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if((hoverOnPlay = playButton.onButton(e.getX(), e.getY()))) {
            playButton.buttonDown();
        }
        else if((hoverOnEdit = editButton.onButton(e.getX(), e.getY()))) {
            editButton.buttonDown();
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
        switch (e.getKeyCode()) {
            case KeyEvent.VK_D, KeyEvent.VK_SPACE, KeyEvent.VK_KP_RIGHT:
                currentLevel = Math.min(levels.length - 1, currentLevel + 1);
                break;
            case KeyEvent.VK_A, KeyEvent.VK_KP_LEFT:
                currentLevel = Math.max(currentLevel - 1, 0);

        }
    }
}
