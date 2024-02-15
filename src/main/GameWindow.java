package main;

import javax.swing.*;

import static utils.Constants.GameWindow.*;

public class GameWindow {

    public GameWindow(GamePanel gamePanel) {
        JFrame jframe = new JFrame(WINDOW_NAME);

        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jframe.add(gamePanel);

        jframe.setLocationRelativeTo(null);

        jframe.pack();

        //jframe.setResizable(false);
        jframe.setVisible(true);
    }
}
