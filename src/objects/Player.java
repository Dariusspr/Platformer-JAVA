package objects;

import states.Ingame;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utils.Constants.Game.*;
import static utils.Constants.Player.*;
import static utils.LoadSave.*;
import static objects.Collision.*;

public class Player extends Object {
    private BufferedImage[][] animations;
    private int animationIndex = 0;
    private int animationTick = 0;
    private int playerAction;
    private boolean movingUp, movingLeft, movingRight;
    private final String character = "MaskDude";
    private boolean flying, doubleJump;
    private float flyingSpeed = 0;


    private final Ingame ingame;

    public Player(float x, float y, BufferedImage[][] animations, Ingame ingame) {
        super(x, y, PLAYER_SIZE, PLAYER_SIZE, PLAYER_HITBOX_WIDTH, PLAYER_HITBOX_HEIGHT, PLAYER_WIDTH_OFFSET, PLAYER_HEIGHT_OFFSET);
        this.ingame = ingame;
        this.animations = animations;
        setAction(PLAYER_IDLE);
    }

    public void update() {
            updatePosition();
            updateAction();
            updateAnimation();
    }


    public void render(Graphics g) {
        super.render(animations[playerAction][animationIndex], g);
    }

    public void render(Graphics g, int offset) {
            super.renderCustomOffset(animations[playerAction][animationIndex], g, offset);
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
            flyingSpeed = PLAYER_JUMP_SPEED * 0.8f;
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
            if (canMoveDown(hitbox, 0.1f, ingame.getLevelHandler().getCurrentLevelTerrain())) {
                flying = true;
                flyingSpeed = -0.1f;
            }
            else {
                return;
            }
        }

        if (flyingSpeed > 0) {
            if (movingUp && canMoveUp(hitbox, flyingSpeed, ingame.getLevelHandler().getCurrentLevelTerrain())) {
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
            if (canMoveDown(hitbox, -flyingSpeed, ingame.getLevelHandler().getCurrentLevelTerrain())) {
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
            if (canMoveLeft(hitbox, PLAYER_MOVE_SPEED, ingame.getLevelHandler().getCurrentLevelTerrain())){
                x -= PLAYER_MOVE_SPEED;
            }
            else {
                x -= (int) hitbox.x % TILE_SIZE;
            }
            setDirecton(LEFT_DIRECTION);
        }

        if (movingRight && !movingLeft) {
            if (canMoveRight(hitbox, PLAYER_MOVE_SPEED, ingame.getLevelHandler().getCurrentLevelTerrain())) {
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

    public void reset() {
        super.reset();
        movingLeft = false;
        movingRight = false;
        movingUp = false;
        flying = false;
        doubleJump = false;
        flyingSpeed = 0;
        animationIndex = 0;
        animationTick = 0;
        playerAction = PLAYER_IDLE;
    }
}
