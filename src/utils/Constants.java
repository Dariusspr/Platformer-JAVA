package utils;

import static utils.Constants.Game.*;

public class Constants {
    public static class Game {
        public static float LEVEL_SCALE = 1.0f;

        public static final float GAME_SCALE = 1.4f;

        public static final int FPS_COUNT = 144;
        public static final int UPS_COUNT = 200;

        public static final double SEC_TO_NANO = 1e9;

        public static final int TILE_INIT_SIZE = 16;
        private static final float TILE_SCALE = 1.7f;
        public static final int TILE_ROW_COUNT = 25;
        public static final int TILE_COL_COUNT = 40;
        public static final int TILE_SIZE = (int)(TILE_SCALE * TILE_INIT_SIZE * GAME_SCALE);
        public static final int PANEL_WIDTH = (int) (TILE_SIZE * TILE_COL_COUNT);
        public static final int PANEL_HEIGHT = (int) (TILE_SIZE * TILE_ROW_COUNT);
        public static int LEFT_TILE_BORDER = (int) (0.5  * TILE_COL_COUNT);
        public static int RIGHT_TILE_BORDER = (int) (0.5  * TILE_COL_COUNT);
        public static int MAX_TILE_OFFSET = LevelManager.LEVEL_MAX_COL - TILE_COL_COUNT;

        public static final int LEFT_DIRECTION = -1;
        public static final int RIGHT_DIRECTION = 1;
    }
    public static class GameWindow {
        public static final String WINDOW_NAME = "Platformer";
    }

    public static class Fruit {
        public static final int MAX_FRUIT_COUNT = 10;

        public static final int FRUIT_ANIM_SIZE = 32;
        public static final int FRUIT_ANIMATION_SPEED = 13;
        public static final int FRUIT_ANIMATION_LENGTH = 12;
        public static final float FRUIT_SCALE = 2.0f;
        public static final int FRUIT_SIZE = (int) (FRUIT_ANIM_SIZE * FRUIT_SCALE * GAME_SCALE);
        public static final int FRUIT_HITBOX_WIDTH = (int) (FRUIT_SIZE * 0.4);
        public static final int FRUIT_HITBOX_HEIGHT= (int) (FRUIT_SIZE * 0.35);
        public static final int FRUIT_WIDTH_OFFSET = (FRUIT_SIZE - FRUIT_HITBOX_WIDTH) / 2;
        public static final int FRUIT_HEIGHT_OFFSET = (int) (FRUIT_SIZE - FRUIT_HITBOX_HEIGHT * 2.0f);
    }

    public static class Player {
        public static final float PLAYER_MOVE_SPEED = 1.1f * Game.GAME_SCALE;
        public static final float PLAYER_JUMP_SPEED = 3.7f * Game.GAME_SCALE;
        public static final float GRAVITY = 0.05f * Game.GAME_SCALE;
        public static final float PLAYER_FALL_AFTER_COLLISION_SPEED = 0.8f * Game.GAME_SCALE;
        public static final int PLAYER_ANIMATION_SPEED = 13;


        public static final float PLAYER_SCALE = 1.8f;
        public static final int PLAYER_ANIM_APPEAR_SIZE = 72;
        public static final int PLAYER_ANIM_SIZE = 32;
        public  static final int PLAYER_SIZE = (int) (PLAYER_ANIM_SIZE * PLAYER_SCALE * Game.GAME_SCALE);

        public static final int PLAYER_HITBOX_WIDTH = (int) (PLAYER_SIZE * 0.55);
        public static final int PLAYER_HITBOX_HEIGHT= (int) (PLAYER_SIZE * 0.8);
        public static final int PLAYER_WIDTH_OFFSET = (PLAYER_SIZE - PLAYER_HITBOX_WIDTH) / 2;
        public static final int PLAYER_HEIGHT_OFFSET = PLAYER_SIZE - PLAYER_HITBOX_HEIGHT;

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

    public static class LevelManager {
        public static final int MAX_LEVEL_COUNT = 100;

        public static final int LEVEL_MAX_COL = 80;
        public static final int LEVEL_HEIGHT = 25;

        public static final int TERRAIN_WIDTH = 17; // TILES
        public static final int TERRAIN_HEIGHT = 7; // TILES
        public static final int BACKGROUND_SIZE = 5760;

        public static final String LEVEL_TEMPLATE = "TEMPLATE";
    }

    public static class UI {
        public final static String TIME_FORMAT = "%.2f";
        public static int BUTTON_ANIM_LENGTH = 4;
        public static int BUTTON_ANIM_HEIGHT = 32;
        public static int BUTTON_ANIM_WIDTH = 96;
        public static int BUTTON_ANIM_SPEED = 35;
        public static int BUTTON_INIT_HEIGHT = (int) (Game.GAME_SCALE * BUTTON_ANIM_HEIGHT);
        public static int BUTTON_INIT_WIDTH = (int) (Game.GAME_SCALE * BUTTON_ANIM_WIDTH);

