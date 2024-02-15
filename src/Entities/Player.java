package Entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static utils.Constants.Player.*;
import static utils.Load.*;

public class Player extends Entity {
    private BufferedImage[][] animations;
    private int animationIndex = 0;
    private int animationTick = 0;
    private int playerAction;
    private boolean movingUp, movingDown, movingLeft, movingRight;
    private final String character = "MaskDude";

    public Player(int x, int y) {
        super(x, y);
        loadAnimations();
    }

    private BufferedImage[] loadImages() {
        final String[] PLAYER_ANIM_IMG = {
                "assets/MainCharacters/" + character + "/Idle.png", "assets/MainCharacters/" + character + "/Hit.png",
                "assets/MainCharacters/" + character + "/Run.png", "assets/MainCharacters/" + character + "/Jump.png",
                "assets/MainCharacters/" + character + "/Fall.png", "assets/MainCharacters/" + character + "/DoubleJump.png",
                "assets/MainCharacters/" + character + "/WallJump.png", "assets/MainCharacters/Appear.png",
                "assets/MainCharacters/Disappear.png" };

        BufferedImage[] img = new BufferedImage[PLAYER_ANIM_IMG.length];

        for (int i = 0; i < PLAYER_ANIM_IMG.length; i++) {
            img[i] = LoadImage(PLAYER_ANIM_IMG[i]);
        }

        return img;
    }

    public void loadAnimations() {
        BufferedImage[] img = loadImages();
        animations = new BufferedImage[PLAYER_ANIM_COUNT][PLAYER_ANIM_MAX_FRAMES];

        for (int i = 0; i < img.length; i++)
        {
            for (int j = 0; j < PLAYER_ANIM_LENGTH[i]; j++)
            {
                if (i >= PLAYER_APPEAR) {
                    animations[i][j] =  img[i].getSubimage(j * PLAYER_ANIM_APPEAR_SIZE, 0 , PLAYER_ANIM_APPEAR_SIZE, PLAYER_ANIM_APPEAR_SIZE);
                }
                else {
                    animations[i][j] =  img[i].getSubimage(j * PLAYER_ANIM_SIZE, 0 , PLAYER_ANIM_SIZE, PLAYER_ANIM_SIZE);
                }
            }
        }
    }

    public void update() {
        updatePosition();
        updateAnimation();
    }


    public void render(Graphics g) {
        if (playerAction >= PLAYER_APPEAR) {
            g.drawImage(animations[playerAction][animationIndex], super.x, super.y, PLAYER_SCALE * PLAYER_ANIM_APPEAR_SIZE, PLAYER_SCALE * PLAYER_ANIM_APPEAR_SIZE, null);
        }
        else {
            g.drawImage(animations[playerAction][animationIndex], super.x, super.y, PLAYER_SCALE * PLAYER_ANIM_SIZE, PLAYER_SCALE * PLAYER_ANIM_SIZE, null);
        }
    }

    public void updatePosition() {
        if (this.movingUp) {
            super.y -= PLAYER_SPEED;
        }
        if (this.movingDown) {
            super.y += PLAYER_SPEED;
        }
        if (this.movingLeft) {
            super.x -= PLAYER_SPEED;
        }
        if (this.movingRight) {
            super.x += PLAYER_SPEED;
        }
    }

    public void setAction(int action) {
        if (this.playerAction != action) {
            this.playerAction = action;
            this.animationIndex = 0;
        }
    }

    public void updateAnimation() {
        animationTick++;
        if (animationTick >= PLAYER_ANIMATION_SPEED)
        {
            animationTick = 0;
            animationIndex = (animationIndex + 1) % PLAYER_ANIM_LENGTH[playerAction];
        }
    }

    public void setMovingUp(boolean state) {
        this.movingUp = state;
    }

    public void setMovingDown(boolean state) {
        this.movingDown = state;
    }

    public void setMovingLeft(boolean state) {
        this.movingLeft = state;
    }

    public void setMovingRight(boolean state) {
        this.movingRight = state;
    }
}
