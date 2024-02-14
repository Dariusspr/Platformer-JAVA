package main;

import utils.Constants;

import static utils.Constants.Game.*;

public class Game implements  Runnable{

    public Game(){
        GamePanel gamePanel = new GamePanel();
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
                // reupdate
            }
            if (deltaFps >= 1.0) { // 1 frame
                deltaFps--;
                fpsCount++;
                // repaint
            }

            if (currentTime - lastSecTime >= SEC_TO_NANO) {
                System.out.println("FPS: " + fpsCount + " UPS: " + upsCount);
                lastSecTime = currentTime;
                fpsCount = 0;
                upsCount = 0;
            }
        }
    }
}
