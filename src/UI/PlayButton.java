package UI;

import java.awt.image.BufferedImage;


public class PlayButton extends Button{

    public PlayButton(int x, int y, int width, int height, BufferedImage[] animations) {
        super(x,y,width, height);
        setAnimations(animations);
    }

}
