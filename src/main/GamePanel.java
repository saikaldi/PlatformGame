package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import inputs.KeyboardInputs;
import inputs.MouseInputs;
import javax.swing.*;
import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

public class GamePanel extends JPanel {
//    an instance of a class that implements the MouseListener interface
    private MouseInputs mouseInputs;
    private Game game;
    public GamePanel(Game game) {
        mouseInputs = new MouseInputs(this); // Initialize mouse input handling
        this.game = game;

        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
//        This is a method from a JComponent (like JPanel or JFrame) that registers a MouseListener with the component so that the component can handle mouse events.
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);
        System.out.println("Game Panel Size: " + GAME_WIDTH+ ":" + GAME_HEIGHT);
    }

    public void updateGame(){
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.white);
        for (int i = 0; i < 64; i++)
            for (int j = 0; j < 40; j++)
                g.fillRect(i * 20, j * 20, 20, 20);

        game.render(g);
    }

    public Game getGame() {
        return game;
    }
}



