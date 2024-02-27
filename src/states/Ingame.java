package states;

import UI.LoseUI;
import UI.PauseUI;
import UI.Text;
import UI.WinUI;
import levels.Level;
import objects.Player;
import levels.LevelManager;
import main.Game;

import static utils.Constants.Game.*;
import static utils.Constants.UI.TIME_FORMAT;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * The Ingame class represents the state of the game when the player is playing.
 * It handles rendering, updating, user input, and game logic.
 */
public class Ingame extends State implements StateHandler{
    private final Player player;
    private final LevelManager levelManager;
    private final PauseUI pauseUi;
    private final WinUI winUI;
    private final LoseUI loseUI;
    private int offsetWidthRender = 0;

    private double lastTimeCheck = 0;
    private double currentTime = 0.0f;
    Text time;

    /**
     * Constructs an Ingame object.
     * @param game The Game object.
     */
    public Ingame(Game game) {
        super(game);
        levelManager = new LevelManager(this);
        player = new Player(levelManager.getCurrentLevel().getPlayerX(), levelManager.getCurrentLevel().getPlayerY(),game.getAssetsManager().getPlayerAnimations(), this);
        pauseUi = new PauseUI(this);
        loseUI = new LoseUI(this);
        winUI = new WinUI(this);
        time = new Text("000000", (int) (PANEL_WIDTH * 0.05), (int) (PANEL_WIDTH * 0.5f), (int) (PANEL_HEIGHT * 0.1f), game.getAssetsManager().getWhiteText());
    }

    /**
     * Renders level, player and timer based on the current game state.
     * @param g The Graphics context.
     */
    @Override
    public void render(Graphics g) {

        levelManager.render(g);
        player.render(g);
        time.render(g);
        Level.LevelState currentGameState = levelManager.getCurrentLevel().getLevelState();
        switch (currentGameState) {
            case WON:
                winUI.render(g);
                break;
            case LOST:
                loseUI.render(g);
                break;
            case PAUSED:
                pauseUi.render(g);
                break;
        }
    }

    /**
     * Renders level, player and timer based on the current game state with render offset.
     * @param g The Graphics context.
     */
    public void render(Graphics g, int offset) {
        levelManager.render(g, offset);
        player.render(g, offset);
    }


    /**
     * Reset level to its initial state
     */
    public void resetLevel() {
        player.reset();
        levelManager.resetLevel();
        currentTime = 0.0f;
        lastTimeCheck = System.currentTimeMillis();
    }

    /**
     * Updates the game logic.
     */
    @Override
    public void update() {
        Level.LevelState currentGameState = levelManager.getCurrentLevel().getLevelState();
        switch (currentGameState) {
            case WON:
                levelManager.updateLevelBestTime((float) currentTime);
                winUI.setBestTimeMessage(getLevelManager().getCurrentLevel().getLevelBestTime());
                winUI.setCurrentTime(getCurrentTime());
                winUI.update();
                break;
            case LOST:
                loseUI.update();
                break;
            case PAUSED:
                pauseUi.update();
                break;
            case PLAYING:
                levelManager.update();
                player.update();
                updateOffsetRender();
                updateTimer();
                break;
        }
    }

    /**
     * Updates timer
     */
    private void updateTimer() {
        currentTime += ((System.currentTimeMillis() - lastTimeCheck) / 1000.0f);
        lastTimeCheck = System.currentTimeMillis();
        time.changeText(String.format(TIME_FORMAT, currentTime));
    }

    /**
     * @return time since player started playing the current level.
     */
    private float getCurrentTime() {
        return (float) currentTime;
    }

