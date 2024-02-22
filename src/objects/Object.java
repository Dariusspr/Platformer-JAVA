package objects;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utils.Constants.Game.LEVEL_SCALE;

public abstract class Object {
    private float x, y;
    private float initX, initY;
    private int offsetWidthRender = 0;
    private int width, height;
    private int offsetWidthHitbox, offsetHeightHitbox;
    private Rectangle2D.Float hitbox;
    private int direction = 1;

    protected Object(float x, float y, int width, int height, int hitboxWidth, int hiboxHeight, int offsetWidthHitbox, int offsetHeightHitbox) {
        initEntity(x, y, width, height);
        initHitbox(x, y, hitboxWidth, hiboxHeight, offsetWidthHitbox, offsetHeightHitbox);
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

    protected void setDirecton(int dir) {
        direction = dir;
    }

    protected void render(BufferedImage frame, Graphics g) {
        g.drawImage(frame, (int)(LEVEL_SCALE * (this.x + (direction == -1 ? width : 0))) - offsetWidthRender, (int)(LEVEL_SCALE * this.y),
                (int)(direction * this.width * LEVEL_SCALE), (int)(this.height * LEVEL_SCALE), null);
        drawHitbox(g);
    }

    protected void renderCustomOffset(BufferedImage frame, Graphics g, int offset) {
        g.drawImage(frame, (int)(LEVEL_SCALE * (this.x + (direction == -1 ? width : 0))) - offset, (int)(LEVEL_SCALE * this.y),
                (int)(direction * this.width * LEVEL_SCALE), (int)(this.height * LEVEL_SCALE), null);
    }

    private void drawHitbox(Graphics g) {
        g.setColor(Color.red);
        g.drawRect((int)hitbox.x - offsetWidthRender, (int)hitbox.y, (int)hitbox.width, (int)hitbox.height);
    }

    protected void updateHitbox(float x, float y) {
        this.hitbox.x = x + offsetWidthHitbox;
        this.hitbox.y = y + offsetHeightHitbox;
    }

    protected void updatePosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setOffsetRender(int x) {
        offsetWidthRender = x;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    public  float getWidth() {
        return width;
    }
    public  float getHeight() {
        return height;
    }

    public void reset() {
        this.x = initX;
        this.y = initY;
        updateHitbox(initX, initY);
    }
}
