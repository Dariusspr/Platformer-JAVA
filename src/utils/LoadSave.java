package utils;

import levels.Level;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

import static utils.Constants.LevelHandler.*;
import static utils.Constants.Fruit.*;

public class LoadSave {


    private static final String LEVEL_DIRECTORY = "assets/Levels/layout";
    private static final String COMMA_DELIMITER = ",";
    private static final String FILE_EXTENSION = ".csv";

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

        try(Scanner scanner = new Scanner(new File(path))) {
            // Terrain
            int row = 0;
            while (scanner.hasNextLine() && row < LEVEL_HEIGHT) {
                String[] values = scanner.nextLine().split(COMMA_DELIMITER);
                for (int col = 0; col < LEVEL_MAX_COL; col++) {
                    int value = Integer.parseInt(values[col]);
                    if (value > TERRAIN_WIDTH * TERRAIN_HEIGHT) { // Exceeds tile value
                        value = 0;
                    }
                    terrainData[row][col] = value;
                }
                row++;
            }

            // Fruits
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
            // Player

            // Time
            String timeString = scanner.nextLine();
            bestTime = Float.parseFloat(timeString);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        level.setName(name.substring(0, name.lastIndexOf(FILE_EXTENSION)));
        level.setTerrainData(terrainData);
        level.setFruitData(fruitData);
        level.setFruitCount(fruitCount);
        level.setBestTime(bestTime);

        return level;
    }

    public static void saveLevels(Level[] levels) {
        for (int i = 0; i < levels.length; i++) {
            if (levels[i] == null) {
                break;
            }
                saveLevelData(levels[i]);
        }
    }

    public static void saveLevelData(Level level) {
        String path =  LEVEL_DIRECTORY + "/" + level.getLevelName() + FILE_EXTENSION;

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))) {

            // Terrain
            int[][] terrain = level.getTerrainData();
            for (int row = 0; row < terrain.length; row++) {
                for (int col = 0; col < terrain[0].length; col++) {
                    bufferedWriter.write(Integer.toString(terrain[row][col]));
                    bufferedWriter.write(COMMA_DELIMITER);
                }
                bufferedWriter.newLine();
            }

            // Fruits
            int[] fruits = level.getFruitData();
            for (int col = 0; col < level.getFruitCount() * 2; col++) {
                bufferedWriter.write(Integer.toString(fruits[col]));
                bufferedWriter.write(COMMA_DELIMITER);
            }
            bufferedWriter.newLine();

            // Player position

            // Best time
            float bestTime = level.getLevelBestTime();
            bufferedWriter.write(String.format("%.2f", bestTime));
            bufferedWriter.newLine();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
