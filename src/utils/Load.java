package utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;

import static utils.Constants.Game.*;
import static utils.Constants.LevelHandler.*;

public class Load {

    public static BufferedImage LoadImage(String path) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(path));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return img;
    }

    public static int[][] loadLevel(String path) {
        final String COMMA_DELIMITER = ",";
        int[][] levelData = new int[TILE_ROW_COUNT][TILE_COL_COUNT]; // TODO: change to level size
        try {
            Scanner scanner = new Scanner(new File(path));
            int row = 0;
            while (scanner.hasNextLine() && row < TILE_ROW_COUNT) {
                String[] values = scanner.nextLine().split(COMMA_DELIMITER);
                for (int col = 0; col < TILE_COL_COUNT; col++) {
                    int value = Integer.parseInt(values[col]);
                    if (value > TERRAIN_WIDTH * TERRAIN_HEIGHT) { // exceeds tile value
                        value = 0;
                    }
                    levelData[row][col] = value;
                }
                row++;
            }
            scanner.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return levelData;
    }
}
