package UI;

import states.Ingame;

import java.awt.*;

import static utils.Constants.UI.Pause.*;

/**
 * Ingame User interface for displaying options (ex. exit, restart...).
 */
public abstract class IngameUI {

    public Button restartButton;
    public Button exitButton;
    public boolean onRestart, onExit;
    /**
     * Initializes the in-game UI with buttons for restart and exit.
     * @param resX The x-coordinate for the restart button.
     * @param resY The y-coordinate for the restart button.
     * @param exitX The x-coordinate for the exit button.
     * @param exitY The y-coordinate for the exit button.
     * @param ingame The instance of the Ingame state.
     */
    protected IngameUI(int resX, int resY, int exitX, int exitY, Ingame ingame) {
        restartButton = new Button(resX, resY, BUTTON_WIDTH, BUTTON_HEIGHT, ingame.getGame().getAssetsManager().getRestartButtonAnimations());
        exitButton = new Button(exitX, exitY,BUTTON_WIDTH, BUTTON_HEIGHT, ingame.getGame().getAssetsManager().getExitButtonAnimations());
    }
    /**
     * Renders the in-game UI components.
     * @param g The Graphics object.
     */
    public void render(Graphics g) {
        exitButton.render(g);
        restartButton.render(g);
    }
    /**
     * Updates buttons' animations based mouse placement relative to it
     */
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

