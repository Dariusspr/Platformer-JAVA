package levels;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.Game.*;
import static utils.Constants.LevelHandler.*;
import static utils.Load.*;

public class LevelHandler {
    private final String TERRAIN_IMG = "assets/Terrain/Terrain.png";
    private String LEVEL_DIRECTORY = "assets/Levels";
    private Level[] levels;
    private int currentLevel = 0;
    private BufferedImage[] terrainArray;
    private int offsetWidthRender = 0;


    public LevelHandler() {
        loadElementsToArray();
        loadAllLevels();
    }

    private void loadAllLevels() {
        String[] files = loadFileNames(LEVEL_DIRECTORY);
        levels = new Level[files.length];
        for (int i = 0; i < files.length; i++) {
            levels[i] = new Level(
                    loadLevel(LEVEL_DIRECTORY+ "/" + files[i]),
                    files[i].substring(0, files[i].lastIndexOf(".csv")),
                    i
                );
        }
    }

    private void loadElementsToArray() {
        loadTerrainToArray();
    }

    private void loadTerrainToArray() {
        BufferedImage terrainImg = LoadImage(TERRAIN_IMG);
        terrainArray = new BufferedImage[TERRAIN_HEIGHT * TERRAIN_WIDTH];
        for (int row = 0; row < TERRAIN_HEIGHT; row++) {
            for (int col = 0; col < TERRAIN_WIDTH; col++) {
                terrainArray[row * TERRAIN_WIDTH + col] = terrainImg.getSubimage(col * TILE_INIT_SIZE, row * TILE_INIT_SIZE, TILE_INIT_SIZE, TILE_INIT_SIZE);
            }
        }
    }

    public void render(Graphics g) {
        renderTerrain(g, offsetWidthRender);
    }

    public void renderCustomOffset(Graphics g, int offset) {
        renderTerrain(g, offset);
    }

    private void renderTerrain(Graphics g, int offset) {
        for (int row = 0; row < TILE_ROW_COUNT; row++) {
            for (int col = 0; col < getCurrentLevelData()[0].length; col++) {
                g.drawImage(terrainArray[levels[currentLevel].getTile(row, col)], (int)(LEVEL_SCALE * col  * TILE_SIZE) - offset, (int)(LEVEL_SCALE * row  * TILE_SIZE), (int)(TILE_SIZE * LEVEL_SCALE), (int)(LEVEL_SCALE * TILE_SIZE),null);
            }
        }
    }

    public void setOffsetRender(int x) {
        offsetWidthRender = x;
    }

    public void update() {
    }

    public int[][] getCurrentLevelData() {
        return levels[currentLevel].getLevelData();
    }

    public void ChangeLevel(int level) {
        if (level < 0) {
            currentLevel = levels.length - 1;
        }
        else if (level >= levels.length) {
            currentLevel = 0;
        }
        else {
            currentLevel = level;
        }
    }
    public int getCurrentLevelIndex() {
        return currentLevel;
    }
    public int getPlayerX() {
        return 300;
    }
    public int getPlayerY() {
        return 550;
    }
}
