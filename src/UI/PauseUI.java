package UI;

import states.Ingame;

import java.awt.*;

import static utils.Constants.Game.PANEL_HEIGHT;
import static utils.Constants.Game.PANEL_WIDTH;
import static utils.Constants.UI.Pause.*;

public class PauseUI extends IngameUI {

    private final String pausedMessage = "paused";
    Text pausedText;
    public PauseUI(Ingame ingame) {
        super(RESTART_BUTTON_POSX, RESTART_BUTTON_POSY, EXIT_BUTTON_POSX, EXIT_BUTTON_POSY, ingame);
        pausedText = new Text(pausedMessage, (int) (0.03f * PANEL_WIDTH), (int) (PANEL_WIDTH * 0.47f), (int) (PANEL_HEIGHT * 0.2f), ingame.getGame().getAssetsManager().getBlackText());
    }

    public void render(Graphics g) {
        super.render(g);
        pausedText.render(g);
    }
}

