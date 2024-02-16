package levels;

public class Level {
    private int[][] levelData;

    public Level(int [][] levelData) {
        this.levelData = levelData;
    }

    public int getTile(int row, int col) {
        return levelData[row][col];
    }

    public int[][] getLevelData() {
        return levelData;
    }
}
