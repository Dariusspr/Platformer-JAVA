package UI;

import states.Ingame;

import java.awt.*;

import static utils.Constants.Game.PANEL_HEIGHT;
import static utils.Constants.Game.PANEL_WIDTH;
import static utils.Constants.UI.Pause.*;
import static utils.Constants.UI.Pause.EXIT_BUTTON_POSY;
import static utils.Constants.UI.TIME_FORMAT;
/**
 * User interface for displaying options and level information when the player wins the game.
 */
public class WinUI extends  IngameUI{

    private final String currTimeMessage = "current time: ";
    private final String bestTimeMessage = "best time: ";
    private float currentTime;
    public float bestTime;
    Text winText;
    Text currText;
    Text bestText;
    /**
     * Constructs the WinUI object.
     * @param ingame The Ingame state.
     */
    public WinUI(Ingame ingame) {
        super(RESTART_BUTTON_POSX, RESTART_BUTTON_POSY, EXIT_BUTTON_POSX, EXIT_BUTTON_POSY, ingame);
        String winMessage = "you won!";
        winText = new Text(winMessage, (int)(0.03f * PANEL_WIDTH), (int) (PANEL_WIDTH * 0.47f), (int) (PANEL_HEIGHT * 0.2f), ingame.getGame().getAssetsManager().getBlackText());
        currText = new Text(currTimeMessage + String.format(TIME_FORMAT, currentTime), (int)(0.03f * PANEL_WIDTH), (int) (PANEL_WIDTH * 0.47f), (int) (PANEL_HEIGHT * 0.8f),  ingame.getGame().getAssetsManager().getWhiteText());
        bestText = new Text(bestTimeMessage + String.format(TIME_FORMAT, bestTime), (int)(0.03f * PANEL_WIDTH), (int) (PANEL_WIDTH * 0.47f), (int) (PANEL_HEIGHT * 0.7f), ingame.getGame().getAssetsManager().getWhiteText());
    }

    /**
     * @param time current attempt's time to be displayed
     */
    public void setCurrentTime(float time) {
        currentTime = time;
    }

    /**
      * @param time new best time to be displayed
     */
    public void setBestTimeMessage(float time) {
        bestTime = time;
    }

    /**
     * Updates the UI components.
     */
    public void update() {
        super.update(); // Call the update method of the superclass
        updateText(); // Update the text components
    }

    /**
     * Updates the text components with the current time values.
     */
    private void updateText() {
        bestText.changeText(bestTimeMessage + String.format(TIME_FORMAT, bestTime));
        currText.changeText(currTimeMessage + String.format(TIME_FORMAT, currentTime));
    }
    /**
     * Renders the WinUI - buttons and text.
     * @param g The Graphics object.
     */
    public void render(Graphics g) {
        if (bestText != null && currText != null) {
            super.render(g);
            winText.render(g);
            currText.render(g);
            bestText.render(g);
        }
    }


}
