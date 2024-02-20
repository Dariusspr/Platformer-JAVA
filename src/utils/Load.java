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

    public static BufferedImage loadImage(String path) {
        BufferedImage img;
        try {
            img = ImageIO.read(new File(path));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return img;
    }

    public static String[] loadFileNames(String directory) {
        File dir = new File(directory);
        return dir.list();
    }

    public static Level loadLevelData(String path) {
        final String COMMA_DELIMITER = ",";

        Level level = new Level();
        int[][] terrainData = new int[LEVEL_HEIGHT][LEVEL_MAX_COL];
        int[][] fruitData = new int[FRUIT_COUNT][MAX_FRUIT_COUNT * 2];
        int[] fruitCount = new int[FRUIT_COUNT];

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
            while(scanner.hasNextLine() && row < FRUIT_COUNT) {
                String[] values = scanner.nextLine().split(COMMA_DELIMITER);
                int count = 0;

                for (int i = 0; i < values.length / 2 && i < MAX_FRUIT_COUNT; i++) {
                    int value = Integer.parseInt(values[i * 2]);
                    if (value >= 0) {
                        count++;
                        fruitData[row][i * 2] = value;
                        fruitData[row][i * 2 + 1] = Integer.parseInt(values[i * 2 + 1]);
                    }
                }
                fruitCount[row] = count;
                row++;
            }
            // player

            scanner.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        level.setTerrainData(terrainData);
        level.setFruitData(fruitData);
        level.setFruitCount(fruitCount);
        return level;
    }

    public static float[] loadTimes(String path) {
        final String COMMA_DELIMITER = ",";
        float[] times = new float[MAX_LEVEL_COUNT];
        try {
            Scanner scanner = new Scanner(new File(path));
            String[] values = scanner.nextLine().split(COMMA_DELIMITER);
            for (int i = 0; i < values.length && i < MAX_LEVEL_COUNT; i++) {
                times[i] = Float.parseFloat(values[i]);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return times;
    }
}
