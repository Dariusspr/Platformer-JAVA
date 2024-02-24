package levels;

import objects.Apple;
import states.Ingame;

import java.awt.*;
import java.awt.image.BufferedImage;

import static objects.Collision.hitSpike;
import static objects.Collision.objectsCollide;
import static utils.Constants.Fruit.*;
import static utils.Constants.Game.*;
import static utils.Constants.LevelHandler.*;
import static utils.LoadSave.*;


public class LevelManager {
    private final Ingame ingame;
    private final String TERRAIN_IMG = "assets/Terrain/Terrain.png";
    private String BACKGROUND_IMG = "assets/Background/background_blue.png";
    private BufferedImage background;
    private Level[] levels;
    private int currentLevelIndex = 0;
    private BufferedImage[] terrainArray;
    private int renderOffsetX = 0;

    private int totalFruitCount;
    private int currentFruitCount;

    private Apple[] apples;

    int levelCount;

    public LevelManager(Ingame ingame) {
        this.ingame = ingame;

        background = loadImage(BACKGROUND_IMG);
        loadTerrainImgs();

        loadAllLevels();
        loadFruits();
        currentFruitCount = totalFruitCount = levels[currentLevelIndex].getFruitCount();
    }

    private void loadFruits() {
        loadApples();
    }

    private void loadAllLevels() {
        String[] files = loadFileNames();
        levels = new Level[MAX_LEVEL_COUNT];
        levelCount = files.length;
        for (int i = 0; i < files.length && i < MAX_LEVEL_COUNT; i++) {
            levels[i] = loadLevelData(files[i]);
            levels[i].setIndex(i);
        }
    }

    // TERRAIN STUFF
    private void loadTerrainImgs() {
        BufferedImage terrainImg = loadImage(TERRAIN_IMG);
        terrainArray = new BufferedImage[TERRAIN_HEIGHT * TERRAIN_WIDTH];
        for (int row = 0; row < TERRAIN_HEIGHT; row++) {
            for (int col = 0; col < TERRAIN_WIDTH; col++) {
                terrainArray[row * TERRAIN_WIDTH + col] = terrainImg.getSubimage(col * TILE_INIT_SIZE, row * TILE_INIT_SIZE, TILE_INIT_SIZE, TILE_INIT_SIZE);
            }
        }
    }

    private void renderTerrain(Graphics g, int offset) {
        for (int row = 0; row < TILE_ROW_COUNT; row++) {
            for (int col = 0; col < getCurrentLevelTerrain()[0].length; col++) {
                g.drawImage(terrainArray[levels[currentLevelIndex].getTile(row, col)], (int)(LEVEL_SCALE * col  * TILE_SIZE) - offset, (int)(LEVEL_SCALE * row  * TILE_SIZE), (int)(TILE_SIZE * LEVEL_SCALE), (int)(LEVEL_SCALE * TILE_SIZE),null);
            }
        }
    }
    /////////////////////////////////////


    private void renderFruits(Graphics g) {
        renderApples(g);
    }

    private void renderApples(Graphics g) {
        for (int i = 0; i < apples.length; i++) {
            if (apples[i] != null) {
                apples[i].render(g);
            }
        }
    }

    private void renderApples(Graphics g, int offset) {
        for (int i = 0; i < apples.length; i++) {
            if (apples[i] != null) {
                apples[i].render(g, offset);
            }
        }
    }

    public void render(Graphics g) {
        g.drawImage(background, 0 - renderOffsetX, 0, BACKGROUND_SIZE, BACKGROUND_SIZE, null);
        renderTerrain(g, renderOffsetX);
        renderFruits(g);
    }

    public void render(Graphics g, int offset) {
        renderTerrain(g, offset);
        renderApples(g, offset);
    }

    public void setRenderOffset(int x) {
        renderOffsetX = x;
    }

    public void update() {
        if (levels[currentLevelIndex].getLevelState() == Level.LevelState.OTHER) {
            updateApples();
            updateLevelState();
        }
    }

    private void updateLevelState() {
        if (currentFruitCount == 0) {
            levels[currentLevelIndex].setLevelState(Level.LevelState.WON);
        }
        if (hitSpike(ingame.getPlayer().getHitbox(), getCurrentLevelTerrain())) {
            levels[currentLevelIndex].setLevelState(Level.LevelState.LOST);
        }
    }

    public int[][] getCurrentLevelTerrain() {
        return levels[currentLevelIndex].getTerrainData();
    }

    public Level getCurrentLevel() {return levels[currentLevelIndex];}

    // TODO: read from file
    public int getPlayerX() {
        return 200;
    }
    public int getPlayerY() {
        return 600;
    }
    public Level[] getAllLevels() {return levels;}

    public void setCurrentLevelIndex(int currentLevelIndex) {
        this.currentLevelIndex = currentLevelIndex;
    }

    public void loadApples() {
        apples = new Apple[MAX_FRUIT_COUNT];
        int[] appleData = levels[currentLevelIndex].getFruitData();
        for (int i = 0; i < levels[currentLevelIndex].getFruitCount(); i++) {
            apples[i] = new Apple(appleData[i], appleData[i + 1], ingame.getGame().getAssetsManager().getAppleAnimations());
        }
    }

    private void updateApples() {
        for (int i = 0; i < apples.length; i++) {
            if (apples[i] != null && apples[i].isCollectable()) {
                if (objectsCollide(ingame.getPlayer().getHitbox(), apples[i].getHitbox())) {
                    apples[i].setCollected();
                    currentFruitCount--;
                }
                else {
                    apples[i].update();
                    apples[i].setOffsetRender(renderOffsetX);
                }
            }
        }
    }
    private void resetApples() {
        for (int i = 0; i < apples.length; i++) {
            if (apples[i] != null && !apples[i].isCollectable()) {
                apples[i].setCollectable();
            }
        }
    }

    public void resetLevel() {
        resetApples();
        currentFruitCount = totalFruitCount;
        getCurrentLevel().setLevelState(Level.LevelState.OTHER);
    }

    public void updateLevelBestTime(float time) {
        if (time < getCurrentLevel().getLevelBestTime())
            getCurrentLevel().setBestTime(time);
    }

    public int getLevelCount() {
        return levelCount;
    }
}
