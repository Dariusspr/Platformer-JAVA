package utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;

import static utils.Constants.LevelHandler.*;

public class Load {

    public static BufferedImage LoadImage(String path) {
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

    public static int[][] loadLevel(String path) {
        final String COMMA_DELIMITER = ",";
        int[][] levelData = new int[LEVEL_HEIGHT][LEVEL_MAX_COL];
        try {
            Scanner scanner = new Scanner(new File(path));
            int row = 0;
            while (scanner.hasNextLine() && row < LEVEL_HEIGHT) {
                String[] values = scanner.nextLine().split(COMMA_DELIMITER);
                for (int col = 0; col < LEVEL_MAX_COL; col++) {
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
