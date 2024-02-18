package entities;

import states.Ingame;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utils.Constants.Game.*;
import static utils.Constants.Player.*;
import static utils.Load.*;
import static entities.Collision.*;

public class Player extends Entity {
    private BufferedImage[][] animations;
    private int animationIndex = 0;
    private int animationTick = 0;
    private int playerAction;
    private boolean movingUp, movingLeft, movingRight;
    private final String character = "MaskDude";
    private boolean flying, doubleJump;
    private float flyingSpeed = 0;


    private final Ingame ingame;

    public Player(float x, float y, int width, int height, float speed,
                  int hitBoxWidth, int hitboxHeight, int offsetWidth, int offsetHeight,
                  Ingame ingame) {
        super(x, y, width, height, speed, hitBoxWidth, hitboxHeight, offsetWidth, offsetHeight);
        this.ingame = ingame;
        loadAnimations();
        setAction(PLAYER_IDLE);
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
            img[i] = loadImage(PLAYER_ANIM_IMG[i]);
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
        updateAction();
        updateAnimation();
    }


    public void render(Graphics g) {
        if (playerAction >= PLAYER_APPEAR) {
            // TODO: appear/disapper animation
        }
        else {
            super.render(animations[playerAction][animationIndex], g);
        }
    }

    public void updatePosition() {
        if (movingUp) {
            jump();
        }
        updateYPosition();
        if (movingLeft || movingRight) {
            updateXPosition();
        }
    }

    private void jump() {
        if (flying && flyingSpeed <= 0 && !doubleJump) {
            doubleJump = true;
            flyingSpeed = (float) (PLAYER_JUMP_SPEED * 0.8f);
            return;
        }
        if (flying) {
            return;
        }
        flying = true;
        flyingSpeed = PLAYER_JUMP_SPEED;
    }

    private void updateYPosition() {
        float x = getX();
        float y = getY();
        Rectangle2D.Float hitbox = getHitbox();

        if (!flying) {
            if (canMoveDown(hitbox, 0.1f, ingame.getLevelHandler().getCurrentLevelData())) {
                flying = true;
                flyingSpeed = -0.1f;
            }
            else {
                return;
            }
        }

        if (flyingSpeed > 0) {
            if (movingUp && canMoveUp(hitbox, flyingSpeed, ingame.getLevelHandler().getCurrentLevelData())) {
                y -= flyingSpeed;
                flyingSpeed -= GRAVITY;
            }
            else if (movingUp){
                y -= (int) hitbox.y % TILE_SIZE;
                flyingSpeed -= PLAYER_FALL_AFTER_COLLISION_SPEED;
            }
            else {
                flyingSpeed = 0;
            }
        }
        else {
            if (canMoveDown(hitbox, -flyingSpeed, ingame.getLevelHandler().getCurrentLevelData())) {
                y -= flyingSpeed;
                flyingSpeed -= GRAVITY;
            }
            else {
                y -= (TILE_SIZE - 1 - (int)(hitbox.y + hitbox.height) % TILE_SIZE);
                flying = false;
                doubleJump = false;
            }
        }

        super.updatePosition(x, y);
        super.updateHitbox(x, y);
    }

    private void updateXPosition() {
        float x = getX();
        float y = getY();
        Rectangle2D.Float hitbox = getHitbox();
        if (movingLeft && !movingRight) {
            if (canMoveLeft(hitbox, PLAYER_MOVE_SPEED, ingame.getLevelHandler().getCurrentLevelData())){
                x -= PLAYER_MOVE_SPEED;
            }
            else {
                x -= (int) hitbox.x % TILE_SIZE;
            }
            setDirecton(LEFT_DIRECTION);
        }

        if (movingRight && !movingLeft) {
            if (canMoveRight(hitbox, PLAYER_MOVE_SPEED, ingame.getLevelHandler().getCurrentLevelData())) {
                x += PLAYER_MOVE_SPEED;
            }
            else {
                x += (TILE_SIZE - 1 - (hitbox.x + hitbox.width) % TILE_SIZE);
            }
            setDirecton(RIGHT_DIRECTION);
        }

        super.updatePosition(x, y);
        super.updateHitbox(x, y);
    }

    private void updateAction() {
        if (flying && flyingSpeed > 0 && doubleJump) {
            setAction(PLAYER_DOUBLE_JUMP);
        }
        else if (flying && flyingSpeed > 0) {
            setAction(PLAYER_JUMP);
        }
        else if (flying && flyingSpeed < 0) {
            setAction(PLAYER_FALL);
        }
        else if (movingRight && !movingLeft) {
            setAction(PLAYER_RUN);
        }
        else if (!movingRight && movingLeft) {
            setAction(PLAYER_RUN);
        }
        else {
            setAction(PLAYER_IDLE);
        }
    }

    private void setAction(int action) {
        if (playerAction != action) {
            playerAction = action;
            animationIndex = 0;
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

    public void setMovingLeft(boolean state) {
        this.movingLeft = state;
    }

    public void setMovingRight(boolean state) {
        this.movingRight = state;
    }
}
