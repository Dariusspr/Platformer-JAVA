package states;


import static utils.Constants.Game.LEVEL_SCALE;
/**
 * The GameState enum represents the various states of the game.
 */
public enum GameState {
    START_MENU, MENU, INGAME, EDITOR, PAUSED;

    /**
     * The current state of the game.
     */
    public static GameState state = START_MENU;

    /**
     * Indicates whether the game state has changed (That is previous != current).
     */
    public static boolean changed;

    /**
     * Sets the current state of the game to the specified state.
     * @param state The new state.
     */
    public static void setState(GameState state) {
        if (GameState.state == GameState.EDITOR) {
            LEVEL_SCALE = 1.0f;
        }
        changed = GameState.state != state;

        switch (state) {
            case START_MENU, MENU, INGAME:
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
