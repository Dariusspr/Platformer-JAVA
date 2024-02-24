package UI;

import states.Ingame;

import java.awt.*;

import static utils.Constants.UI.Pause.*;

public abstract class IngameUI {

    public RestartButton restartButton;
    public ExitButton exitButton;
    public boolean onRestart, onExit, onSave;
    protected IngameUI(int resX, int resY, int exitX, int exitY, Ingame ingame) {
        restartButton = new RestartButton(resX, resY, BUTTON_WIDTH, BUTTON_HEIGHT, ingame.getGame().getAssetsManager().getRestartButtonAnimations());
        exitButton = new ExitButton(exitX, exitY,BUTTON_WIDTH, BUTTON_HEIGHT, ingame.getGame().getAssetsManager().getExitButtonAnimations());
    }

    public void render(Graphics g) {
        exitButton.render(g);
        restartButton.render(g);
    }
    public void update() {
        if (onExit) {
            exitButton.buttonDown();
        }
        else {
            exitButton.buttonUp();
        }

        if (onRestart) {
            restartButton.buttonDown();
        }
        else {
            restartButton.buttonUp();
        }
    }
}

