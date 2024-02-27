package UI;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utils.Constants.UI.*;

public class Button {
    private int animWidth, animHeight;;
    BufferedImage[] animation;
    private int animationIndex = 0;
    private int animationTick = 0;

    Rectangle2D.Float button;

    public Button(int x, int y, int width, int height, BufferedImage[] animation) {
        button = new Rectangle2D.Float();
        button.x = x;
        button.y = y;
        this.animWidth = BUTTON_ANIM_WIDTH;
        this.animHeight = BUTTON_ANIM_HEIGHT;
        button.width = width;
        button.height = height;
        this.animation = animation;
    }

    protected void setAnimations(BufferedImage[] animation) {
        this.animation = animation;
    }

    public void render(Graphics g) {
        g.drawImage(animation[animationIndex], (int) button.x, (int) button.y, (int) button.width, (int) button.height, null);
    }

    public  void update() {
            updateAnimation();
    }

    public void buttonDown() {
        animationTick = 0;
        animationIndex = 2;
    }

    public void buttonUp() {
        animationTick = 0;
        animationIndex = 0;
    }

    private void updateAnimation() {
        animationTick++;
        if (animationTick >= BUTTON_ANIM_SPEED)
        {
            animationTick = 0;
            animationIndex = (animationIndex + 1) % BUTTON_ANIM_LENGTH;
        }
    }

    public boolean onButton(int x, int y) {
        return button.contains(x, y);
    }
}
