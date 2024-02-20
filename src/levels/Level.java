package levels;

public class Level {
    private int[][] terrainData;
    private int[][] fruitData;
    private int[] fruitCount;
    private String name;
    private float bestTime = 0;
    private int index;

    public enum LevelState {
        WON, LOST, PAUSED, OTHER;
    }

    LevelState levelState = LevelState.OTHER;
    public int getTotalFruitCount() {
        int total = 0;
        for (int i = 0; i < fruitCount.length; i++) {
            total += fruitCount[i];
        }
        return total;
    }


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

    public void setFruitData(int[][] fruitData) {
        this.fruitData = fruitData;
    }

    public void setFruitCount(int[] fruitCount) {
        this.fruitCount = fruitCount;
    }

    public int[] getFruitData(int type) {
        return fruitData[type];
    }

    public int getFruitCount(int type) {
        return fruitCount[type];
    }

}
