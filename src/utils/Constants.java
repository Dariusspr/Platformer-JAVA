package utils;

public class Constants {
    public static class Game {

        public static int FPS_COUNT = 144;
        public static int UPS_COUNT = 200;

        public static double SEC_TO_NANO = 1e9;
    }
    public static class GameWindow {
        public static String WINDOW_NAME = "Platformer";
    }
    public static class GamePanel {
        public static int PANEL_WIDTH = 1280;
        public static int PANEL_HEIGHT = 800;
    }

    public static class Player {
        public static short PLAYER_SPEED = 1;
        public static short PLAYER_ANIMATION_SPEED = 20;

        public static int PLAYER_SCALE = 3;

        public static int PLAYER_ANIM_APPEAR_SIZE = 72;
        public static int PLAYER_ANIM_SIZE = 32;

        public static int PLAYER_ANIM_COUNT = 9;
        public static int PLAYER_ANIM_MAX_FRAMES = 12;
        public static int[] PLAYER_ANIM_LENGTH= {11, 7, 12, 1, 1, 6, 5, 1, 1};
        public static int PLAYER_IDLE = 0;
        public static int PLAYER_HIT = 1;
        public static int PLAYER_RUN = 2;
        public static int PLAYER_JUMP = 3;
        public static int PLAYER_FALL = 4;
        public static int PLAYER_DOUBLE_JUMP = 5;
        public static int PLAYER_WALL_JUMP = 6;
        public static int PLAYER_APPEAR = 7;
        public static int PLAYER_DISAPPEAR = 8;

    }
}
