package main;

import listeners.WindowEvents;

import javax.swing.*;

import static utils.Constants.GameWindow.*;

/**
 * Represents the main window of the game.
 */
public class GameWindow {
    private final JFrame jframe;
    /**
     * Constructs a GameWindow object with the specified GamePanel.
     * Initializes the JFrame, sets its properties, adds window event listener, and adds the game panel.
     *
     * @param gamePanel The GamePanel to be added to the window.
     */
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
