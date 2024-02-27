package UI;

import states.Editor;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.Fruit.FRUIT_SIZE;
import static utils.Constants.Game.*;
import static utils.Constants.LevelManager.*;
import static utils.Constants.Player.PLAYER_SIZE;
import static utils.Constants.UI.Editor.*;
/**
 * The EditorUI class represents the user interface for the editor mode.
 */
public class EditorUI {
    private final BufferedImage gridImg;
    private final BufferedImage terrainImg;
    private final BufferedImage playImg;
    private final BufferedImage appleImg;

    private final BufferedImage[] selectorAnimation;
    private int animationTick = 0;
    private int animationIndex = 0;

    private final int offsetX = 0;
    private final int offsetY = PANEL_HEIGHT / 2;

    private ElementType currentElementType = ElementType.TERRAIN;
    private int currentElementValue = 0;

    private int selectorPosX = offsetX + EDITOR_BORDER_THICKNESS;
    private int selectorPosY = offsetY + EDITOR_BORDER_THICKNESS;

    private final int terrainTileWidth = (int) (TERRAIN_IMG_WIDTH / TERRAIN_COL_COUNT * GAME_SCALE);
    private final int terrainTileHeight = (int) (TERRAIN_IMG_HEIGHT / TERRAIN_ROW_COUNT * GAME_SCALE);

    private boolean inMenu;

    public Button exitButton;
    public boolean onExit;
    /**
     * Constructs an EditorUI object
     * @param editor The Editor instance.
     */
    public EditorUI(Editor editor) {
        gridImg = editor.getGame().getAssetsManager().getGridImg();
        terrainImg = editor.getGame().getAssetsManager().getTerrainImg();
        appleImg = editor.getGame().getAssetsManager().getAppleImg();
        playImg = editor.getGame().getAssetsManager().getPlayerImg();

        selectorAnimation = editor.getGame().getAssetsManager().getSelectorAnimations();

        exitButton = new Button(EXIT_BUTTON_POSX, EXIT_BUTTON_POSY,
                                    BUTTON_WIDTH, BUTTON_HEIGHT,
                                    editor.getGame().getAssetsManager().getExitButtonAnimations() );
    }
    /**
     * Resets the editor UI state to initial state
     */
    public void resetEditorUI() {
        inMenu = false;
        onExit = false;
        currentElementType = ElementType.TERRAIN;
        currentElementValue = 0;
    }

    /**
     * Sets the editor UI's menu state.
     * @param state true or false
     */
    public void setInMenu(boolean state) {
        inMenu = state;
    }

    /**
     * Checks if the editor UI is in menu state.
     * @return True if the UI is in menu state, else false.
     */
    public boolean isInMenu() {
        return inMenu;
    }

    /**
     * Renders the editor UI.
     * @param g The Graphics object.
     */
    public void render(Graphics g) {
        drawGridOnLevel(g);
        drawBorder(g);
        drawTerrainElements(g);
        drawFruitElements(g);
        drawPlayerElement(g);
        drawElementSelector(g);
        if (inMenu) {
            exitButton.render(g);
        }
    }
    /**
     * Draws the fruit selector on the editor UI.
     * @param g The Graphics object.
     */
    private void drawFruitSelector(Graphics g) {
        final int x = (int) (EDITOR_BORDER_THICKNESS - FRUIT_SIZE * 0.05f);
        final int y = (int) (offsetY + GAME_SCALE * TERRAIN_IMG_HEIGHT + EDITOR_BORDER_THICKNESS * 2 - FRUIT_SIZE * 0.05f);
        final int selectorHeight = (int) (FRUIT_SIZE * 0.6f);
        final int selectorWidth = (int) (FRUIT_SIZE * 0.6f);
        g.drawImage(selectorAnimation[animationIndex], x, y, selectorWidth, selectorHeight, null);
    }
    /**
     * Draws the fruit elements on the editor UI.
     * @param g The Graphics object.
     */
    private void drawFruitElements(Graphics g) {
        final int x = (int) (EDITOR_BORDER_THICKNESS - FRUIT_SIZE * 0.2f);
        final int y = (int) (offsetY + GAME_SCALE * TERRAIN_IMG_HEIGHT + EDITOR_BORDER_THICKNESS * 2 - FRUIT_SIZE * 0.2f);
        g.drawImage(appleImg, x, y, FRUIT_SIZE, FRUIT_SIZE, null);
    }
    /**
     * Draws the player on the editor UI.
     * @param g The Graphics object.
     */
    private void drawPlayerElement(Graphics g) {
        final int x = (int) ((GAME_SCALE * TERRAIN_IMG_WIDTH) + 2 * EDITOR_BORDER_THICKNESS);
        final int y = offsetY + EDITOR_BORDER_THICKNESS;
        g.drawImage(playImg, x, y, PLAYER_SIZE, PLAYER_SIZE, null);
    }
    /**
     * Draws the Player selector on the editor UI.
     * @param g The Graphics object.
     */
    private void drawPlayerSelector(Graphics g) {
        final int x = (int) ((GAME_SCALE * TERRAIN_IMG_WIDTH) + 2 * EDITOR_BORDER_THICKNESS);
        final int y = offsetY + EDITOR_BORDER_THICKNESS;
        g.drawImage(selectorAnimation[animationIndex], x, y, PLAYER_SIZE, PLAYER_SIZE, null);
    }

