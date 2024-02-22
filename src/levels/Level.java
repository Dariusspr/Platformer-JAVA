package levels;

public class Level {
    private String name;

    // can change during at run time
    private int[][] terrainData;
    private int[] fruitData;
    private int[] trapData;
    private int PlayerX, playerY;
    private int fruitCount;
    private float bestTime = 0;
    private int index;

    public enum LevelState {
        WON, LOST, PAUSED, OTHER;
    }

    LevelState levelState = LevelState.OTHER;

    public LevelState getLevelState() {
        return levelState;
    }

    public void setLevelState(LevelState state) {
        this.levelState =  state;
    }

    public int getTile(int row, int col) {
        return terrainData[row][col];
    }

    public int[][] getTerrainData() {
        return terrainData;
    }

    public String getLevelName() {
        return name;
    }

    public int getLevelIndex() {
        return index;
    }

    public void setTerrainData(int[][] newData) {
        this.terrainData = newData;
    }

    public float getLevelBestTime() {
        return bestTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setBestTime(float time) {
        this.bestTime = time;
    }

    public void setFruitData(int[] fruitData) {
        this.fruitData = fruitData;
    }

    public int[] getFruitData() {
        return fruitData;
    }

    public void setFruitCount(int fruitCount) {
        this.fruitCount = fruitCount;
    }

    public int getFruitCount() {
        return fruitCount;
    }

}
