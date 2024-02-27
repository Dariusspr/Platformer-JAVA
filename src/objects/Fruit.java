package objects;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.Fruit.*;

/**
 * Represents a fruit object in the game.
 */
public class Fruit extends Object {
    private boolean exists = true;
    private int animationIndex = 0;
    private int animationTick = 0;
    private BufferedImage[] animations;

    /**
     * Constructs a new Fruit object with the specified coordinates and animations.
     *
     * @param x           The x-coordinate of the fruit.
     * @param y           The y-coordinate of the fruit.
     * @param animations  The array of animation images for the fruit.
     */
    public Fruit(float x, float y, BufferedImage[] animations) {
        super(x, y, FRUIT_SIZE, FRUIT_SIZE, FRUIT_HITBOX_WIDTH, FRUIT_HITBOX_HEIGHT, FRUIT_WIDTH_OFFSET, FRUIT_HEIGHT_OFFSET);
        setAnimations(animations);
    }

    /**
     * Sets the animations for the fruit.
     *
     * @param animations The array of animation images for the fruit.
     */
    private void setAnimations(BufferedImage[] animations) {
        this.animations = animations;
    }

    /**
     * Renders the fruit.
     *
     * @param g The graphics context.
     */
    public void render(Graphics g) {
        if (exists) {
            super.render(animations[animationIndex], g);
        }
    }

    /**
     * Renders the fruit with the specified offset.
     *
     * @param g      The graphics context.
     * @param offset The offset to be applied while rendering.
     */
    public void render(Graphics g, int offset) {
        super.render(animations[animationIndex], g, offset);
    }

    /**
     * Sets the fruit as collected.
     */
    public void setCollected() {
        exists = false;
    }

    /**
     * Sets the fruit as collectable.
     */
    public void setCollectable() {
        exists = true;
    }

    /**
     * If fruit is collectable, updates its animations and hitbox
     */
    public void update() {
        if (exists) {
            updateAnimation();
            updateHitbox();
        }
    }

    /**
     * Checks if the fruit is collectable.
     *
     * @return true if the fruit is collectable; otherwise, false.
     */
    public boolean isCollectable() {
        return exists;
    }

    /**
     * Updates the animation of the fruit.
     */
    public void updateAnimation() {
        animationTick++;
        if (animationTick >= FRUIT_ANIMATION_SPEED) {
            animationTick = 0;
            animationIndex = (animationIndex + 1) % FRUIT_ANIMATION_LENGTH;
        }
    }
}
