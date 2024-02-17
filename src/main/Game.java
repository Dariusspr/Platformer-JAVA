package main;

import states.GameState;
import states.Ingame;
import states.StartMenu;

import java.awt.*;

import static utils.Constants.Game.*;

public class Game implements  Runnable{
    private GamePanel gamePanel;

    private Ingame ingame;
    private StartMenu startMenu;

    public Game(){
        ingame = new Ingame(this);
        startMenu = new StartMenu(this);
        gamePanel = new GamePanel(this);
        new GameWindow(gamePanel);

        gamePanel.requestFocus();

        startGame();
    }

    private void startGame() {
        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double timePerFrame = (double) SEC_TO_NANO / FPS_COUNT;
        double timePerUpdate = (double) SEC_TO_NANO / UPS_COUNT;

        int fpsCount = 0;
        int upsCount = 0;

        long previousTime =  System.nanoTime();
        long currentTime;
        long lastSecTime = System.nanoTime();

        double deltaUps = 0;
        double deltaFps = 0;
        while(true) {

            currentTime = System.nanoTime();
            deltaFps += (currentTime - previousTime) / timePerFrame;
            deltaUps += (currentTime - previousTime) / timePerUpdate;
            previousTime = currentTime;

            if (deltaUps >= 1.0) { // 1 update
                deltaUps--;
                upsCount++;
                update();
            }
            if (deltaFps >= 1.0) { // 1 frame
                deltaFps--;
                fpsCount++;
                gamePanel.repaint();
            }

            if (currentTime - lastSecTime >= SEC_TO_NANO) {
                System.out.println("FPS: " + fpsCount + " UPS: " + upsCount);
                lastSecTime = currentTime;
                fpsCount = 0;
                upsCount = 0;
            }
        }
    }

    public void render(Graphics g) {
        switch (GameState.state) {
            case START_MENU:
                startMenu.render(g);
                break;
            case INGAME:
               ingame.render(g);
                break;
            case EDITOR:
                break;
        }
    }

    private void update() {
        switch (GameState.state) {
            case START_MENU:
                startMenu.update();
                break;
            case INGAME:
                ingame.update();
                break;
            case EDITOR:
                break;
        }
    }
    public Ingame getIngame() {
        return ingame;
    }
    public StartMenu getStartMenu() {
        return startMenu;
    }

    public void setState(GameState state) {
        switch (state) {
            case START_MENU:
                GameState.state = state;
                break;
            case MENU:
                break;
            case INGAME:
                if (GameState.state == GameState.EDITOR) {
                    LEVEL_SCALE = 1.0f;
                }
                GameState.state = state;
                break;
            case EDITOR:
                LEVEL_SCALE = 0.5f;
                GameState.state = state;
                break;
            case PAUSED:
                break;
        }

    }
}
