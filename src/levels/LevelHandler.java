package levels;

import objects.Apple;
import states.Ingame;

import java.awt.*;
import java.awt.image.BufferedImage;

import static objects.Collision.objectsCollide;
import static utils.Constants.Fruit.*;
import static utils.Constants.Game.*;
import static utils.Constants.LevelHandler.*;
import static utils.Load.*;


public class LevelHandler {
    private final Ingame ingame;
    private final String TERRAIN_IMG = "assets/Terrain/Terrain.png";
    private String LEVEL_DIRECTORY = "assets/Levels/layout";
    private String LEVEL_INFO = "assets/Levels/levelInfo.csv";
    private String BACKGROUND_IMG = "assets/Background/background_blue.png";
    private BufferedImage background;
    private Level[] levels;
    private int currentLevel = 0;
    private BufferedImage[] terrainArray;
    private int offsetWidthRender = 0;

    private int totalFruitCount;
    private int currentFruitCount;

    private Apple[] apples;

    public LevelHandler(Ingame ingame) {
        this.ingame = ingame;
        loadTerrainToArray();
        loadAllLevels();
        loadFruits();
       currentFruitCount = totalFruitCount = levels[currentLevel].getTotalFruitCount();
       background = loadImage(BACKGROUND_IMG);
    }

    private void loadFruits() {
        loadApples();
    }

    private void loadAllLevels() {
        String[] files = loadFileNames(LEVEL_DIRECTORY);
        float[] times = loadTimes(LEVEL_INFO);
        levels = new Level[files.length];

        for (int i = 0; i < files.length; i++) {
            levels[i] = loadLevelData(LEVEL_DIRECTORY+ "/" + files[i]);
            levels[i].setName(files[i].substring(0, files[i].lastIndexOf(".csv")));
            levels[i].setIndex(i);
            levels[i].setBestTime(times[i]);
        }
    }

    // TERRAIN STUFF
    private void loadTerrainToArray() {
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
                g.drawImage(terrainArray[levels[currentLevel].getTile(row, col)], (int)(LEVEL_SCALE * col  * TILE_SIZE) - offset, (int)(LEVEL_SCALE * row  * TILE_SIZE), (int)(TILE_SIZE * LEVEL_SCALE), (int)(LEVEL_SCALE * TILE_SIZE),null);
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

    private void renderApplesCustomOffset(Graphics g, int offset) {
        for (int i = 0; i < apples.length; i++) {
            if (apples[i] != null) {
                apples[i].renderCustomOffset(g, offset);
            }
        }
    }

    public void render(Graphics g) {
        g.drawImage(background, 0 - offsetWidthRender, 0, BACKGROUND_SIZE, BACKGROUND_SIZE, null);
        renderTerrain(g, offsetWidthRender);
        renderFruits(g);
    }

    public void renderCustomOffset(Graphics g, int offset) {
        renderTerrain(g, offset);
        renderApplesCustomOffset(g, offset);
    }

    public void setOffsetRender(int x) {
        offsetWidthRender = x;
    }

    public void update() {
        if (levels[currentLevel].getLevelState() == Level.LevelState.OTHER) {
            updateApples();
            updateLevelState();
        }

    }

    private void updateLevelState() {
        if (currentFruitCount == 0) {
            levels[currentLevel].setLevelState(Level.LevelState.WON);
        }
    }

    public int[][] getCurrentLevelTerrain() {
        return levels[currentLevel].getTerrainData();
    }

    public void changeLevel(int level) {
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
    public Level getCurrentLevel() {return levels[currentLevel];}

    // TODO: read from file
    public int getPlayerX() {
        return 200;
    }
    public int getPlayerY() {
        return 600;
    }
    public Level[] getAllLevels() {return levels;}

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    // TODO: load copy for all fruits, and timer, add pause, and win/lose, add spikes, ability to go back to menu, reset

    public void loadApples() {
        Apple tmp = new Apple(0, 0);
        tmp.loadAnimations();
        BufferedImage[] animations = tmp.getAnimations();

        apples = new Apple[MAX_FRUIT_COUNT];
        int[] appleData = levels[currentLevel].getFruitData(Fruits.APPLE.ordinal());
        for (int i = 0; i < levels[currentLevel].getFruitCount(Fruits.APPLE.ordinal()); i++) {
            apples[i] = new Apple(appleData[i], appleData[i + 1]);
            apples[i].setAnimations(animations);
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
                    apples[i].setOffsetRender(offsetWidthRender);
                }
            }
        }
    }
    private void resetApples() {
        for (int i = 0; i < apples.length; i++) {
            if (apples[i] != null && !apples[i].isCollectable()) {
                apples[i].setCollectable();
                System.out.println("S");
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
}
