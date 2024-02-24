package UI;

import java.awt.image.BufferedImage;

public class EditButton extends Button{
    public EditButton(int x, int y, int width, int height, BufferedImage[] animations) {
        super(x, y, width, height);
        setAnimations(animations);
    }
}
