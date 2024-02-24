package UI;

import states.Editor;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.Game.*;
import static utils.Constants.LevelHandler.*;
import static utils.Constants.UI.Editor.*;
import static utils.LoadSave.*;

public class EditorUI {
    private String GRID_IMG = "assets/Background/grid.png";
    private final String TERRAIN_IMG = "assets/Terrain/Terrain.png";
    private final String SELECTOR_IMG = "assets/Menu/Selector.png";
    private BufferedImage gridImg;
    private BufferedImage terrainImg;
    private BufferedImage[] selectorAnimation;
    private int animationTick = 0;
    private int animationIndex = 0;

    private final int offsetX = 0;
    private final int offsetY = (int)PANEL_HEIGHT / 2;

    private ElementType currentElementType = ElementType.TERRAIN;
    private int currentElementValue = 0;

    private int selectorPosX = offsetX + EDITOR_BORDER_THICKNESS;
    private int selectorPosY = offsetY + EDITOR_BORDER_THICKNESS;

    private int terrainTileWidth = (int) (TERRAIN_IMG_WIDTH / TERRAIN_COL_COUNT * GAME_SCALE);
    private int terrainTileHeight = (int) (TERRAIN_IMG_HEIGHT / TERRAIN_ROW_COUNT * GAME_SCALE);

    private boolean inMenu;

    public ExitButton exitButton;
    public boolean onExit;
    public EditorUI(Editor editor) {
        loadImages();
        loadAnimations();
        exitButton = new ExitButton(EXIT_BUTTON_POSX, EXIT_BUTTON_POSY, BUTTON_WIDTH, BUTTON_HEIGHT,editor.getGame().getAssetsManager().getExitButtonAnimations() );
    }

    public void resetEditorUI() {
        inMenu = false;
        onExit = false;
        currentElementType = ElementType.TERRAIN;
        currentElementValue = 0;
    }

    private void loadImages() {
        gridImg = loadImage(GRID_IMG);
        terrainImg = loadImage(TERRAIN_IMG);
    }

    private void loadAnimations() {
        selectorAnimation = loadSelectorAnimation();
    }

    public void setInMenu(boolean state) {
        inMenu = state;
    }

    public boolean isInMenu() {
        return inMenu;
    }

    public void render(Graphics g) {
        drawGridOnLevel(g);
        drawBorder(g);
        drawTerrainElements(g);
        drawElemenSelector(g);

        if (inMenu) {
            exitButton.render(g);
        }
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

    private BufferedImage[] loadSelectorAnimation() {

        BufferedImage img = loadImage(SELECTOR_IMG);
        BufferedImage[] animation = new BufferedImage[SELECTOR_FRAME_COUNT];

        for (int i = 0; i < SELECTOR_FRAME_COUNT; i++) {
            animation[i] = img.getSubimage(SELECTOR_INIT_SIZE * i, 0, SELECTOR_INIT_SIZE, SELECTOR_INIT_SIZE);
        }
        return animation;
    }


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

    public void drawGridOnLevel(Graphics g){
        for (int row = 0; row < TILE_ROW_COUNT; row++) {
            for (int col = 0; col < LEVEL_MAX_COL; col++) {
                g.drawImage(gridImg, (int) (col * GRID_SIZE * LEVEL_SCALE), (int) (row * GRID_SIZE * LEVEL_SCALE), (int) (GRID_SIZE * LEVEL_SCALE), (int) (GRID_SIZE * LEVEL_SCALE), null);
            }
        }
    }

    public void handleMouseClick(int x, int y) {
            if (x < offsetX + EDITOR_BORDER_THICKNESS || y < offsetY + EDITOR_BORDER_THICKNESS)
                return;

            if (x < offsetX + EDITOR_BORDER_THICKNESS + (GAME_SCALE * TERRAIN_IMG_WIDTH)  && y < offsetY + EDITOR_BORDER_THICKNESS + (GAME_SCALE * TERRAIN_IMG_HEIGHT)) {
                int inboundsX = x - offsetX - EDITOR_BORDER_THICKNESS;
                int inboundsY = y - offsetY - EDITOR_BORDER_THICKNESS;

                int col = inboundsX / terrainTileWidth;
                int row = inboundsY / terrainTileHeight;

                currentElementValue =  col + row * TERRAIN_COL_COUNT;
                currentElementType = ElementType.TERRAIN;

                selectorPosX = offsetX + EDITOR_BORDER_THICKNESS + col * terrainTileWidth;
                selectorPosY = offsetY + EDITOR_BORDER_THICKNESS + row * terrainTileHeight;
            }
    }

    public ElementType getCurrentElementType() {
        return currentElementType;
    }

    public int getCurrentElementValue() {
        return currentElementValue;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void drawTerrainElements(Graphics g) {
        g.drawImage(terrainImg, offsetX + EDITOR_BORDER_THICKNESS, offsetY + EDITOR_BORDER_THICKNESS, (int) (GAME_SCALE * TERRAIN_IMG_WIDTH), (int) (GAME_SCALE * TERRAIN_IMG_HEIGHT), null);
    }

    public void drawElemenSelector(Graphics g) {
        switch (currentElementType) {
            case TERRAIN:
                drawElementSelectorOnTerrain(g);
                break;
            case FRUIT:
                break;
            case PLAYER:
                break;
            case TRAP:
                break;
            case ENEMY:
                break;
        }

    }

    public void drawElementSelectorOnTerrain(Graphics g) {
        g.drawImage(selectorAnimation[animationIndex], selectorPosX, selectorPosY, terrainTileWidth + TERRAIN_SELECTOR_OFFSET, terrainTileHeight, null);
    }

    public void updateAnimation() {
        animationTick++;
        if (animationTick >= SELECTOR_SPEED)
        {
            animationTick = 0;
            animationIndex = (animationIndex + 1) % SELECTOR_FRAME_COUNT;
        }
    }
}
