package objects;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utils.Constants.Game.LEVEL_SCALE;
import static utils.Constants.Game.RIGHT_DIRECTION;

/**
 * Abstract class representing game objects.
 */
public abstract class Object {
    private float x, y;
    private float initX, initY;
    private int offsetWidthRender;
    private int width, height;
    private int offsetWidthHitbox, offsetHeightHitbox;
    private Rectangle2D.Float hitbox;
    private int direction = RIGHT_DIRECTION;

    /**
     * Constructs a new object with the specified coordinates, size
     *
     * @param x                 The x-coordinate of the object.
     * @param y                 The y-coordinate of the object.
     * @param width             The width of the object.
     * @param height            The height of the object.
     * @param hitboxWidth       The width of the hitbox.
     * @param hitboxHeight      The height of the hitbox.
     * @param offsetWidthHitbox The offset width of the hitbox.
     * @param offsetHeightHitbox The offset height of the hitbox.
     */
    protected Object(float x, float y, int width, int height, int hitboxWidth, int hitboxHeight, int offsetWidthHitbox, int offsetHeightHitbox) {
        initEntity(x, y, width, height);
        initHitbox(x, y, hitboxWidth, hitboxHeight, offsetWidthHitbox, offsetHeightHitbox);
    }

    private void initHitbox(float x, float y, int width, int height, int offsetWidth, int offsetHeight) {
        this.offsetHeightHitbox = offsetHeight;
        this.offsetWidthHitbox = offsetWidth;

        this.hitbox = new Rectangle2D.Float();
        this.hitbox.x = x + offsetWidth;
        this.hitbox.y = y + offsetHeight;
        this.hitbox.width = width;
        this.hitbox.height = height;
    }

    private void initEntity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.initX = x;
        this.initY = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Sets the direction of the object.
     *
     * @param dir new direction
     */
    protected void setDirection(int dir) {
        direction = dir;
    }

    /**
     * Renders the object
     *
     * @param frame The image frame of the object.
     * @param g     The graphics context.
     */
    protected void render(BufferedImage frame, Graphics g) {
        g.drawImage(frame, (int) (LEVEL_SCALE * (this.x + (direction == -1 ? width : 0))) - offsetWidthRender, (int) (LEVEL_SCALE * this.y),
                (int) (direction * this.width * LEVEL_SCALE), (int) (this.height * LEVEL_SCALE), null);
//        drawHitbox(g);
    }

    /**
     * Renders the object with the specified offset.
     *
     * @param frame  The image frame of the object.
     * @param g      The graphics context.
     * @param offset The offset to be applied while rendering.
     */
    protected void render(BufferedImage frame, Graphics g, int offset) {
        g.drawImage(frame, (int) (LEVEL_SCALE * (this.x + (direction == -1 ? width : 0))) - offset, (int) (LEVEL_SCALE * this.y),
                (int) (direction * this.width * LEVEL_SCALE), (int) (this.height * LEVEL_SCALE), null);
    }

    /**
     * Renders hitbox of the object
     *  @param g  The graphics context.
     */

    private void drawHitbox(Graphics g) {
        g.setColor(Color.red);
        g.drawRect((int) hitbox.x - offsetWidthRender, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    /**
     * Updates hitbox position.
     */
    protected void updateHitbox() {
        this.hitbox.x = x + offsetWidthHitbox;
        this.hitbox.y = y + offsetHeightHitbox;
    }

    /**
     * Updates the position of the object.
     *
     * @param x The new x-coordinate.
     * @param y The new y-coordinate.
     */
    protected void updatePosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sets the render offset of the object.
     *
     * @param x The offset value.
     */
    public void setOffsetRender(int x) {
        offsetWidthRender = x;
    }

    /**
     * Retrieves the hitbox of the object.
     *
     * @return The hitbox rectangle.
     */
    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    /**
     * Retrieves the x-coordinate of the object.
     *
     * @return The x-coordinate.
     */
    public float getX() {
        return x;
    }

    /**
     * Retrieves the y-coordinate of the object.
     *
     * @return The y-coordinate.
     */
    public float getY() {
        return y;
    }

    /**
     * Sets the x-coordinate of the object.
     *
     * @param x The new x-coordinate.
     */
    public void setX(float x) {
        this.x = x;
        this.initX = x;
    }

    /**
     * Sets the y-coordinate of the object.
     *
     * @param y The new y-coordinate.
     */
    public void setY(float y) {
        this.y = y;
        this.initY = y;
    }


    /**
     * Resets the object to its initial state.
     */
    public void reset() {
        updatePosition(initX, initY);
        direction = RIGHT_DIRECTION;
        updateHitbox();
    }
}
