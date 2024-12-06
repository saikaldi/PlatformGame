package entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utilz.Constants.Directions.*;
import static utilz.Constants.Directions.DOWN;
import static utilz.Constants.PlayerConstants.*;

public class Player extends Entity{
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 25; // Animation controls: frame timing (aniTick), index, and speed
    private int playerAction = IDLE; // Current player action, defaulting to idle
    private int playerDir = -1; // Current direction of player, initially set to -1 (no direction)
    private boolean moving = false, attacking = false;; // Tracks if the player is moving
    private boolean left, up, right, down;
    private float playerSpeed = 2.0f;

    public Player(float x, float y) {
        super(x, y);
        loadAnimations();
    }

//    to update the player
    public void update(){
        updatePos();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g){
        g.drawImage(animations[playerAction][aniIndex], (int)x, (int)y, 128, 80, null);
    }

    private void updateAnimationTick() { // Updates animation frame based on timing
        aniTick++; // Increment animation tick counter
        if (aniTick >= aniSpeed) { // If tick count reaches animation speed threshold
            aniTick = 0; // Reset tick counter
            aniIndex++; // Move to the next frame
            if (aniIndex >= GetSpriteAmount(playerAction)) {  // If end of animation frames is reached
                aniIndex = 0;  // Reset frame index to loop animation
                attacking = false;
            }
        }
    }

    private void setAnimation() { // Sets player animation based on movement status
        int startAni = playerAction;

        if(moving)
            playerAction = RUNNING;
        else
            playerAction = IDLE;
        if(attacking)
            playerAction = ATTACK_1;
        if(startAni != playerAction)
            resetAniTick();
    }

    private void resetAniTick() {
        aniIndex = 0;
        aniSpeed = 0;
    }

    private void updatePos() {

        moving = false;

        if(left && !right){
            x-= playerSpeed;
            moving = true;
        }else if(right && !left){
            x+=playerSpeed;
            moving = true;
        }
        if(up && !down){
            y-= playerSpeed;
            moving = true;
        }else if(down && !up){
            y+=playerSpeed;
            moving = true;
        }
    }


    private void loadAnimations() {  // Loads animation frames from sprite sheet into `animations` array
        InputStream is = getClass().getResourceAsStream("/player.png");
        try {
            BufferedImage img = ImageIO.read(is);
            animations = new BufferedImage[9][6]; // Initialize 2D array for 9 actions, 6 frames each

            for (int j = 0; j < animations.length; j++) // Loop through each action
                for (int i = 0; i < animations[j].length; i++) // Loop through each frame for action `j`
                    animations[j][i] = img.getSubimage(i*64, j*40, 64, 40); // Extract frame `i` of action `j` from sprite sheet

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

    public void resetDirBooleans(){
        left = false;
        right = false;
        up = false;
        down = false;
    }

    public void setAttacking(boolean attacking){
        this.attacking = attacking;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }
}
