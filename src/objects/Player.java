package objects;

import states.Ingame;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utils.Constants.Game.*;
import static utils.Constants.Player.*;
import static objects.Collision.*;

/**
 * Represents the player object in the game.
 */
public class Player extends Object {
    private final BufferedImage[][] animations;
    private int animationIndex = 0;
    private int animationTick = 0;
    private int playerAction;
    private boolean movingUp, movingLeft, movingRight;
    private boolean flying, doubleJump;
    private float flyingSpeed = 0;


    private final Ingame ingame;

    /**
     * Constructs a player object with the specified coordinates, animations
     *
     * @param x          The initial x-coordinate of the player.
     * @param y          The initial y-coordinate of the player.
     * @param animations The animation frames for the player.
     * @param ingame     The InGame state.
     */
    public Player(float x, float y, BufferedImage[][] animations, Ingame ingame) {
        super(x, y, PLAYER_SIZE, PLAYER_SIZE, PLAYER_HITBOX_WIDTH, PLAYER_HITBOX_HEIGHT, PLAYER_WIDTH_OFFSET, PLAYER_HEIGHT_OFFSET);
        this.ingame = ingame;
        this.animations = animations;
        setAction(PLAYER_IDLE);
    }

    /**
     * Updates the player's position, action, and animation.
     */
    public void update() {
            updatePosition();
            updateAction();
            updateAnimation();
    }

    /**
     * Renders the player.
     *
     * @param g The graphics context.
     */
    public void render(Graphics g) {
        super.render(animations[playerAction][animationIndex], g);
    }

    /**
     * Renders the player with the specified offset.
     *
     * @param g      The graphics context.
     * @param offset The offset value.
     */
    public void render(Graphics g, int offset) {
            super.render(animations[playerAction][animationIndex], g, offset);
    }

    /**
     * Updates the player's position based on input and its current position and state.
     */
    public void updatePosition() {
        if (movingUp) {
            jump();
        }
        updateYPosition();
        if (movingLeft || movingRight) {
            updateXPosition();
        }
    }

    /**
     * Handles player jump and double jump mechanic
     */
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

    /**
     * Updates the player's vertical position.
     */
    private void updateYPosition() {
        float x = getX();
        float y = getY();
        Rectangle2D.Float hitbox = getHitbox();

        if (!flying) {
            if (canMoveDown(hitbox, 0.1f, ingame.getLevelManager().getCurrentLevelTerrain())) {
                flying = true;
                flyingSpeed = -0.1f;
            }
            else {
                return;
            }
        }

        if (flyingSpeed > 0) {
            if (movingUp && canMoveUp(hitbox, flyingSpeed, ingame.getLevelManager().getCurrentLevelTerrain())) {
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
            if (canMoveDown(hitbox, -flyingSpeed, ingame.getLevelManager().getCurrentLevelTerrain())) {
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
        super.updateHitbox();
    }

    /**
     * Updates the player's horizontal position.
     */
    private void updateXPosition() {
        float x = getX();
        float y = getY();
        Rectangle2D.Float hitbox = getHitbox();
        if (movingLeft && !movingRight) {
            if (canMoveLeft(hitbox, PLAYER_MOVE_SPEED, ingame.getLevelManager().getCurrentLevelTerrain())){
                x -= PLAYER_MOVE_SPEED;
            }
            else {
                x -= (int) hitbox.x % TILE_SIZE;
            }
            setDirection(LEFT_DIRECTION);
        }

        if (movingRight && !movingLeft) {
            if (canMoveRight(hitbox, PLAYER_MOVE_SPEED, ingame.getLevelManager().getCurrentLevelTerrain())) {
                x += PLAYER_MOVE_SPEED;
            }
            else {
                x += (TILE_SIZE - 1 - (hitbox.x + hitbox.width) % TILE_SIZE);
            }
            setDirection(RIGHT_DIRECTION);
        }

        super.updatePosition(x, y);
        super.updateHitbox();
    }

    /**
     * Updates the player's action.
     */
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

    /**
     * Sets new player's action.
     *
     * @param action new action
     */
    private void setAction(int action) {
        if (playerAction != action) {
            playerAction = action;
            animationIndex = 0;
        }
    }

    /**
     * Updates the player's animation.
     */
    public void updateAnimation() {
        animationTick++;
        if (animationTick >= PLAYER_ANIMATION_SPEED)
        {
            animationTick = 0;
            animationIndex = (animationIndex + 1) % PLAYER_ANIM_LENGTH[playerAction];
        }
    }

    /**
     * Sets moving up
     * @param state true or false
     */
    public void setMovingUp(boolean state) {
        this.movingUp = state;
    }

    /**
     * Sets moving left
     * @param state true or false
     */
    public void setMovingLeft(boolean state) {
        this.movingLeft = state;
    }

    /**
     * Sets moving right
     * @param state true or false
     */
    public void setMovingRight(boolean state) {
        this.movingRight = state;
    }

    /**
     * Resets player to state at the beginning of the level
     */
    public void reset() {
        this.setX(ingame.getLevelManager().getCurrentLevel().getPlayerX());
        this.setY(ingame.getLevelManager().getCurrentLevel().getPlayerY());
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
