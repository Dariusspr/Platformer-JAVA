package UI;

import states.Ingame;

import java.awt.*;

import static utils.Constants.UI.Pause.*;

public abstract class IngameUI {

    public RestartButton restartButton;
    public ExitButton exitButton;
    public SaveButton saveButton;
    public boolean onRestart, onExit, onSave;
    protected IngameUI(int resX, int resY, int saveX, int saveY, int exitX, int exitY) {
        restartButton = new RestartButton(resX, resY, BUTTON_WIDTH, BUTTON_HEIGHT);
        saveButton = new SaveButton(saveX, saveY, BUTTON_WIDTH, BUTTON_HEIGHT);
        exitButton = new ExitButton(exitX, exitY,BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    public void render(Graphics g) {
        exitButton.render(g);
        restartButton.render(g);
        saveButton.render(g);
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

        if (onSave) {
            saveButton.buttonDown();
        }
        else {
            saveButton.buttonUp();
        }
    }
}

