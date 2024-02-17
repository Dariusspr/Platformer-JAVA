package states;

import main.Game;

public abstract class State {
    protected Game game;
    protected State(Game game) {
        this.game = game;
    }
}