    /**
     * Updates the rendering offset based on the player's position.
     */
    private void updateOffsetRender() {
        int currentX = (int) player.getHitbox().x;
        int deltaOffset = currentX - offsetWidthRender;

        if (deltaOffset < LEFT_TILE_BORDER * TILE_SIZE) {
            offsetWidthRender += deltaOffset - LEFT_TILE_BORDER * TILE_SIZE;
        }
        else if (deltaOffset > RIGHT_TILE_BORDER * TILE_SIZE) {
            offsetWidthRender += deltaOffset - RIGHT_TILE_BORDER * TILE_SIZE;
        }

        if (offsetWidthRender < 0) {
            offsetWidthRender = 0;
        }
        else if (offsetWidthRender > MAX_TILE_OFFSET * TILE_SIZE) {
            offsetWidthRender = MAX_TILE_OFFSET * TILE_SIZE;
        }

        player.setOffsetRender(offsetWidthRender);
        levelManager.setRenderOffset(offsetWidthRender);
    }



    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (levelManager.getCurrentLevel().getLevelState()) {
            case WON -> {
                if (winUI.onExit) {
                    GameState.setState(GameState.MENU);
                }
                else if (winUI.onRestart) {
                    levelManager.getCurrentLevel().setLevelState(Level.LevelState.PLAYING);
                    resetLevel();
                }
            }
            case LOST -> {
                if (loseUI.onExit) {
                    GameState.setState(GameState.MENU);
                }
                else if (loseUI.onRestart) {
                    levelManager.getCurrentLevel().setLevelState(Level.LevelState.PLAYING);
                    resetLevel();
                }
            }
            case PAUSED -> {
                if (pauseUi.onExit) {
                    GameState.setState(GameState.MENU);
                }
                else if (pauseUi.onRestart) {
                    levelManager.getCurrentLevel().setLevelState(Level.LevelState.PLAYING);
                    resetLevel();
                }
            }
            case PLAYING -> {
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (levelManager.getCurrentLevel().getLevelState()) {
            case WON -> {
                winUI.onExit = pauseUi.exitButton.onButton(e.getX(), e.getY());
                winUI.onRestart = pauseUi.restartButton.onButton(e.getX(), e.getY());
            }
            case LOST -> {
                loseUI.onExit = pauseUi.exitButton.onButton(e.getX(), e.getY());
                loseUI.onRestart = pauseUi.restartButton.onButton(e.getX(), e.getY());
            }
            case PAUSED -> {
                pauseUi.onExit = pauseUi.exitButton.onButton(e.getX(), e.getY());
                pauseUi.onRestart = pauseUi.restartButton.onButton(e.getX(), e.getY());
            }
            case PLAYING -> {
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (levelManager.getCurrentLevel().getLevelState() == Level.LevelState.PLAYING) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_SPACE:
                case KeyEvent.VK_W:
                    player.setMovingUp(true);
                    break;
                case KeyEvent.VK_A:
                    player.setMovingLeft(true);
                    break;
                case KeyEvent.VK_D:
                    player.setMovingRight(true);
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (levelManager.getCurrentLevel().getLevelState()) {
            case PAUSED -> {

                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    levelManager.getCurrentLevel().setLevelState(Level.LevelState.PLAYING);
                    lastTimeCheck = System.currentTimeMillis();
                }
            }
            case PLAYING -> {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_SPACE:
                    case KeyEvent.VK_W:
                        player.setMovingUp(false);
                        break;
                    case KeyEvent.VK_A:
                        player.setMovingLeft(false);
                        break;
                    case KeyEvent.VK_D:
                        player.setMovingRight(false);
                        break;
                    case KeyEvent.VK_F1:
                        GameState.setState(GameState.EDITOR);
                        levelManager.getCurrentLevel().setLevelState(Level.LevelState.PAUSED);
                        break;
                    case KeyEvent.VK_ESCAPE:
                        levelManager.getCurrentLevel().setLevelState(Level.LevelState.PAUSED);
                        break;
                }
            }
        }
    }

    /**
     * Retrieves the player object.
     * @return The player object.
     */
    public Player getPlayer() {
        return player;
    }
    /**
     * Retrieves the levelManager object.
     * @return The LevelManager object.
     */
    public LevelManager getLevelManager() {
        return levelManager;
    }
}
