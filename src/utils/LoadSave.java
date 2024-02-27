package utils;

import levels.Level;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static utils.Constants.LevelManager.*;
import static utils.Constants.Fruit.*;
import static utils.Constants.UI.TIME_FORMAT;

public class LoadSave {


    private static final String LEVEL_DIRECTORY = "assets/Levels/";
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
        List<String> files = new ArrayList<>();

        for (String str : Objects.requireNonNull(dir.list())) {
            if (!str.equals(LEVEL_TEMPLATE + FILE_EXTENSION)) {
                files.add(str);
            }
        }

        return files.toArray(new String[0]);
    }

//    public static Level loadLevelTemplate() {
//        return loadLevelData(LEVEL_TEMPLATE + FILE_EXTENSION);
//    }


    public static Level loadLevelData(String name) {
        String path =  LEVEL_DIRECTORY + "/" + name;
        Level level = new Level();
        int[][] terrainData = new int[LEVEL_HEIGHT][LEVEL_MAX_COL];
        float[] fruitData;
        int fruitCount = 0;
        float bestTime;
        float playerX, playerY;

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
            fruitCount = Math.min(values.length / 2, MAX_FRUIT_COUNT);
            fruitData = new float[fruitCount * 2];
            for (int i = 0; i < fruitCount ; i++) {
                fruitData[i * 2] = Float.parseFloat(values[i * 2]);
                fruitData[i * 2 + 1] = Float.parseFloat(values[i * 2 + 1]);
            }

            // Player
            String[] playerPosString = scanner.nextLine().split(COMMA_DELIMITER);
            playerX = Float.parseFloat(playerPosString[0]);
            playerY = Float.parseFloat(playerPosString[1]);

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
        level.setPlayerPos(playerX, playerY);

        return level;
    }

    public static void saveLevels(Level[] levels) {
        for (Level level : levels) {
            if (level == null) {
                break;
            }
            saveLevelData(level);
        }
    }

    public static void saveLevelData(Level level) {
        String path =  LEVEL_DIRECTORY + "/" + level.getLevelName() + FILE_EXTENSION;

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))) {

            // Terrain
            int[][] terrain = level.getTerrainData();
            for (int[] value : terrain) {
                for (int col = 0; col < terrain[0].length; col++) {
                    bufferedWriter.write(Integer.toString(value[col]));
                    bufferedWriter.write(COMMA_DELIMITER);
                }
                bufferedWriter.newLine();
            }

            // Fruits
            float[] fruits = level.getFruitData();
            for (int col = 0; col < level.getFruitCount() * 2; col++) {
                bufferedWriter.write(Float.toString(fruits[col]));
                bufferedWriter.write(COMMA_DELIMITER);
            }
            bufferedWriter.newLine();

            // Player position
            bufferedWriter.write(level.getPlayerX() + COMMA_DELIMITER + level.getPlayerY());
            bufferedWriter.newLine();
            // Best time
            float bestTime = level.getLevelBestTime();
            bufferedWriter.write(String.format(TIME_FORMAT, bestTime));
            bufferedWriter.newLine();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
