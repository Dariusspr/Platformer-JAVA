package states;

import UI.Banner;
import UI.EditButton;
import UI.ExitButton;
import UI.PlayButton;
import levels.Level;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static utils.Constants.UI.Menu.*;


public class Menu extends State implements  StateHandler{

    private Level[] levels;
    private Banner[] nameBanners;
    private Banner[] timeBanners;
    private Banner empty;
    private int currentLevel = 0;
    private PlayButton playButton;
    private EditButton editButton;
    private ExitButton exitButton;
    private boolean onPlay, onEdit, onExit;
    public Menu(Game game) {
        super(game);
        levels = game.getIngame().getLevelHandler().getAllLevels();
        setUpBanners();
        playButton = new PlayButton(PLAY_BUTTON_POSX, PLAY_BUTTON_POSY, BUTTON_WIDTH, BUTTON_HEIGHT, game.getAssetsManager().getPlayButtonAnimations());
        editButton = new EditButton(EDIT_BUTTON_POSX, EDIT_BUTTON_POSY, BUTTON_WIDTH, BUTTON_HEIGHT, game.getAssetsManager().getEditButtonAnimations());
        exitButton = new ExitButton(EXIT_BUTTON_POSX, EXIT_BUTTON_POSY,BUTTON_WIDTH, BUTTON_HEIGHT, game.getAssetsManager().getExitButtonAnimations());
    }

    private void setUpBanners() {
        empty = new Banner("No levels", NAME_BANNER_X, NAME_BANNER_Y, BANNER_WIDTH, BANNER_HEIGHT, game.getAssetsManager().getBannerImg(), game);
        nameBanners = new Banner[levels.length];
        timeBanners = new Banner[levels.length];
        for (int i = 0; i < game.getIngame().getLevelHandler().getLevelCount(); i++) {
            nameBanners[i] = new Banner(levels[i].getLevelName(), NAME_BANNER_X, NAME_BANNER_Y, BANNER_WIDTH, BANNER_HEIGHT, game.getAssetsManager().getBannerImg(), game);
            timeBanners[i] = new Banner(String.format("%.2f", levels[i].getLevelBestTime()), TIME_BANNER_X, TIME_BANNER_Y, BANNER_WIDTH, BANNER_HEIGHT, game.getAssetsManager().getBannerImg(), game);
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
        exitButton.render(g);
    }

    public void resetMenu() {
        onExit =  false;
        onEdit = false;
        onPlay = false;
        updateInfo();
    }

    public void updateInfo() {
        for (int i = 0; i < game.getIngame().getLevelHandler().getLevelCount(); i++) {
            nameBanners[i].changeBannerText(levels[i].getLevelName());
            timeBanners[i].changeBannerText(String.format("%.2f", levels[i].getLevelBestTime()));
        }
    }

    @Override
    public void update() {
        updateInfo();
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
            game.getIngame().getLevelHandler().setCurrentLevelIndex(currentLevel);
            GameState.setState(GameState.INGAME);
        }
        else if (onEdit) {
            game.getIngame().getLevelHandler().setCurrentLevelIndex(currentLevel);
            game.getIngame().getLevelHandler().getCurrentLevel().setLevelState(Level.LevelState.PAUSED);
            GameState.setState(GameState.EDITOR);
        }
        else if (onExit) {
            GameState.setState(GameState.START_MENU);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(onPlay = playButton.onButton(e.getX(), e.getY())) {
            playButton.buttonDown();
        }
        else {
            playButton.buttonUp();
        }

        if(onEdit = editButton.onButton(e.getX(), e.getY())) {
            editButton.buttonDown();
        }
        else {
            editButton.buttonUp();
        }

        if (onExit = exitButton.onButton(e.getX(), e.getY())) {
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
        switch (e.getKeyCode()) {
            case KeyEvent.VK_D, KeyEvent.VK_SPACE, KeyEvent.VK_KP_RIGHT:
                currentLevel = Math.min(game.getIngame().getLevelHandler().getLevelCount() - 1, currentLevel + 1);
                break;
            case KeyEvent.VK_A, KeyEvent.VK_KP_LEFT:
                currentLevel = Math.max(currentLevel - 1, 0);
        }
    }
}
