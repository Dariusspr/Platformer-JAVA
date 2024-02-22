package objects;

import java.awt.*;

import java.awt.image.BufferedImage;

import static utils.Constants.Fruit.*;
import static utils.Load.loadImage;

public abstract class Fruit extends Object {
    private boolean exists = true;
    private int animationIndex = 0;
    private int animationTick = 0;
    private BufferedImage[] animations;

    protected Fruit(float x, float y) {
        super(x, y, FRUIT_SIZE, FRUIT_SIZE, FRUIT_HITBOX_WIDTH, FRUIT_HITBOX_HEIGHT, FRUIT_WIDTH_OFFSET, FRUIT_HEIGHT_OFFSET);
    }

    protected void loadAnimations(String path) {
        BufferedImage img = loadImage(path);
        animations = new BufferedImage[FRUIT_ANIMATION_LENGTH];
        for (int i = 0; i < FRUIT_ANIMATION_LENGTH; i++)
        {
            animations[i] =  img.getSubimage(i * FRUIT_ANIM_SIZE, 0 , FRUIT_ANIM_SIZE, FRUIT_ANIM_SIZE);
        }

    }

    public void setAnimations(BufferedImage[] animations) {
        this.animations = animations;
    }

    public BufferedImage[] getAnimations() {
        return animations;
    }

    public void render(Graphics g) {
        if (exists) {
            super.render(animations[animationIndex], g);
        }
    }
    public void render(Graphics g, int offset) {
        super.renderCustomOffset(animations[animationIndex], g, offset);
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
