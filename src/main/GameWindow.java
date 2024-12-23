package main;


import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class GameWindow {
    // jframe is declared as a private instance variable, so it can be used across methods within the class.
    private JFrame jframe;
    public GameWindow(GamePanel gamePanel) {
        // Create a JFrame instance
        jframe = new JFrame();

        // On click close exit the program
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(gamePanel);
        // Bound the window in center of the screen
        jframe.setResizable(false);
        jframe.setLocationRelativeTo(null);
        // If you add components with set preferred sizes or minimum/maximum sizes, calling pack()
        // ensures the JFrame respects those sizes and sizes itself accordingly.
        jframe.pack();
        // Window to be visible
        jframe.setVisible(true);
        jframe.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                gamePanel.getGame().windowFocusLost();
            }

            @Override
            public void windowLostFocus(WindowEvent e) {

            }
        });


    }
}
