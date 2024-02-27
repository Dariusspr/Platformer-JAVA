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

    /**
     * Changes the current level to the one at the specified index and initializes fruits for that level
     *
     * @param currentLevelIndex The index of the level to switch to.
     */
    public void changeLevel(int currentLevelIndex) {
        this.currentLevelIndex = currentLevelIndex;
        initFruits();
    }

    /**
     * Initializes the fruits for the current level using level's fruit data
     */
    private void initFruits() {
        float[] fruitData = getCurrentLevel().getFruitData();
        totalFruitCount = 0;
        fruitIndex = 0;
        for (int i = 0; i < getCurrentLevel().getFruitCount(); i++) {
            createFruit(fruitData[i * 2], fruitData[i * 2 + 1]);
        }
    }

    /**
     * Creates a new fruit at the specified coordinates.
     *
     * @param x The X coordinate of the fruit.
     * @param y The Y coordinate of the fruit.
     */
    public void createFruit(float x, float y) {
        if (fruitIndex >= MAX_FRUIT_COUNT) {
            return;
        }
        apples[fruitIndex].setX(x);
        apples[fruitIndex].setY(y);

        fruitIndex++;
        totalFruitCount++;
    }

    /**
     * Deletes the fruit at the specified coordinates.
     *
     * @param x The X coordinate of the fruit.
     * @param y The Y coordinate of the fruit.
     */
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


    /**
     * Loads fruit animations for every fruit instance.
     */
    public void loadFruits() {
        apples = new Fruit[MAX_FRUIT_COUNT];

        for (int i = 0; i < MAX_FRUIT_COUNT; i++) {
            apples[i] = new Fruit(0, 0, ingame.getGame().getAssetsManager().getAppleAnimations());
        }
    }

    /**
     * Updates the state of all collectable fruits based on player collisions.
     * Fruit is set to a collected state if player collides with it, and fruit count is decreased
     */
    private void updateFruits() {
        for (int i = 0; i < totalFruitCount; i++) {
            if (apples[i].isCollectable()) {
                if (objectsCollide(ingame.getPlayer().getHitbox(), apples[i].getHitbox())) {
                    apples[i].setCollected();
                    currentFruitCount--;
                } else {
                    apples[i].update();
                    apples[i].setOffsetRender(renderOffsetX);
                }
            }
        }
    }

    /**
     * Resets all fruits to a collectable state.
     */
    private void resetFruits() {
        for (Fruit apple : apples) {
            if (apple != null && !apple.isCollectable()) {
                apple.setCollectable();
            }
        }
    }

    /**
     * Saves the current fruit data for the level.
     * Updates the fruit data and count for the current level
     */
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

    /**
     * Retrieves level count
     *
     * @return The total count of levels.
     */
    public int getLevelCount() {
        return levelCount;
    }

    /**
     * Renders the terrain of the current level.
     *
     * @param g      The Graphics object to render with.
     * @param offset The offset for rendering the terrain.
     */
    private void renderTerrain(Graphics g, int offset) {
        for (int row = 0; row < TILE_ROW_COUNT; row++) {
            for (int col = 0; col < getCurrentLevelTerrain()[0].length; col++) {
                g.drawImage(terrainArray[getCurrentLevel().getTile(row, col)],
                        (int) (LEVEL_SCALE * col * TILE_SIZE) - offset,
                        (int) (LEVEL_SCALE * row * TILE_SIZE),
                        (int) (TILE_SIZE * LEVEL_SCALE),
                        (int) (LEVEL_SCALE * TILE_SIZE),
                        null);
            }
        }
    }

    /**
     * Updates the state of the current level(WON, LOST) based on game conditions
     */
    private void updateLevelState() {
        if (currentFruitCount == 0) {
            levels[currentLevelIndex].setLevelState(Level.LevelState.WON);
        }
        if (hitSpike(ingame.getPlayer().getHitbox(), getCurrentLevelTerrain())) {
            levels[currentLevelIndex].setLevelState(Level.LevelState.LOST);
        }
    }

    /**
     * Retrieves the terrain data of the current level.
     *
     * @return The terrain data of the current level.
     */
    public int[][] getCurrentLevelTerrain() {
        return levels[currentLevelIndex].getTerrainData();
    }

    /**
     * Retrieves an array containing all levels.
     *
     * @return An array containing all levels.
     */
    public Level[] getAllLevels() {
        return levels;
    }

    /**
     * Renders the game elements including background, terrain, and apples.
     *
     * @param g The Graphics object to render with.
     */
    public void render(Graphics g) {
        g.drawImage(background, -renderOffsetX, 0, BACKGROUND_SIZE, BACKGROUND_SIZE, null);
        renderTerrain(g, renderOffsetX);
        renderApples(g, renderOffsetX);
    }

    /**
     * Renders the game elements including terrain and apples with a specified offset.
     *
     * @param g      The Graphics object to render with.
     * @param offset The offset for rendering.
     */
    public void render(Graphics g, int offset) {
        renderTerrain(g, offset);
        renderApples(g, offset);
    }

    /**
     * Updates the level logic if current level state is PLAYING
     */
    public void update() {
        if (levels[currentLevelIndex].getLevelState() == Level.LevelState.PLAYING) {
            updateFruits();
            updateLevelState();
        }
    }

    /**
     * Renders collectable apples.
     *
     * @param g      The Graphics object to render with.
     * @param offset The offset for rendering.
     */
    private void renderApples(Graphics g, int offset) {
        for (int i = 0; i < totalFruitCount; i++) {
            if (apples[i].isCollectable()) {
                apples[i].render(g, offset);
            }
        }
    }

    /**
     * Sets the render offset for rendering game elements.
     *
     * @param x The X-coordinate offset for rendering.
     */
    public void setRenderOffset(int x) {
        renderOffsetX = x;
    }

    /**
     * Loads all available levels from files.
     */
    private void loadAllLevels() {
        String[] files = loadFileNames();
        levels = new Level[MAX_LEVEL_COUNT];
        levelCount = Math.min(files.length, MAX_LEVEL_COUNT);
        for (int i = 0; i < levelCount; i++) {
            levels[i] = loadLevelData(files[i]);
        }
    }

    /**
     * Retrieves the current level.
     *
     * @return The current level.
     */
    public Level getCurrentLevel() {
        return levels[currentLevelIndex];
    }

    /**
     * Resets the current level by resetting fruits, fruit count, and updating its state to playing.
     */
    public void resetLevel() {
        resetFruits();
        currentFruitCount = totalFruitCount;
        getCurrentLevel().setLevelState(Level.LevelState.PLAYING);
    }

    /**
     * Updates the best completion time for the current level if the provided time is lower than the current best time.
     *
     * @param time The new best completion time.
     */
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
