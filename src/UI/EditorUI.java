package UI;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.Game.*;
import static utils.Constants.LevelHandler.*;
import static utils.Constants.UI.Editor.*;
import static utils.Load.*;
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

    private int currentElementType = 0;
    private int currentElementValue = 0;

    private int selectorPosX = offsetX + EDITOR_BORDER_WIDTH;
    private int selectorPosY = offsetY + EDITOR_BORDER_WIDTH;

    private int terrainTileWidth = (int) (GAME_SCALE * TERRAIN_IMG_WIDTH / TERRAIN_COL_COUNT);
    private int terrainTileHeight = (int) (GAME_SCALE * TERRAIN_IMG_HEIGHT / TERRAIN_ROW_COUNT);


    public EditorUI() {
        loadImages();
        loadAnimations();
    }


    private void loadImages() {
        gridImg = loadImage(GRID_IMG);
        terrainImg = loadImage(TERRAIN_IMG);
    }

    private void loadAnimations() {
        selectorAnimation = loadSelectorAnimation();
    }

    public void update() {
        updateAnimation();
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
        g.fillRect(0, PANEL_HEIGHT / 2, PANEL_WIDTH , EDITOR_BORDER_WIDTH);
        g.fillRect(0, PANEL_HEIGHT / 2, EDITOR_BORDER_WIDTH , PANEL_HEIGHT / 2);

        g.fillRect(0, PANEL_HEIGHT - EDITOR_BORDER_WIDTH, PANEL_WIDTH , EDITOR_BORDER_WIDTH);
        g.fillRect(PANEL_WIDTH - EDITOR_BORDER_WIDTH, PANEL_HEIGHT / 2, EDITOR_BORDER_WIDTH , PANEL_HEIGHT / 2);
    }

    public void drawGridOnLevel(Graphics g){
        for (int row = 0; row < TILE_ROW_COUNT; row++) {
            for (int col = 0; col < LEVEL_MAX_COL; col++) {
                g.drawImage(gridImg, (int) (col * GRID_SIZE * LEVEL_SCALE), (int) (row * GRID_SIZE * LEVEL_SCALE), (int) (GRID_SIZE * LEVEL_SCALE), (int) (GRID_SIZE * LEVEL_SCALE), null);
            }
        }
    }

    public void handleMouseClick(int x, int y) {
        if (x < offsetX + EDITOR_BORDER_WIDTH || y < offsetY + EDITOR_BORDER_WIDTH )
            return;

        if (x < offsetX + EDITOR_BORDER_WIDTH + (GAME_SCALE * TERRAIN_IMG_WIDTH)  && y < offsetY + EDITOR_BORDER_WIDTH + (GAME_SCALE * TERRAIN_IMG_HEIGHT)) {
            int inboundsX = x - offsetX - EDITOR_BORDER_WIDTH;
            int inboundsY = y - offsetY - EDITOR_BORDER_WIDTH;

            int col = inboundsX / terrainTileWidth;
            int row = inboundsY / terrainTileHeight;

            currentElementValue =  col + row * TERRAIN_COL_COUNT;
            currentElementType = ELEMENT_TERRAIN;

            selectorPosX = offsetX + EDITOR_BORDER_WIDTH + col * terrainTileWidth;
            selectorPosY = offsetY + EDITOR_BORDER_WIDTH + row * terrainTileHeight;
        }

    }

    public int getCurrentElementType() {
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
        g.drawImage(terrainImg, offsetX + EDITOR_BORDER_WIDTH, offsetY + EDITOR_BORDER_WIDTH , (int) (GAME_SCALE * TERRAIN_IMG_WIDTH), (int) (GAME_SCALE * TERRAIN_IMG_HEIGHT), null);
    }

    public void drawElemenSelector(Graphics g) {
        switch (currentElementType) {
            case ELEMENT_TERRAIN:
                drawElementSelectorOnTerrain(g);
                break;
            case ELEMENT_TRAP:
                break;
            case ELEMENT_ENEMY:
                break;
            case ELEMENT_POINTS:
                break;
            case ELEMENT_PLAYER:
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
