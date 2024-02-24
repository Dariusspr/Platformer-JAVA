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

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Ingame extends State implements StateHandler{
    private Player player;
    private LevelManager levelManager;
    private PauseUI pauseUi;
    private WinUI winUI;
    private LoseUI loseUI;
    private int offsetWidthRender = 0;

    private double lastTimeCheck = 0;
    private double currentTime = 0.0f;
    Text time;

    public Ingame(Game game) {
        super(game);
        levelManager = new LevelManager(this);
        player = new Player(levelManager.getPlayerX(), levelManager.getPlayerY(),game.getAssetsManager().getPlayerAnimations(), this);
        pauseUi = new PauseUI(this);
        loseUI = new LoseUI(this);
        winUI = new WinUI(this);
        time = new Text("00000", (int) (PANEL_WIDTH * 0.05), (int) (PANEL_WIDTH * 0.5f), (int) (PANEL_HEIGHT * 0.1f), game.getAssetsManager().getWhiteText());
    }

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

    public void render(Graphics g, int offset) {
        levelManager.render(g, offset);
        player.render(g, offset);
    }

    public void setLastTimeCheck() {
        lastTimeCheck = System.currentTimeMillis();
    }

    public void resetLevel() {
        player.reset();
        levelManager.resetLevel();
        currentTime = 0.0f;
        lastTimeCheck = System.currentTimeMillis();
    }

    @Override
    public void update() {
        Level.LevelState currentGameState = levelManager.getCurrentLevel().getLevelState();
        switch (currentGameState) {
            case WON:
                levelManager.updateLevelBestTime((float) currentTime);
                winUI.setBestTimeMessage(getLevelHandler().getCurrentLevel().getLevelBestTime());
                winUI.setCurrentTime(getCurrentTime());
                winUI.update();
                break;
            case LOST:
                loseUI.update();
                break;
            case PAUSED:
                pauseUi.update();
                break;
            case OTHER:
                levelManager.update();
                player.update();
                updateOffsetRender();
                updateTimer();
                break;
        }
    }

    private void updateTimer() {
        currentTime += ((System.currentTimeMillis() - lastTimeCheck) / 1000.0f);
        lastTimeCheck = System.currentTimeMillis();
        time.updateText(String.format("%.2f", currentTime));
    }

    private float getCurrentTime() {
        return (float) currentTime;
    }


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
                    levelManager.getCurrentLevel().setLevelState(Level.LevelState.OTHER);
                    resetLevel();
                }
                else if (winUI.onSave) {
                    // save changes
                }
            }
            case LOST -> {
                if (loseUI.onExit) {
                    GameState.setState(GameState.MENU);
                }
                else if (loseUI.onRestart) {
                    levelManager.getCurrentLevel().setLevelState(Level.LevelState.OTHER);
                    resetLevel();
                }
                else if (loseUI.onSave) {
                    // save changes
                }
            }
            case PAUSED -> {
                if (pauseUi.onExit) {
                    GameState.setState(GameState.MENU);
                }
                else if (pauseUi.onRestart) {
                    levelManager.getCurrentLevel().setLevelState(Level.LevelState.OTHER);
                    resetLevel();
                }
                else if (pauseUi.onSave) {
                    // save changes
                }
            }
            case OTHER -> {
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
            case OTHER -> {
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (levelManager.getCurrentLevel().getLevelState() == Level.LevelState.OTHER) {
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
            case WON -> {
            }
            case LOST -> {
            }
            case PAUSED -> {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ESCAPE:
                        levelManager.getCurrentLevel().setLevelState(Level.LevelState.OTHER);
                        lastTimeCheck = System.currentTimeMillis();
                        break;
                }
            }
            case OTHER -> {
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
                        // render save, exit
                        break;
                }
            }
        }
    }

    public Player getPlayer() {
        return player;
    }
    public LevelManager getLevelHandler() {
        return levelManager;
    }
}
