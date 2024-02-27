package UI;

import states.Ingame;
import utils.Constants;

import java.awt.*;

import static utils.Constants.UI.Pause.*;
import static utils.Constants.Game.*;



public class LoseUI extends IngameUI{

    private final String LostMessage = "you lost!";
    Text lostText;
    public LoseUI(Ingame ingame) {
        super(RESTART_BUTTON_POSX, RESTART_BUTTON_POSY, EXIT_BUTTON_POSX, EXIT_BUTTON_POSY, ingame);
        lostText = new Text(LostMessage,  (int) (0.03f * PANEL_WIDTH), (int) (PANEL_WIDTH * 0.47f), (int) (PANEL_HEIGHT * 0.2f), ingame.getGame().getAssetsManager().getBlackText());
    }
    public void render(Graphics g) {
        super.render(g);
        lostText.render(g);
    }
}
