package Entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public abstract class Entity {
    private float x, y;
    private float speed;
    private int width, height;
    private int offsetWidth, offsetHeight;
    private Rectangle2D.Float hitbox;
    private int direction = 1;

    protected Entity(float x, float y, int width, int height, float speed, int hitboxWidth, int hiboxHeight, int offsetWidth, int offsetHeight) {
        initEntity(x, y, width, height, speed);
        initHitbox(x, y, hitboxWidth, hiboxHeight, offsetWidth, offsetHeight);
    }

    private void initHitbox(float x, float y, int width, int height, int offsetWidth, int offsetHeight) {
        this.offsetHeight = offsetHeight;
        this.offsetWidth = offsetWidth;

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
        this.speed = speed;
    }

    protected void setDirecton(int dir) {
        direction = dir;
    }

    protected void render(BufferedImage frame, Graphics g) {
        g.drawImage(frame, (int)this.x + (direction == -1 ? width : 0), (int)this.y, direction * this.width, this.height, null);
        //drawHitbox(g);
    }

//    private void drawHitbox(Graphics g) {
//        g.setColor(Color.red);
//        g.drawRect((int)hitbox.x, (int)hitbox.y, (int)hitbox.width, (int)hitbox.height);
//    }

    protected void updateHitbox(float x, float y) {
        this.hitbox.x = x + offsetWidth;
        this.hitbox.y = y + offsetHeight;
    }

    protected void updatePosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    protected Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    protected float getX() {
        return x;
    }
    protected float getY() {
        return y;
    }
    protected  float getWidth() {
        return width;
    }
    protected  float getHeight() {
        return height;
    }
}
