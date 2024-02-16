package main;

import Entities.Player;
import levels.LevelHandler;

import java.awt.*;

import static utils.Constants.Game.*;
import static utils.Constants.Player.*;

public class Game implements  Runnable{

    private Player player;
    private GamePanel gamePanel;
    private LevelHandler levelHandler;
    public Game(){
        player = new Player(200, 300, PLAYER_SIZE, PLAYER_SIZE, PLAYER_MOVE_SPEED,
                PLAYER_HITBOX_WIDTH, PLAYER_HITBOX_HEIGHT, PLAYER_WIDTH_OFFSET, PLAYER_HEIGHT_OFFSET,
                this);
        levelHandler = new LevelHandler();
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
        levelHandler.render(g);
        player.render(g);
    }

    private void update() {
        levelHandler.update();
        player.update();
    }

    public Player getPlayer() {
        return player;
    }
    public LevelHandler getLevelHandler() {
        return levelHandler;
    }
}
