package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;

import static utils.OSValidator.*;

import static utils.Constants.GamePanel.*;

public class GamePanel extends JPanel {
    public GamePanel(){

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
        if (isLinux()) {
            Toolkit.getDefaultToolkit().sync(); // fixes rendering lag on linux
        }
        super.paintComponent(g);

    }
}
