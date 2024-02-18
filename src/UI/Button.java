package UI;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utils.Constants.UI.*;
import static utils.Load.loadImage;

public abstract class Button {
    protected int animWidth, animHeight;;
    BufferedImage[] animaton;
    private int animationIndex = 0;
    private int animationTick = 0;

    Rectangle2D.Float button;

    protected Button(int x, int y, int animWidth, int animHeight, int width, int height, String path) {
        button = new Rectangle2D.Float();
        button.x = x;
        button.y = y;
        this.animWidth = animWidth;
        this.animHeight = animHeight;
        button.width = width;
        button.height = height;
        loadAnimations(path, animWidth, animHeight);
    }

    private void loadAnimations(String path, int animWidth, int animHeight) {
        BufferedImage img = loadImage(path);
        animaton = new BufferedImage[BUTTON_ANIM_LENGTH];

        for (int i = 0; i < BUTTON_ANIM_LENGTH; i++)
        {
            animaton[i]=  img.getSubimage(i * animWidth, 0 , animWidth, animHeight);
        }
    }

    public void render(Graphics g) {
        g.drawImage(animaton[animationIndex], (int) button.x, (int) button.y, (int) button.width, (int) button.height, null);
    }

    public  void update() {
            updateAnimation();
    }

    public void buttonDown() {
        animationTick = 0;
        animationIndex = 2;
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
