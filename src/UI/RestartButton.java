package UI;


import java.awt.image.BufferedImage;


public class RestartButton extends  Button{
    public RestartButton(int x, int y, int width, int height, BufferedImage[] animations) {
        super(x,y,width, height);
        setAnimations(animations);
    }
}
