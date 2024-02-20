package objects;

import java.awt.geom.Rectangle2D;
import static utils.Constants.Game.*;
public class Collision {



    public static boolean canMoveLeft(Rectangle2D.Float hitbox, float speed, int[][] levelData) {
        return !(isTerrain(hitbox.x - speed, hitbox.y, levelData) || isTerrain(hitbox.x - speed, hitbox.y + hitbox.height / 2, levelData) || isTerrain(hitbox.x - speed, hitbox.y + hitbox.height, levelData));
    }

    public static boolean canMoveRight(Rectangle2D.Float hitbox, float speed, int[][] levelData) {
        return !(isTerrain(hitbox.x + hitbox.width + speed, hitbox.y, levelData) || isTerrain(hitbox.x + hitbox.width + speed, hitbox.y + hitbox.height / 2, levelData) || isTerrain(hitbox.x + hitbox.width + speed, hitbox.y + hitbox.height, levelData));
    }

    public static boolean canMoveUp(Rectangle2D.Float hitbox, float speed, int[][] levelData) {
        return !(isTerrain(hitbox.x, hitbox.y - speed, levelData) || isTerrain(hitbox.x + hitbox.width / 2, hitbox.y - speed, levelData)|| isTerrain(hitbox.x + hitbox.width, hitbox.y - speed, levelData));
    }

    public static boolean canMoveDown(Rectangle2D.Float hitbox, float speed, int[][] levelData) {
        return !(isTerrain(hitbox.x, hitbox.y + hitbox.height + speed, levelData) || isTerrain(hitbox.x + hitbox.width / 2, hitbox.y + hitbox.height + speed, levelData) || isTerrain(hitbox.x + hitbox.width, hitbox.y + hitbox.height + speed, levelData)) &&
                (isPlatform(hitbox.x, hitbox.y + hitbox.height + speed, levelData) && isPlatform(hitbox.x + hitbox.width / 2, hitbox.y + hitbox.height + speed, levelData) && isPlatform(hitbox.x + hitbox.width, hitbox.y + hitbox.height + speed, levelData));
    }

    public static boolean objectsCollide(Rectangle2D.Float hitbox1, Rectangle2D.Float hitbox2) {
        return (hitbox1.intersects(hitbox2));
    }


    private static boolean isTerrain(float x, float y, int[][] levelData) {
        int levelEndX = levelData[0].length * TILE_SIZE;
        if (x < 0 || x >= levelEndX || y < 0 || y >= PANEL_HEIGHT) {
            return true;
        }

        int rowTile = (int) (y / TILE_SIZE);
        int colTile = (int) (x / TILE_SIZE);
        int tile = levelData[rowTile][colTile];

        return !(tile == 84 || tile == 101 || tile >= 108);
    }
    private static boolean isPlatform(float x, float y, int[][] levelData) {
        int rowTile = (int) (y / TILE_SIZE);
        int colTile = (int) (x / TILE_SIZE);
        int tile = levelData[rowTile][colTile];
        return !(tile >= 113);
    }
}
