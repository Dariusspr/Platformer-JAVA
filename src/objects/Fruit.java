package objects;

import java.awt.*;

import java.awt.image.BufferedImage;

import static utils.Constants.Fruit.*;

public class Fruit extends Object {
    private boolean exists = true;
    private int animationIndex = 0;
    private int animationTick = 0;
    private BufferedImage[] animations;

    public Fruit(float x, float y, BufferedImage[] animations){
        super(x, y, FRUIT_SIZE, FRUIT_SIZE, FRUIT_HITBOX_WIDTH, FRUIT_HITBOX_HEIGHT, FRUIT_WIDTH_OFFSET, FRUIT_HEIGHT_OFFSET);
        setAnimations(animations);
    }

    private void setAnimations(BufferedImage[] animations) {
        this.animations = animations;
    }

    public void render(Graphics g) {
        if (exists) {
            super.render(animations[animationIndex], g);
        }
    }
    public void render(Graphics g, int offset) {
        super.render(animations[animationIndex], g, offset);
    }

    public void setCollected() {
        exists = false;
    }

    public void setCollectable() {
        exists = true;
    }

    public void update() {
        if (exists) {
            updateAnimation();
            updateHitbox();
        }
    }

    public boolean isCollectable() {
        return exists;
    }

    public void updateAnimation() {
        animationTick++;
        if (animationTick >= FRUIT_ANIMATION_SPEED)
        {
            animationTick = 0;
            animationIndex = (animationIndex + 1) % FRUIT_ANIMATION_LENGTH;
        }
    }
}
