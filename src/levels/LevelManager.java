package levels;

import objects.Fruit;
import states.Ingame;

import java.awt.*;
import java.awt.image.BufferedImage;

import static objects.Collision.hitSpike;
import static objects.Collision.objectsCollide;
import static utils.Constants.Fruit.*;
import static utils.Constants.Game.*;
import static utils.Constants.LevelManager.*;
import static utils.LoadSave.*;

/**
 * Manages game levels, including rendering, updating logic(WON, LOST, PLAYING...).
 */
public class LevelManager {
    private final Ingame ingame;
    private final BufferedImage background;
    private Level[] levels;
    private int currentLevelIndex = 0;
    private final BufferedImage[] terrainArray;
    private int renderOffsetX = 0;

    private int totalFruitCount;
    private int currentFruitCount;

    private Fruit[] apples;
    private int fruitIndex;

    int levelCount;

    /**
     * Constructs a LevelManager
     * @param ingame The Ingame instance
     */
    public LevelManager(Ingame ingame) {
        this.ingame = ingame;

        background = ingame.getGame().getAssetsManager().getBackgroundImg();
        terrainArray = ingame.getGame().getAssetsManager().getTerrainArray();
        loadAllLevels();
        loadFruits();
    }

    public void changeLevel(int currentLevelIndex) {
        this.currentLevelIndex = currentLevelIndex;
        initFruits();
    }

    private void initFruits() {
        float[] fruitData = getCurrentLevel().getFruitData();
        totalFruitCount = 0;
        fruitIndex = 0;
        for (int i = 0; i < getCurrentLevel().getFruitCount(); i++) {
            createFruit(fruitData[i * 2] , fruitData[i * 2 + 1]);
        }
    }

    public void createFruit(float x, float y) {
        if (fruitIndex >= MAX_FRUIT_COUNT) {
            return;
        }
        apples[fruitIndex].setX(x);
        apples[fruitIndex].setY(y);

        fruitIndex++;
        totalFruitCount++;
    }

    public void deleteFruit(float x, float y) {
        if (totalFruitCount < 1)
            return;
        for (int i = 0; i < totalFruitCount; i++) {
            if (apples[i].getHitbox().contains(x, y)) {
                if (i != fruitIndex - 1) {
                    apples[i].setX(apples[fruitIndex - 1].getX());
                    apples[i].setY(apples[fruitIndex - 1].getY());
                }
                totalFruitCount--;
                fruitIndex--;
                return;
            }
        }
    }


    public void loadFruits() {
        apples = new Fruit[MAX_FRUIT_COUNT];

        for (int i = 0; i < MAX_FRUIT_COUNT; i++) {
            apples[i] = new Fruit(0, 0, ingame.getGame().getAssetsManager().getAppleAnimations());
        }
    }


    private void updateFruits() {
        for (int i = 0; i < totalFruitCount; i++) {
            if (apples[i].isCollectable()) {
                if (objectsCollide(ingame.getPlayer().getHitbox(), apples[i] .getHitbox())) {
                    apples[i].setCollected();
                    currentFruitCount--;
                } else {
                    apples[i].update();
                    apples[i].setOffsetRender(renderOffsetX);
                }
            }
        }
    }

    private void resetFruits() {
        for (Fruit apple : apples) {
            if (apple != null && !apple.isCollectable()) {
                apple.setCollectable();
            }
        }
    }

    public void saveFruitData() {
        float[] fruitData = new float[totalFruitCount * 2];
        for (int i = 0; i < totalFruitCount; i++) {

            fruitData[i * 2] = apples[i].getX();
            fruitData[i * 2 + 1] = apples[i].getY();
        }
        levels[currentLevelIndex].setFruitData(fruitData);
        levels[currentLevelIndex].setFruitCount(totalFruitCount);
        getCurrentLevel().setFruitData(fruitData);
        getCurrentLevel().setFruitCount(totalFruitCount);
    }


    public int getLevelCount() {
        return levelCount;
    }

    private void renderTerrain(Graphics g, int offset) {
        for (int row = 0; row < TILE_ROW_COUNT; row++) {
            for (int col = 0; col < getCurrentLevelTerrain()[0].length; col++) {
                g.drawImage(terrainArray[getCurrentLevel().getTile(row, col)],
                        (int)(LEVEL_SCALE * col  * TILE_SIZE) - offset,
                        (int)(LEVEL_SCALE * row  * TILE_SIZE),
                        (int)(TILE_SIZE * LEVEL_SCALE),
                        (int)(LEVEL_SCALE * TILE_SIZE),
                        null);
            }
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

    public Level[] getAllLevels() {return levels;}

    public void render(Graphics g) {
        g.drawImage(background,  -renderOffsetX, 0, BACKGROUND_SIZE, BACKGROUND_SIZE, null);
        renderTerrain(g, renderOffsetX);
        renderApples(g, renderOffsetX);
    }

    public void render(Graphics g, int offset) {
        renderTerrain(g, offset);
        renderApples(g, offset);
    }

    public void update() {
        if (levels[currentLevelIndex].getLevelState() == Level.LevelState.PLAYING) {
            updateFruits();
            updateLevelState();
        }
    }

    private void renderApples(Graphics g, int offset) {
        for (int i = 0; i < totalFruitCount; i++) {
            if (apples[i].isCollectable()) {
                apples[i].render(g, offset);
            }
        }
    }

    public void setRenderOffset(int x) {
        renderOffsetX = x;
    }

    private void loadAllLevels() {
        String[] files = loadFileNames();
        levels = new Level[MAX_LEVEL_COUNT];
        levelCount = Math.min(files.length, MAX_LEVEL_COUNT);
        for (int i = 0; i < levelCount; i++) {
            levels[i] = loadLevelData(files[i]);
        }
    }

    public Level getCurrentLevel() {return levels[currentLevelIndex];}

    public void resetLevel() {
        resetFruits();
        currentFruitCount = totalFruitCount;
        getCurrentLevel().setLevelState(Level.LevelState.PLAYING);
    }

    public void updateLevelBestTime(float time) {
        if (time < getCurrentLevel().getLevelBestTime())
            getCurrentLevel().setBestTime(time);
    }



//    public void createNewLevel(String name) {
//        Level newLevel = new Level();
//
//        newLevel.setName(name);
//        newLevel.setTerrainData(template.getTerrainData());
//        newLevel.setFruitData(template.getFruitData());
//        newLevel.setFruitCount(template.getFruitCount());
//        newLevel.setBestTime(MAX_BEST_TIME);
//        newLevel.setPlayerPos(template.getPlayerX(), template.getPlayerY());
//
//        currentLevelIndex = levelCount;
//        levels[levelCount++] = newLevel;
//    }

}
