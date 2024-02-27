
package levels;

/**
 * Represents a game level with terrain data, fruit data, player position,
 * level name, level state, and best completion time.
 */
public class Level {
    private String name;
    private int[][] terrainData;
    private float[] fruitData;
    private float playerX, playerY;
    private int fruitCount;
    private float bestTime = 0;

    /**
     * Enum representing the state of the level.
     */
    public enum LevelState {
        WON, LOST, PAUSED, PLAYING
    }

    private LevelState levelState = LevelState.PLAYING;

    /**
     * Retrieves the current state of the level.
     * @return The current state of the level.
     */
    public LevelState getLevelState() {
        return levelState;
    }

    /**
     * Sets the state of the level.
     * @param state The state to set.
     */
    public void setLevelState(LevelState state) {
        this.levelState = state;
    }

    /**
     * Gets the tile value at the specified row and column.
     * @param row The row index.
     * @param col The column index.
     * @return The tile value at the specified position.
     */
    public int getTile(int row, int col) {
        return terrainData[row][col];
    }

    /**
     * Retrieves the terrain data of the level.
     * @return The terrain data.
     */
    public int[][] getTerrainData() {
        return terrainData;
    }

    /**
     * Retrieves the name of the level.
     * @return The name of the level.
     */
    public String getLevelName() {
        return name;
    }

    /**
     * Sets the terrain data of the level.
     * @param newData The new terrain data to set.
     */
    public void setTerrainData(int[][] newData) {
        this.terrainData = newData;
    }

    /**
     * Retrieves the best completion time for the level.
     * @return The best completion time.
     */
    public float getLevelBestTime() {
        return bestTime;
    }

    /**
     * Sets the name of the level.
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the best completion time for the level.
     * @param time The best completion time to set.
     */
    public void setBestTime(float time) {
        this.bestTime = time;
    }

    /**
     * Retrieves the X coordinate of the player's position.
     * @return The X coordinate of the player's position.
     */
    public float getPlayerX() {
        return playerX;
    }

    /**
     * Retrieves the Y coordinate of the player's position.
     * @return The Y coordinate of the player's position.
     */
    public float getPlayerY() {
        return playerY;
    }

    /**
     * Sets the player's position.
     * @param x The X coordinate of the player's position.
     * @param y The Y coordinate of the player's position.
     */
    public void setPlayerPos(float x, float y) {
        this.playerX = x;
        this.playerY = y;
    }

    /**
     * Sets the fruit data for the level.
     * @param fruitData The fruit data to set.
     */
    public void setFruitData(float[] fruitData) {
        this.fruitData = fruitData;
    }

    /**
     * Retrieves the fruit data for the level.
     * @return The fruit data.
     */
    public float[] getFruitData() {
        return fruitData;
    }

    /**
     * Sets the count of fruits in the level.
     * @param fruitCount The count of fruits to set.
     */
    public void setFruitCount(int fruitCount) {
        this.fruitCount = fruitCount;
    }

    /**
     * Retrieves the count of fruits in the level.
     * @return The count of fruits.
     */
    public int getFruitCount() {
        return fruitCount;
    }
}