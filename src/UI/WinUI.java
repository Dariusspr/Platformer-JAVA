package UI;

import states.Ingame;

import java.awt.*;

import static utils.Constants.Game.PANEL_HEIGHT;
import static utils.Constants.Game.PANEL_WIDTH;
import static utils.Constants.UI.Pause.*;
import static utils.Constants.UI.Pause.EXIT_BUTTON_POSY;
import static utils.Constants.UI.TIME_FORMAT;

public class WinUI extends  IngameUI{

    private final String winMessage = "you won!";
    private final String currTimeMessage = "current time: ";
    private final String bestTimeMessage = "best time: ";
    private float currentTime;
    public float bestTime;
    Text winText;
    Text currText;
    Text bestText;
    public WinUI(Ingame ingame) {
        super(RESTART_BUTTON_POSX, RESTART_BUTTON_POSY, EXIT_BUTTON_POSX, EXIT_BUTTON_POSY, ingame);
        winText = new Text(winMessage, (int)(0.03f * PANEL_WIDTH), (int) (PANEL_WIDTH * 0.47f), (int) (PANEL_HEIGHT * 0.2f), ingame.getGame().getAssetsManager().getBlackText());
        currText = new Text(currTimeMessage + String.format(TIME_FORMAT, currentTime), (int)(0.03f * PANEL_WIDTH), (int) (PANEL_WIDTH * 0.47f), (int) (PANEL_HEIGHT * 0.8f),  ingame.getGame().getAssetsManager().getWhiteText());
        bestText = new Text(bestTimeMessage + String.format(TIME_FORMAT, bestTime), (int)(0.03f * PANEL_WIDTH), (int) (PANEL_WIDTH * 0.47f), (int) (PANEL_HEIGHT * 0.7f), ingame.getGame().getAssetsManager().getWhiteText());
    }

    public void setCurrentTime(float time) {
        currentTime = time;
    }

    public void setBestTimeMessage(float time) {
        bestTime = time;
    }

    public void update() {
        super.update();
        updateText();
    }

    private void updateText() {
        bestText.updateText(bestTimeMessage + String.format(TIME_FORMAT, bestTime));
        currText.updateText(currTimeMessage + String.format(TIME_FORMAT, currentTime));
    }

    public void render(Graphics g) {
        if (bestText != null && currText != null) {
            super.render(g);
            winText.render(g);
            currText.render(g);
            bestText.render(g);
        }
    }


}
