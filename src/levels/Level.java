package levels;

public class Level {
    private int[][] levelData;
    private String name;
    private float recordTime;
    int index;

    public Level(int [][] levelData, String name, int index) {
        this.levelData = levelData;
        this.name = name;
        this.index = index;
    }

    public int getTile(int row, int col) {
        return levelData[row][col];
    }

    public int[][] getLevelData() {
        return levelData;
    }

    public String getLevelName() {
        return name;
    }

    public int getLevelIndex() {
        return index;
    }

    public void setLevelData(int[][] newData) {
        this.levelData = newData;
    }
}
