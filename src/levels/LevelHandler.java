package levels;

import main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.Game.*;
import static utils.Constants.Level.*;
import static utils.Load.*;

public class LevelHandler {
    private final String TERRAIN_IMG = "assets/Terrain/Terrain.png";
    private final String BASIC_LEVEL_DATA = "assets/Levels/25x40Basic.csv";
    private BufferedImage terrainImg;
    private BufferedImage[] terrainArray;
    private int[][] levelData;

    public LevelHandler() {
        loadImages();
        loadSubImagesToArray();
        loadLevelData();
    }

    private void loadLevelData() {
        levelData = loadLevel(BASIC_LEVEL_DATA);
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
        //g.drawImage(terrainArray[80], 100, 100, null);
    }

    private void renderTerrain(Graphics g) {
        for (int row = 0; row < TILE_ROW_COUNT; row++) {
            for (int col = 0; col < TILE_COL_COUNT; col++) {
                g.drawImage(terrainArray[levelData[row][col]], col  * TILE_SIZE, row  * TILE_SIZE, TILE_SIZE, TILE_SIZE ,null);
            }
        }
    }

    public void update() {
    }

}
