package objects;


import java.awt.image.BufferedImage;

public class Apple extends Fruit{

    public Apple(float x, float y, BufferedImage[] animations) {
        super(x, y);
        super.setAnimations(animations);
    }

}
