package entities;

import static utilz.Constants.PlayerConstants.*;

import main.Game;
import utilz.LoadSave;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import static utilz.HelpMethods.*;

public class Player extends Entity{
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 15; // Animation controls: frame timing (aniTick), index, and speed
    private int playerAction = IDLE; // Current player action, defaulting to idle
    private boolean moving = false, attacking = false;; // Tracks if the player is moving
    private boolean left, up, right, down, jump;
    private float playerSpeed = 2.0f;
    private int [][] lvlData;
    private float xDrawOffset = 21 * Game.SCALE;
    private float yDrawOffset = 4 * Game.SCALE;
//    Jumping / Gravity
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initHitbox(x, y, 20*Game.SCALE, 27*Game.SCALE);
    }

//    to update the player
    public void update(){
        updatePos();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g){
        g.drawImage(animations[playerAction][aniIndex], (int)(hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffset), width, height, null);
//        drawHitbox(g);
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
        if(inAir){
            if(airSpeed < 0)
                playerAction = JUMP;
            else
                playerAction = FALLING;
        }
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
        if(jump)
            jump();
        if(!left && !right && !inAir)
            return;
        float xSpeed = 0;

        if(left)
            xSpeed -= playerSpeed;
        if(right)
            xSpeed += playerSpeed;
        if(!inAir){
            if(!IsEntityOnFloor(hitbox, lvlData)){
                inAir = true;
            }
        }

        if (inAir) {
            if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else {
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
                if (airSpeed > 0)
                    resetInAir();
                else
                    airSpeed = fallSpeedAfterCollision;
                updateXPos(xSpeed);
            }

        } else
            updateXPos(xSpeed);
        moving = true;
    }

    private void jump() {
        if(inAir)
            return;
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;

    }

    private void updateXPos(float xSpeed) {
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
            hitbox.x += xSpeed;
        } else {
            hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
        }

    }


    private void loadAnimations() {  // Loads animation frames from sprite sheet into `animations` array

        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
        animations = new BufferedImage[9][6]; // Initialize 2D array for 9 actions, 6 frames each

        for (int j = 0; j < animations.length; j++)
            for (int i = 0; i < animations[j].length; i++)
                animations[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);

    }

    public void loadLvlData(int[][] lvlData){
        this.lvlData = lvlData;
        if(!IsEntityOnFloor(hitbox, lvlData))
            inAir = true;
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
    public void setJump(boolean jump) {
        this.jump = jump;
    }
}
