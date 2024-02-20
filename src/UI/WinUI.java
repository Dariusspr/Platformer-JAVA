package UI;

import java.awt.*;

import static utils.Constants.Game.PANEL_HEIGHT;
import static utils.Constants.Game.PANEL_WIDTH;
import static utils.Constants.UI.Pause.*;
import static utils.Constants.UI.Pause.EXIT_BUTTON_POSY;

public class WinUI extends  IngameUI{

    private String winMessage = "you won!";
    private String currTimeMessage = "current time: ";
    private String bestTimeMessage = "best time: ";
    private float currentTime;
    public float bestTime;
    Text winText;
    Text currText;
    Text bestText;
    public WinUI() {
        super(RESTART_BUTTON_POSX, RESTART_BUTTON_POSY, SAVE_BUTTON_POSX, SAVE_BUTTON_POSY, EXIT_BUTTON_POSX, EXIT_BUTTON_POSY);
        winText = new Text(winMessage, (int)(0.03f * PANEL_WIDTH), (int) (PANEL_WIDTH * 0.47f), (int) (PANEL_HEIGHT * 0.2f), 'b');
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
        bestText = new Text(bestTimeMessage + String.format("%.2f", bestTime), (int)(0.03f * PANEL_WIDTH), (int) (PANEL_WIDTH * 0.47f), (int) (PANEL_HEIGHT * 0.7f), 'w');
        currText = new Text(currTimeMessage + String.format("%.2f", currentTime), (int)(0.03f * PANEL_WIDTH), (int) (PANEL_WIDTH * 0.47f), (int) (PANEL_HEIGHT * 0.8f), 'w');
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
