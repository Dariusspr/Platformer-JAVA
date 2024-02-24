package UI;

import java.awt.image.BufferedImage;


public class ExitButton extends  Button {
    public ExitButton(int x, int y, int width, int height, BufferedImage[] animations) {
        super(x,y, width, height);
        setAnimations(animations);
    }
}
