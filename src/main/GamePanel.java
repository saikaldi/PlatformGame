package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GamePanel extends JPanel {
//    an instance of a class that implements the MouseListener interface
    private MouseInputs mouseInputs;
    private float xDelta = 100, yDelta = 100;
    private float xDir = 1f, yDir = 1f;
    private int frames = 0;
    private long lastCheck = 0;
    private Random random;
    private Color color = new Color(15, 255, 255);

    public GamePanel() {
        random = new Random();
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
    }
    public void changeYDelta(int value) {
        this.yDelta += value;
    }
    public void setRectPox(int x, int y) {
        this.xDelta = x;
        this.yDelta = y;
    }


    public void paintComponent(Graphics g) {
//        actually, it is calling JComponent's paintComponent
//        JComponent is the superclass of JPanel
//        public class JPanel extends JComponent implements Accessible
        super.paintComponent(g);

        updateRectangle();

//        g.setColor(Color.BLUE);
//        g.setColor(new Color(15, 255, 255));
        g.setColor(color);
        g.fillRect( (int)xDelta, (int)yDelta, 200, 100);



    }

    private void updateRectangle() {
        xDelta += xDir;
//        if xDelta goes over the bound=400,
        if(xDelta > 400 || xDelta < 0){
//          it reverses the direction of x direction, 1*(-1)=-1
            xDir *= -1;
            color = getRndColor();
        }
        if(yDelta > 400 || yDelta < 0){
            yDir *= -1;
            color = getRndColor();
        }


    }

    private Color getRndColor() {

        int r = random.nextInt(255);
        int b = random.nextInt(255);
        int g = random.nextInt(255);
        return new Color(r, b, g);

    }

}
