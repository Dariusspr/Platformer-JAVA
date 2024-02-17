package UI;

import utils.Constants;

import java.awt.*;

import static utils.Constants.UI.StartMenu.PLAYBUTTON_PATH;

public class PlayButton extends Button{

    public PlayButton(int x, int y, int animWidth, int animHeight, int width, int height) {
        super(x,y, animWidth, animHeight, width, height, PLAYBUTTON_PATH);
    }

}
