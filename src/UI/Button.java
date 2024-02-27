package UI;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utils.Constants.UI.*;

public class Button {
    BufferedImage[] animation;
    private int animationIndex = 0;
    private int animationTick = 0;

    Rectangle2D.Float button;

    /**
     * Constructs a button
     * @param x The x-coordinate of the button.
     * @param y The y-coordinate of the button.
     * @param width The width of the button.
     * @param height The height of the button.
     * @param animation The animation frames for the button.
     */
    public Button(int x, int y, int width, int height, BufferedImage[] animation) {
        button = new Rectangle2D.Float();
        button.x = x;
        button.y = y;
        button.width = width;
        button.height = height;
        this.animation = animation;
    }

    /**
     * Renders the button.
     * @param g The Graphics object.
     */
    public void render(Graphics g) {
        g.drawImage(animation[animationIndex], (int) button.x, (int) button.y, (int) button.width, (int) button.height, null);
    }

    /**
     * Updates the button's animation.
     */
    public  void update() {
            updateAnimation();
    }

    /**
     * Activates the button's down animation.
     */
    public void buttonDown() {
        animationTick = 0;
        animationIndex = 2;
    }
    /**
     * Activates the button's up animation.
     */
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
    /**
     * Checks if a point (x, y) is within the bounds of the button.
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     * @return True if the point is within the button's bounds, otherwise false.
     */
    public boolean onButton(int x, int y) {
        return button.contains(x, y);
    }
}