        public static class Text {
            public static int HEIGHT = 10;
            public static int WIDTH = 8;
            public static int ROW_COUNT = 5;
            public static int COL_COUNT = 10;
            public static int SYMBOL_COUNT = 46;
        }

       public static class StartMenu {


           public static int STARTMENU_BUTTON_HEIGHT = (int) BUTTON_INIT_HEIGHT * 2;
           public static int STARTMENU_BUTTON_WIDTH = (int) BUTTON_INIT_WIDTH * 2;
           public static int PLAY_BUTTON_POSX = (int) (PANEL_WIDTH * 0.4f);
           public static int PLAY_BUTTON_POSY = (int) (PANEL_HEIGHT * 0.4f);

           public static int EXIT_BUTTON_POSX = (int) (PANEL_WIDTH * 0.4f);
           public static int EXIT_BUTTON_POSY = (int) (PANEL_HEIGHT * 0.55f);
       }
       public static class Editor {
           public static final int EDITOR_ERASE_VALUE = 84;

           public static final int EDITOR_BORDER_THICKNESS = (int) (20 * GAME_SCALE);

           public static final int GRID_SIZE = Game.TILE_SIZE;

           public static final int TERRAIN_IMG_WIDTH = 272;
           public static final int TERRAIN_IMG_HEIGHT = 112;

           public static final int SELECTOR_INIT_SIZE = 32;
           public static final int SELECTOR_FRAME_COUNT = 4;

           public static final int TERRAIN_ROW_COUNT = 7;
           public static final int TERRAIN_COL_COUNT = 17;

           public static final int TERRAIN_SELECTOR_OFFSET = (int) (3 * GAME_SCALE);

           public static final int SELECTOR_SPEED = 36;

           public static int BUTTON_HEIGHT = (int) BUTTON_INIT_HEIGHT * 2;
           public static int BUTTON_WIDTH = (int) BUTTON_INIT_WIDTH * 2;


           public static int EXIT_BUTTON_POSX = (int) (PANEL_WIDTH * 0.4f);
           public static int EXIT_BUTTON_POSY = (int) (PANEL_HEIGHT * 0.4f);

           public enum ElementType {
               TERRAIN, FRUIT, PLAYER, TRAP, ENEMY;
           }
       }

       public static class Menu {
           private static final int BANNER_INIT_WIDTH = 96;
           private static final int BANNER_INIT_HEIGHT = 32;
           private static final float BANNER_SCALE = 5;

           public static final int BANNER_WIDTH = (int) (BANNER_INIT_WIDTH * GAME_SCALE * BANNER_SCALE);
           public static final int BANNER_HEIGHT = (int) (BANNER_INIT_HEIGHT * GAME_SCALE * BANNER_SCALE);
           public static final int NAME_BANNER_X = (int) (PANEL_WIDTH / 2 - BANNER_WIDTH / 2);
           public static final int NAME_BANNER_Y = (int) (PANEL_HEIGHT * 0.2 - BANNER_HEIGHT / 2);
           public static final int TIME_BANNER_X = (int) (PANEL_WIDTH / 2 - BANNER_WIDTH / 2);
           public static final int TIME_BANNER_Y = (int) (PANEL_HEIGHT * 0.4 - BANNER_HEIGHT / 2);
           public static float MAX_BEST_TIME = 999999;
           public static String NO_TIME = "-----";

           public static int BUTTON_HEIGHT = (int) BUTTON_INIT_HEIGHT * 2;
           public static int BUTTON_WIDTH = (int) BUTTON_INIT_WIDTH * 2;
           public static int PLAY_BUTTON_POSX = (int) (PANEL_WIDTH * 0.3f);
           public static int PLAY_BUTTON_POSY = (int) (PANEL_HEIGHT * 0.55f);

           public static int EDIT_BUTTON_POSX = (int) (PANEL_WIDTH * 0.5f);
           public static int EDIT_BUTTON_POSY = (int) (PANEL_HEIGHT * 0.55f);

           public static int EXIT_BUTTON_POSX = (int) (PANEL_WIDTH * 0.4f);
           public static int EXIT_BUTTON_POSY = (int) (PANEL_HEIGHT * 0.7f);
       }

       public static class Pause {
           public static int BUTTON_HEIGHT = (int) (BUTTON_INIT_HEIGHT * 1.5f);
           public static int BUTTON_WIDTH = (int) (BUTTON_INIT_WIDTH * 1.5f);

           public static int RESTART_BUTTON_POSX = (int) (PANEL_WIDTH * 0.4f);
           public static int RESTART_BUTTON_POSY = (int) (PANEL_HEIGHT * 0.4f);

           public static int EXIT_BUTTON_POSX = (int) (PANEL_WIDTH * 0.4f);
           public static int EXIT_BUTTON_POSY = (int) (PANEL_HEIGHT * 0.5f);

       }
    }
}
