package main;

import states.Editor;
import states.GameState;
import states.Ingame;
import states.StartMenu;
import states.Menu;
import utils.AssetsManager;

import java.awt.*;

import static utils.Constants.Game.*;
import static utils.LoadSave.saveLevels;

public class Game implements Runnable {
    private final GameWindow gameWindow;
    private final GamePanel gamePanel;
    private final Ingame ingame;
    private final StartMenu startMenu;
    private final Editor editor;
    private final Menu menu;
    private final AssetsManager assetsManager;

    private boolean quit;
    public Game(){
        assetsManager = new AssetsManager();
        ingame = new Ingame(this);
        startMenu = new StartMenu(this);
        menu = new Menu(this);
        editor = new Editor(this, ingame.getLevelManager());
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);

        gamePanel.requestFocus();

        startGame();
    }

    private void startGame() {
        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double timePerFrame =  SEC_TO_NANO / FPS_COUNT;
        double timePerUpdate = SEC_TO_NANO / UPS_COUNT;

        int fpsCount = 0;

        long previousTime =  System.nanoTime();
        long currentTime;
        long lastSecTime = System.nanoTime();

        double deltaUps = 0;
        double deltaFps = 0;
        while(!quit) {

            currentTime = System.nanoTime();
            deltaFps += (currentTime - previousTime) / timePerFrame;
            deltaUps += (currentTime - previousTime) / timePerUpdate;
            previousTime = currentTime;

            if (deltaUps >= 1.0) { // Update game logic
                deltaUps--;
                update();
            }
            if (deltaFps >= 1.0) { // Draw frame
                deltaFps--;
                fpsCount++;
                gamePanel.repaint();
            }

            if (currentTime - lastSecTime >= SEC_TO_NANO) {
                System.out.println("FPS: " + fpsCount);
                lastSecTime = currentTime;
                fpsCount = 0;
            }
        }

        gameWindow.closeWindow();
    }

    public void render(Graphics g) {
        switch (GameState.state) {
            case START_MENU:
                startMenu.render(g);
                break;
            case MENU:
                menu.render(g);
                break;
            case INGAME:
               ingame.render(g);
                break;
            case EDITOR:
                editor.render(g);
                break;
        }
    }

    private void update() {
        switch (GameState.state) {
            case START_MENU:
                if (GameState.changed) {
                    GameState.changed = false;
                }
                startMenu.update();
                break;
            case MENU:
                if (GameState.changed) {
                    menu.resetMenu();
                    GameState.changed = false;
                }
                menu.update();
                break;
            case INGAME:
                if (GameState.changed) {
                    ingame.resetLevel();
                    GameState.changed = false;
                }
                ingame.update();
                break;
            case EDITOR:
                if (GameState.changed) {
                    ingame.resetLevel();
                    editor.resetEditor();
                    GameState.changed = false;
                }
                editor.update();
                break;
        }
    }
    public Ingame getIngame() {
        return ingame;
    }
    public StartMenu getStartMenu() {
        return startMenu;
    }
    public Editor getEditor() {
        return editor;
    }
    public Menu getMenu() {
        return menu;
    }
    public AssetsManager getAssetsManager() {
        return assetsManager;
    }

    public void quit() {
        System.out.println("Closing...");
        saveLevels(getIngame().getLevelManager().getAllLevels());
        quit = true;
    }
}
