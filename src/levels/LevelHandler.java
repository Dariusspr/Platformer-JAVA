package levels;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.Game.*;
import static utils.Constants.LevelHandler.*;
import static utils.Load.*;

public class LevelHandler {
    private final String TERRAIN_IMG = "assets/Terrain/Terrain.png";
    private final String BASIC_LEVEL_DATA = "assets/Levels/25x40Basic.csv";
    private BufferedImage terrainImg;
    private BufferedImage[] terrainArray;
    private int offsetWidthRender = 0;

    private Level levelBasic;

    public LevelHandler() {
        loadImages();
        loadSubImagesToArray();
        loadLevelData();
    }

    private void loadLevelData() {
         levelBasic = new Level(loadLevel(BASIC_LEVEL_DATA));
    }

    private void loadSubImagesToArray() {
        loadTerrainToArray();
    }

    private void loadTerrainToArray() {
        terrainArray = new BufferedImage[TERRAIN_HEIGHT * TERRAIN_WIDTH];
        for (int row = 0; row < TERRAIN_HEIGHT; row++) {
            for (int col = 0; col < TERRAIN_WIDTH; col++) {
                terrainArray[row * TERRAIN_WIDTH + col] = terrainImg.getSubimage(col * TILE_INIT_SIZE, row * TILE_INIT_SIZE, TILE_INIT_SIZE, TILE_INIT_SIZE);
            }
        }
    }

    private void  loadImages() {
        terrainImg = LoadImage(TERRAIN_IMG);

    }

    public void render(Graphics g) {
        renderTerrain(g);
    }

    private void renderTerrain(Graphics g) {
        for (int row = 0; row < TILE_ROW_COUNT; row++) {
            for (int col = 0; col < getCurrentLevelData()[0].length; col++) {
                g.drawImage(terrainArray[levelBasic.getTile(row, col)], (int)(LEVEL_SCALE * col  * TILE_SIZE) - offsetWidthRender, (int)(LEVEL_SCALE * row  * TILE_SIZE), (int)(TILE_SIZE * LEVEL_SCALE), (int)(LEVEL_SCALE * TILE_SIZE),null);
            }
        }
    }

    public void setOffsetRender(int x) {
        offsetWidthRender = x;
    }

    public void update() {
    }

    public int[][] getCurrentLevelData() {
        return levelBasic.getLevelData();
    }

}
