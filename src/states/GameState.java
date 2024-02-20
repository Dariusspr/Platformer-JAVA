package states;

import utils.Constants;

import static utils.Constants.Game.LEVEL_SCALE;

public enum GameState {
    START_MENU, MENU, INGAME, EDITOR, PAUSED;

    public static GameState state = START_MENU;

    public static boolean changed;
    public static void setState(GameState state) {
        if (GameState.state == GameState.EDITOR) {
            LEVEL_SCALE = 1.0f;
        }
        if (GameState.state != state) {
            changed = true;
        }
        else {
            changed = false;
        }
        switch (state) {
            case START_MENU, MENU:
                GameState.state = state;
                break;
            case INGAME:
                GameState.state = state;
                break;
            case EDITOR:
                LEVEL_SCALE = 0.5f;
                GameState.state = state;
                break;
            case PAUSED:
                break;
        }
    }
}
