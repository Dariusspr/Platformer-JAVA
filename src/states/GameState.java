package states;

public enum GameState {
    START_MENU, MENU, INGAME, EDITOR, PAUSED;

    public static GameState state = START_MENU;
}
