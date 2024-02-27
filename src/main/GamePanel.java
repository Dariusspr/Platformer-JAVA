package main;

import listeners.KeyboardInputs;
import listeners.MouseInputs;

import javax.swing.*;
import java.awt.*;


import static utils.Constants.Game.*;

/**
 * Represents the panel where the game is rendered.
 */
public class GamePanel extends JPanel {
    private final Game game;

    /**
     * Constructs a GamePanel object with the specified Game.
     * Initializes keyboard and mouse input listeners.
     * Sets the preferred size of the panel.
     *
     * @param game The Game object
     */
    public GamePanel(Game game){
        this.game = game;
        KeyboardInputs keyboardInputs = new KeyboardInputs(this);
        MouseInputs mouseInputs = new MouseInputs(this);

        setPanelSize();

        addKeyListener(keyboardInputs);
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);

    }

    private void setPanelSize() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Toolkit.getDefaultToolkit().sync(); // Fixes rendering lag on linux
        super.paintComponent(g);
        game.render(g);
    }

    /**
     * @return The Game object.
     */
    public Game getGame() {
        return this.game;
    }
}
