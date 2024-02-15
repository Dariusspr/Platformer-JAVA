package utils;

import javax.imageio.ImageIO;
import java.io.File;

public class Constants {
    public static class Game {

        public static final int FPS_COUNT = 144;
        public static final int UPS_COUNT = 200;

        public static final double SEC_TO_NANO = 1e9;
        public static final float GAME_SCALE = 1.0f;

        public static final int TILE_INIT_SIZE = 16;
        private static final int TILE_SCALE = 2;
        public static final int TILE_ROW_COUNT = 25;
        public static final int TILE_COL_COUNT = 40;
        public static final int TILE_SIZE = (int)(TILE_SCALE * TILE_INIT_SIZE * GAME_SCALE);
        public static final int PANEL_WIDTH = (int) (TILE_SIZE * TILE_COL_COUNT);
        public static final int PANEL_HEIGHT = (int) (TILE_SIZE * TILE_ROW_COUNT);
    }
    public static class GameWindow {
        public static final String WINDOW_NAME = "Platformer";
    }

    public static class Player {
        public static final int PLAYER_SPEED = 2;
        public static final int PLAYER_ANIMATION_SPEED = 10;

        public static final int PLAYER_SCALE = 2;

        public static final int PLAYER_ANIM_APPEAR_SIZE = 72;
        public static final int PLAYER_ANIM_SIZE = 32;

        public static final int PLAYER_ANIM_COUNT = 9;
        public static final int PLAYER_ANIM_MAX_FRAMES = 12;
        public static final int[] PLAYER_ANIM_LENGTH= {11, 7, 12, 1, 1, 6, 5, 1, 1};
        public static final int PLAYER_IDLE = 0;
        public static final int PLAYER_HIT = 1;
        public static final int PLAYER_RUN = 2;
        public static final int PLAYER_JUMP = 3;
        public static final int PLAYER_FALL = 4;
        public static final int PLAYER_DOUBLE_JUMP = 5;
        public static final int PLAYER_WALL_JUMP = 6;
        public static final int PLAYER_APPEAR = 7;
        public static final int PLAYER_DISAPPEAR = 8;

    }

    public static class Level {
        public static int TERRAIN_WIDTH = 17; // TILES
        public static int TERRAIN_HEIGHT = 7; // TILES
    }
}
