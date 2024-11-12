package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
//    an instance of a class that implements the MouseListener interface
    private MouseInputs mouseInputs;
    private int xDelta = 100, yDelta = 100;
    public GamePanel() {
        mouseInputs = new MouseInputs(this);
//        creates a KeyboardInputs object, which will handle what happens when keys are pressed.
//        Why Pass this?
//        When we say new KeyboardInputs(this), we’re sending a reference to the current GamePanel
//        object (by using this). This allows the KeyboardInputs object to know about the GamePanel that created it.
        addKeyListener(new KeyboardInputs(this));
//        This is a method from a JComponent (like JPanel or JFrame) that registers a MouseListener with the component so that the component can handle mouse events.
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    public void changeXDelta(int value) {
        this.xDelta += value;
        repaint();
    }
    public void changeYDelta(int value) {
        this.yDelta += value;
        repaint();
    }
    public void setRectPox(int x, int y) {
        this.xDelta = x;
        this.yDelta = y;
        repaint();
    }

    public void paintComponent(Graphics g) {
//        actually, it is calling JComponent's paintComponent
//        JComponent is the superclass of JPanel
//        public class JPanel extends JComponent implements Accessible
        super.paintComponent(g);

        g.fillRect( xDelta, yDelta, 200, 100);

    }
}
