package states;

import UI.Banner;
import UI.Button;
import levels.Level;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static utils.Constants.UI.Menu.*;
import static utils.Constants.UI.TIME_FORMAT;


public class Menu extends State implements  StateHandler{

    private final String NO_LEVELS_TEXT = "No levels";
    private Level[] levels;
    private Banner[] nameBanners;
    private Banner[] timeBanners;
    private Banner empty;
    private int currentLevel = 0;
    private Button playButton;
    private Button editButton;
    private Button exitButton;
    private boolean onPlay, onEdit, onExit;
    private int bannerIndex = 0;
    public Menu(Game game) {
        super(game);
        levels = game.getIngame().getLevelManager().getAllLevels();
        setUpBanners();
        playButton = new Button(PLAY_BUTTON_POSX, PLAY_BUTTON_POSY, BUTTON_WIDTH, BUTTON_HEIGHT, game.getAssetsManager().getPlayButtonAnimations());
        editButton = new Button(EDIT_BUTTON_POSX, EDIT_BUTTON_POSY, BUTTON_WIDTH, BUTTON_HEIGHT, game.getAssetsManager().getEditButtonAnimations());
        exitButton = new Button(EXIT_BUTTON_POSX, EXIT_BUTTON_POSY,BUTTON_WIDTH, BUTTON_HEIGHT, game.getAssetsManager().getExitButtonAnimations());
    }

    private void setUpBanners() {
        empty = new Banner(NO_LEVELS_TEXT, NAME_BANNER_X, NAME_BANNER_Y, BANNER_WIDTH, BANNER_HEIGHT, game.getAssetsManager().getBannerImg(), game);
        nameBanners = new Banner[levels.length];
        timeBanners = new Banner[levels.length];
        for (int i = 0; i < game.getIngame().getLevelManager().getLevelCount(); i++) {
            createBanner(levels[i].getLevelName(), levels[i].getLevelBestTime());
        }
    }
    public void createBanner(String name, float time) {
        nameBanners[bannerIndex] = new Banner(name, NAME_BANNER_X, NAME_BANNER_Y, BANNER_WIDTH, BANNER_HEIGHT, game.getAssetsManager().getBannerImg(), game);
        if (time >= MAX_BEST_TIME) {
            timeBanners[bannerIndex] = new Banner(NO_TIME, TIME_BANNER_X, TIME_BANNER_Y, BANNER_WIDTH, BANNER_HEIGHT, game.getAssetsManager().getBannerImg(), game);
        }
        else {
            timeBanners[bannerIndex] = new Banner(String.format(TIME_FORMAT, time), TIME_BANNER_X, TIME_BANNER_Y, BANNER_WIDTH, BANNER_HEIGHT, game.getAssetsManager().getBannerImg(), game);
        }
        bannerIndex++;
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
        for (int i = 0; i < game.getIngame().getLevelManager().getLevelCount(); i++) {
            nameBanners[i].changeBannerText(levels[i].getLevelName());
            float time  = levels[i].getLevelBestTime();
            if (time >= MAX_BEST_TIME) {
                timeBanners[i].changeBannerText(NO_TIME);
            }
            else {
                timeBanners[i].changeBannerText(String.format(TIME_FORMAT, levels[i].getLevelBestTime()));
            }
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
            game.getIngame().getLevelManager().changeLevel(currentLevel);
            GameState.setState(GameState.INGAME);
        }
        else if (onEdit) {
            game.getIngame().getLevelManager().changeLevel(currentLevel);
            game.getIngame().getLevelManager().getCurrentLevel().setLevelState(Level.LevelState.PAUSED);
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
            case KeyEvent.VK_D, KeyEvent.VK_KP_RIGHT:
                currentLevel = Math.min(game.getIngame().getLevelManager().getLevelCount() - 1, currentLevel + 1);
                break;
            case KeyEvent.VK_A, KeyEvent.VK_KP_LEFT:
                currentLevel = Math.max(currentLevel - 1, 0);
                break;
            case KeyEvent.VK_ENTER:
                game.getIngame().getLevelManager().changeLevel(currentLevel);
                GameState.setState(GameState.INGAME);
                break;
            case KeyEvent.VK_ESCAPE:
                GameState.setState(GameState.START_MENU);
                break;
        }
    }
}
