package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    public GamePanel() {

    }

    public void paintComponent(Graphics g) {
//        actually, it is calling JComponent's paintComponent
//        JComponent is the superclass of JPanel
//        public class JPanel extends JComponent implements Accessible
        super.paintComponent(g);

        g.fillRect(100, 100, 200, 500);

    }
}
