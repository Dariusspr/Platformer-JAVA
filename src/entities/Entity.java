package entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utils.Constants.Game.LEVEL_SCALE;

public abstract class Entity {
    private float x, y;
    private int offsetWidthRender = 0;
    private int width, height;
    private int offsetWidthHitbox, offsetHeightHitbox;
    private Rectangle2D.Float hitbox;
    private int direction = 1;

    protected Entity(float x, float y, int width, int height, float speed, int hitboxWidth, int hiboxHeight, int offsetWidthHitbox, int offsetHeightHitbox) {
        initEntity(x, y, width, height, speed);
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

    private void initEntity(float x, float y, int width, int height, float speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected void setDirecton(int dir) {
        direction = dir;
    }

    protected void render(BufferedImage frame, Graphics g) {
        g.drawImage(frame, (int)(LEVEL_SCALE * (this.x + (direction == -1 ? width : 0))) - offsetWidthRender, (int)(LEVEL_SCALE * this.y), (int)(direction * this.width * LEVEL_SCALE), (int)(this.height * LEVEL_SCALE), null);
        //drawHitbox(g);
    }

//    private void drawHitbox(Graphics g) {
//        g.setColor(Color.red);
//        g.drawRect((int)hitbox.x - offsetWidthRender, (int)hitbox.y, (int)hitbox.width, (int)hitbox.height);
//    }

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
    public  float getWidth() {
        return width;
    }
    public  float getHeight() {
        return height;
    }
}
