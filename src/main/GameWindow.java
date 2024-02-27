package main;

import listeners.WindowEvents;

import javax.swing.*;

import static utils.Constants.GameWindow.*;

public class GameWindow {
    private JFrame jframe;
    public GameWindow(GamePanel gamePanel) {
         jframe = new JFrame(WINDOW_NAME);

        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        WindowEvents windowEvents = new WindowEvents(gamePanel);
        jframe.addWindowListener(windowEvents);
        jframe.add(gamePanel);

        jframe.setLocationRelativeTo(null);
        jframe.pack();
        jframe.setResizable(false);
        jframe.setVisible(true);

    }

    public void closeWindow() {
        jframe.dispose();
    }
}
