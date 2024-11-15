package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.Directions.*;

public class GamePanel extends JPanel {
//    an instance of a class that implements the MouseListener interface
    private MouseInputs mouseInputs;
    private float xDelta = 100, yDelta = 100;
    private BufferedImage img;
    private BufferedImage[][] animations; // 2D array to hold different animations and frames for player actions
    private int aniTick, aniIndex, aniSpeed = 15; // Animation controls: frame timing (aniTick), index, and speed
    private int playerAction = IDLE; // Current player action, defaulting to idle
    private int playerDir = -1; // Current direction of player, initially set to -1 (no direction)
    private boolean moving = false; // Tracks if the player is moving

    public GamePanel() {
        mouseInputs = new MouseInputs(this); // Initialize mouse input handling
        importImg();
        loadAnimations(); // Load individual animations from the imported image
        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
//        This is a method from a JComponent (like JPanel or JFrame) that registers a MouseListener with the component so that the component can handle mouse events.
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);

    }

    private void loadAnimations() {  // Loads animation frames from sprite sheet into `animations` array
        animations = new BufferedImage[9][6]; // Initialize 2D array for 9 actions, 6 frames each

        for (int j = 0; j < animations.length; j++) // Loop through each action
            for (int i = 0; i < animations[j].length; i++) // Loop through each frame for action `j`
                animations[j][i] = img.getSubimage(i*64, j*40, 64, 40); // Extract frame `i` of action `j` from sprite sheet

    }

    private void importImg() { // Get image as InputStream from resources
        InputStream is = getClass().getResourceAsStream("/player.png");
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void setPanelSize() {
        Dimension size = new Dimension(1280, 800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }


    public void  setDirection(int direction){ // Sets player's direction and flags them as moving
        this.playerDir = direction; // Assign new direction
        moving = true; // Flag moving status to true

    }
    public void  setMoving(boolean moving){  // Sets whether player is moving or not
        this.moving = moving; // Assign moving status

    }

    private void updateAnimationTick() { // Updates animation frame based on timing

        aniTick++; // Increment animation tick counter

        if (aniTick >= aniSpeed) { // If tick count reaches animation speed threshold
            aniTick = 0; // Reset tick counter
            aniIndex++; // Move to the next frame
            if (aniIndex >= GetSpriteAmount(playerAction)) {  // If end of animation frames is reached
                aniIndex = 0;  // Reset frame index to loop animation
            }
        }
    }

    private void setAnimation() { // Sets player animation based on movement status

        if(moving)
            playerAction = RUNNING;
        else
            playerAction = IDLE;
    }
    private void updatePos() {
        if(moving){
            switch (playerDir){
                case LEFT:
                    xDelta -= 5;
                    break;
                case UP:
                    yDelta -= 5;
                    break;
                case RIGHT:
                    xDelta += 5;
                    break;
                case DOWN:
                    yDelta += 5;
                    break;
            }
        }
    }

    public void paintComponent(Graphics g) {
//        actually, it is calling JComponent's paintComponent
//        JComponent is the superclass of JPanel
//        public class JPanel extends JComponent implements Accessible
        super.paintComponent(g);

        setAnimation();
        updateAnimationTick();

        updatePos();
        g.drawImage(animations[playerAction][aniIndex], (int)xDelta, (int)yDelta, 128, 80, null);


    }



}



