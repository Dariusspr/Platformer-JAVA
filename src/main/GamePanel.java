package main;

import listeners.KeyboardInputs;
import listeners.MouseInputs;

import javax.swing.*;
import java.awt.*;


import static utils.Constants.Game.*;

public class GamePanel extends JPanel {
    private Game game;
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

    protected void paintComponent(Graphics g) {
        Toolkit.getDefaultToolkit().sync(); // fixes rendering lag on linux
        super.paintComponent(g);
        game.render(g);
    }

    public Game getGame() {
        return this.game;
    }
}
