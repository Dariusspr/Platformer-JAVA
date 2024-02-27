package objects;

import java.awt.geom.Rectangle2D;
import static utils.Constants.Game.*;
/**
 * Provides static methods for collision detection in the game.
 */
public class Collision {

    /**
     * Checks if the object can move left without colliding with terrain.
     *
     * @param hitbox The hitbox of the object.
     * @param speed The speed of movement.
     * @param levelData The data representing the level terrain.
     * @return true if the object can move left; otherwise, false.
     */
    public static boolean canMoveLeft(Rectangle2D.Float hitbox, float speed, int[][] levelData) {
        return !(isTerrain(hitbox.x - speed, hitbox.y, levelData) ||
                isTerrain(hitbox.x - speed, hitbox.y + hitbox.height / 2, levelData) ||
                isTerrain(hitbox.x - speed, hitbox.y + hitbox.height, levelData));
    }

    /**
     * Checks if the object can move right without colliding with terrain.
     *
     * @param hitbox The hitbox of the object.
     * @param speed The speed of movement.
     * @param levelData The data representing the level terrain.
     * @return true if the object can move right; otherwise, false.
     */
    public static boolean canMoveRight(Rectangle2D.Float hitbox, float speed, int[][] levelData) {
        return !(isTerrain(hitbox.x + hitbox.width + speed, hitbox.y, levelData) ||
                isTerrain(hitbox.x + hitbox.width + speed, hitbox.y + hitbox.height / 2, levelData) ||
                isTerrain(hitbox.x + hitbox.width + speed, hitbox.y + hitbox.height, levelData));
    }

    /**
     * Checks if the object can move up without colliding with terrain.
     *
     * @param hitbox The hitbox of the object.
     * @param speed The speed of movement.
     * @param levelData The data representing the level terrain.
     * @return true if the object can move up; otherwise, false.
     */
    public static boolean canMoveUp(Rectangle2D.Float hitbox, float speed, int[][] levelData) {
        return !(isTerrain(hitbox.x, hitbox.y - speed, levelData) ||
                isTerrain(hitbox.x + hitbox.width / 2, hitbox.y - speed, levelData)||
                isTerrain(hitbox.x + hitbox.width, hitbox.y - speed, levelData));
    }

    /**
     * Checks if the object can move down without colliding with terrain or platform.
     *
     * @param hitbox The hitbox of the object.
     * @param speed The speed of movement.
     * @param levelData The data representing the level terrain.
     * @return true if the object can move down; otherwise, false.
     */
    public static boolean canMoveDown(Rectangle2D.Float hitbox, float speed, int[][] levelData) {
        return !(isTerrain(hitbox.x, hitbox.y + hitbox.height + speed, levelData) ||
                isTerrain(hitbox.x + hitbox.width / 2, hitbox.y + hitbox.height + speed, levelData) ||
                isTerrain(hitbox.x + hitbox.width, hitbox.y + hitbox.height + speed, levelData))
                &&
                (isPlatform(hitbox.x, hitbox.y + hitbox.height + speed, levelData) &&
                isPlatform(hitbox.x + hitbox.width / 2, hitbox.y + hitbox.height + speed, levelData) &&
                isPlatform(hitbox.x + hitbox.width, hitbox.y + hitbox.height + speed, levelData));
    }


    /**
     * Checks if the object hits a spike.
     *
     * @param hitbox The hitbox of the object.
     * @param levelData The data representing the level terrain.
     * @return true if the object hits a spike; otherwise, false.
     */
    public static boolean hitSpike(Rectangle2D.Float hitbox, int[][] levelData) {
        boolean left = isSpike(hitbox.x, hitbox.y, levelData) ||
                isSpike(hitbox.x, hitbox.y + hitbox.height / 2, levelData) ||
                isSpike(hitbox.x, hitbox.y + hitbox.height, levelData);
        boolean right = isSpike(hitbox.x + hitbox.width, hitbox.y, levelData) ||
                isSpike(hitbox.x + hitbox.width, hitbox.y + hitbox.height / 2, levelData) ||
                isSpike(hitbox.x + hitbox.width, hitbox.y + hitbox.height, levelData);
        boolean top = isSpike(hitbox.x, hitbox.y, levelData) ||
                isSpike(hitbox.x + hitbox.width / 2, hitbox.y, levelData) ||
                isSpike(hitbox.x + hitbox.width, hitbox.y, levelData);
        boolean down = isSpike(hitbox.x, hitbox.y + hitbox.height, levelData) ||
                isSpike(hitbox.x + hitbox.width / 2, hitbox.y + hitbox.height, levelData) ||
                isSpike(hitbox.x + hitbox.width, hitbox.y + hitbox.height, levelData);

        return left || right || top || down;
    }

    /**
     * Checks if two objects collide.
     *
     * @param hitbox1 The hitbox of the first object.
     * @param hitbox2 The hitbox of the second object.
     * @return true if the objects collide; otherwise, false.
     */
    public static boolean objectsCollide(Rectangle2D.Float hitbox1, Rectangle2D.Float hitbox2) {
        return (hitbox1.intersects(hitbox2));
    }

    /**
     * Checks if the specified coordinates represent terrain.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @param levelData The data representing the level terrain.
     * @return true if the coordinates represent terrain; otherwise, false.
     */
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

    /**
     * Checks if the specified coordinates represent spike.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @param levelData The data representing the level spike.
     * @return true if the coordinates represent spike; otherwise, false.
     */
    private static boolean isSpike(float x, float y, int[][] levelData) {
        int rowTile = (int) (y / TILE_SIZE);
        int colTile = (int) (x / TILE_SIZE);
        int tile = levelData[rowTile][colTile];
        return tile >= 108 && tile <= 111;
    }

    /**
     * Checks if the specified coordinates represent platform.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @param levelData The data representing the level terrain.
     * @return true if the coordinates represent platform; otherwise, false.
     */
    private static boolean isPlatform(float x, float y, int[][] levelData) {
        int rowTile = (int) (y / TILE_SIZE);
        int colTile = (int) (x / TILE_SIZE);
        int tile = levelData[rowTile][colTile];
        return !(tile >= 113);
    }
}
