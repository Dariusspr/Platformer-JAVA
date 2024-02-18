package states;

import static utils.Constants.Game.LEVEL_SCALE;

public enum GameState {
    START_MENU, MENU, INGAME, EDITOR, PAUSED;

    public static GameState state = START_MENU;

    public static void setState(GameState state) {
        switch (state) {
            case START_MENU, MENU:
                GameState.state = state;
                break;
            case INGAME:
                if (GameState.state == GameState.EDITOR) {
                    LEVEL_SCALE = 1.0f;
                }
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