    public void update() {
        updateAnimation();
        if (onExit) {
            exitButton.buttonDown();
        }
        else {
            exitButton.buttonUp();
        }

    }
    /**
     * Draws the border on the editor UI.
     * @param g The Graphics object.
     */
    public void drawBorder(Graphics g) {
        // Vertical
        g.fillRect(0, PANEL_HEIGHT / 2, EDITOR_BORDER_THICKNESS, PANEL_HEIGHT / 2);
        g.fillRect((int) ((GAME_SCALE * TERRAIN_IMG_WIDTH) + EDITOR_BORDER_THICKNESS), PANEL_HEIGHT / 2, EDITOR_BORDER_THICKNESS, PANEL_HEIGHT / 2);
        g.fillRect(PANEL_WIDTH - EDITOR_BORDER_THICKNESS, PANEL_HEIGHT / 2, EDITOR_BORDER_THICKNESS, PANEL_HEIGHT / 2);

        // Horizontal
        g.fillRect(0, PANEL_HEIGHT / 2, PANEL_WIDTH , EDITOR_BORDER_THICKNESS);
        g.fillRect(0, (int) (offsetY + EDITOR_BORDER_THICKNESS + GAME_SCALE * TERRAIN_IMG_HEIGHT), PANEL_WIDTH , EDITOR_BORDER_THICKNESS);
        g.fillRect(0, PANEL_HEIGHT - EDITOR_BORDER_THICKNESS, PANEL_WIDTH , EDITOR_BORDER_THICKNESS);

    }

    /**
     * Draws the grid on the level.
     * @param g The Graphics object.
     */
    public void drawGridOnLevel(Graphics g){
        for (int row = 0; row < TILE_ROW_COUNT; row++) {
            for (int col = 0; col < LEVEL_MAX_COL; col++) {
                g.drawImage(gridImg,
                            (int) (col * GRID_SIZE * LEVEL_SCALE),
                            (int) (row * GRID_SIZE * LEVEL_SCALE),
                            (int) (GRID_SIZE * LEVEL_SCALE),
                            (int) (GRID_SIZE * LEVEL_SCALE),
                        null);
            }
        }
    }

    /**
     * Handles mouse input in elements menu area
     * @param x mouse x position
     * @param y mouse y position
     */
    public void handleMouseClick(int x, int y) {
        // Level area
        if (x < offsetX + EDITOR_BORDER_THICKNESS || y < offsetY + EDITOR_BORDER_THICKNESS)
                return;

        // Terrain area
        if (x < offsetX + EDITOR_BORDER_THICKNESS + (GAME_SCALE * TERRAIN_IMG_WIDTH)  &&
                y < offsetY + EDITOR_BORDER_THICKNESS + (GAME_SCALE * TERRAIN_IMG_HEIGHT)) {
            int inboundsX = x - offsetX - EDITOR_BORDER_THICKNESS;
            int inboundsY = y - offsetY - EDITOR_BORDER_THICKNESS;

            int col = inboundsX / terrainTileWidth;
            int row = inboundsY / terrainTileHeight;

            currentElementValue =  col + row * TERRAIN_COL_COUNT;
            currentElementType = ElementType.TERRAIN;

            selectorPosX = offsetX + EDITOR_BORDER_THICKNESS + col * terrainTileWidth;
            selectorPosY = offsetY + EDITOR_BORDER_THICKNESS + row * terrainTileHeight;
        }
        // Fruit area
        else if (y < offsetY + EDITOR_BORDER_THICKNESS * 2 + (GAME_SCALE * TERRAIN_IMG_HEIGHT) + FRUIT_SIZE * 0.5f &&
                x < offsetX + EDITOR_BORDER_THICKNESS + FRUIT_SIZE * 0.5f) {
            currentElementType = ElementType.FRUIT;
        }
        // Player area
        else if (x < ((GAME_SCALE * TERRAIN_IMG_WIDTH) + 2 * EDITOR_BORDER_THICKNESS + PLAYER_SIZE) &&
                y < offsetY + EDITOR_BORDER_THICKNESS + PLAYER_SIZE) {
            currentElementType = ElementType.PLAYER;
        }
    }

    public ElementType getCurrentElementType() {
        return currentElementType;
    }

    /**
     * @return currentElementValue chosen element value
     */
    public int getCurrentElementValue() {
        return currentElementValue;
    }

    /**
     * @return offsetY vertical offset
     */
    public int getOffsetY() {
        return offsetY;
    }

    /**
     * Draws the terrain elements on the editor UI.
     * @param g The Graphics object.
     */
    public void drawTerrainElements(Graphics g) {
        g.drawImage(terrainImg,
                offsetX + EDITOR_BORDER_THICKNESS,
                offsetY + EDITOR_BORDER_THICKNESS,
                (int) (GAME_SCALE * TERRAIN_IMG_WIDTH),
                (int) (GAME_SCALE * TERRAIN_IMG_HEIGHT),
                null);
    }
    /**
     * Draws the element selector on the editor UI based on the current chosen element type.
     * @param g The Graphics object.
     */
    public void drawElementSelector(Graphics g) {
        switch (currentElementType) {
            case TERRAIN:
                drawElementSelectorOnTerrain(g);
                break;
            case FRUIT:
                drawFruitSelector(g);
                break;
            case PLAYER:
                drawPlayerSelector(g);
                break;
        }

    }
    /**
     * Draws the terrain selector on the editor UI.
     * @param g The Graphics object.
     */
    public void drawElementSelectorOnTerrain(Graphics g) {
        g.drawImage(selectorAnimation[animationIndex],
                selectorPosX,
                selectorPosY,
                terrainTileWidth + TERRAIN_SELECTOR_OFFSET,
                terrainTileHeight,
                null);
    }

    /**
     * Updates selector animation
     */
    public void updateAnimation() {
        animationTick++;
        if (animationTick >= SELECTOR_SPEED)
        {
            animationTick = 0;
            animationIndex = (animationIndex + 1) % SELECTOR_FRAME_COUNT;
        }
    }
}
