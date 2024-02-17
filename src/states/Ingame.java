package states;

import entities.Player;
import levels.Level;
import levels.LevelHandler;
import main.Game;
import static utils.Constants.Game.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static utils.Constants.Player.*;
import static utils.Constants.Player.PLAYER_HEIGHT_OFFSET;

public class Ingame extends State implements StateHandler{
    private Player player;
    private LevelHandler levelHandler;
    private int offsetWidthRender = 0;

    public Ingame(Game game) {
        super(game);
        player = new Player(200, 300, PLAYER_SIZE, PLAYER_SIZE, PLAYER_MOVE_SPEED,
                PLAYER_HITBOX_WIDTH, PLAYER_HITBOX_HEIGHT, PLAYER_WIDTH_OFFSET, PLAYER_HEIGHT_OFFSET,
                this);
        levelHandler = new LevelHandler();
    }

    @Override
    public void render(Graphics g) {
        levelHandler.render(g);
        player.render(g);
    }

    @Override
    public void update() {
        levelHandler.update();
        player.update();
        updateOffsetRender();
    }

    private void updateOffsetRender() {
        int currentX = (int) player.getHitbox().x;
        int deltaOffset = currentX - offsetWidthRender;

        if (deltaOffset < LEFT_TILE_BORDER * TILE_SIZE) {
            offsetWidthRender += deltaOffset - LEFT_TILE_BORDER * TILE_SIZE;
        }
        else if (deltaOffset > RIGHT_TILE_BORDER * TILE_SIZE) {
            offsetWidthRender += deltaOffset - RIGHT_TILE_BORDER * TILE_SIZE;
        }

        if (offsetWidthRender < 0) {
            offsetWidthRender = 0;
        }
        else if (offsetWidthRender > MAX_TILE_OFFSET * TILE_SIZE) {
            offsetWidthRender = MAX_TILE_OFFSET * TILE_SIZE;
        }

        player.setOffsetRender(offsetWidthRender);
        levelHandler.setOffsetRender(offsetWidthRender);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_W:
                player.setMovingUp(true);
                break;
            case KeyEvent.VK_A:
                player.setMovingLeft(true);
                break;
            case KeyEvent.VK_D:
                player.setMovingRight(true);
                break;
            case KeyEvent.VK_F1: {
                game.setState(GameState.EDITOR);
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_W:
                player.setMovingUp(false);
                break;
            case KeyEvent.VK_A:
                player.setMovingLeft(false);
                break;
            case KeyEvent.VK_D:
                player.setMovingRight(false);
                break;
        }
    }

    public Player getPlayer() {
        return player;
    }
    public LevelHandler getLevelHandler() {
        return levelHandler;
    }
}
