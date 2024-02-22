package utils;

import levels.Level;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static utils.Constants.Game.PANEL_HEIGHT;
import static utils.Constants.Game.PANEL_WIDTH;
import static utils.Constants.LevelHandler.*;
import static utils.Constants.Fruit.*;

public class Load {


    private static final String LEVEL_DIRECTORY = "assets/Levels/layout";
    private static final String COMMA_DELIMITER = ",";

    public static BufferedImage loadImage(String path) {
        BufferedImage img;
        try {
            img = ImageIO.read(new File(path));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return img;
    }

    public static String[] loadFileNames() {
        File dir = new File(LEVEL_DIRECTORY);
        return dir.list();
    }

    public static Level loadLevelData(String name) {
        String path =  LEVEL_DIRECTORY + "/" + name;

        Level level = new Level();
        int[][] terrainData = new int[LEVEL_HEIGHT][LEVEL_MAX_COL];
        int[] fruitData = new int[MAX_FRUIT_COUNT * 2];
        int fruitCount;
        float bestTime;

        try {
            // terrain
            Scanner scanner = new Scanner(new File(path));
            int row = 0;
            while (scanner.hasNextLine() && row < LEVEL_HEIGHT) {
                String[] values = scanner.nextLine().split(COMMA_DELIMITER);
                for (int col = 0; col < LEVEL_MAX_COL; col++) {
                    int value = Integer.parseInt(values[col]);
                    if (value > TERRAIN_WIDTH * TERRAIN_HEIGHT) { // exceeds tile value
                        value = 0;
                    }
                    terrainData[row][col] = value;
                }
                row++;
            }
            // traps

            // fruits
            row = 0;
            String[] values = scanner.nextLine().split(COMMA_DELIMITER);
            int count = 0;

            for (int i = 0; i < values.length / 2 && i < MAX_FRUIT_COUNT; i++) {
                int value = Integer.parseInt(values[i * 2]);
                if (value >= 0) {
                    count++;
                    fruitData[i * 2] = value;
                    fruitData[i * 2 + 1] = Integer.parseInt(values[i * 2 + 1]);
                }
            }
            fruitCount = count;
            // player

            // time
            String timeString = scanner.nextLine();
            bestTime = Float.parseFloat(timeString);
            scanner.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        level.setName(name.substring(0, name.lastIndexOf(".csv")));
        level.setTerrainData(terrainData);
        level.setFruitData(fruitData);
        level.setFruitCount(fruitCount);
        level.setBestTime(bestTime);
        return level;
    }
}
