package main;

public class Game implements Runnable{
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
//    FPS_SET is a variable that holds the desired frames per second (FPS) for the game
    private final int FPS_SET = 120;

    public Game(){
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);
//        When you call gamePanel.requestFocus(), you're asking the gamePanel to gain focus so that
//        It can start receiving input events like keyboard presses or mouse clicks.
        gamePanel.requestFocus();
        startGameLoop();
    }

    private void startGameLoop(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
//      TimePerFrame calculates how much time (in nanoseconds) each frame should take to achieve that FPS.
        double timePerFrame = 1000000000.0 / FPS_SET;
//      LastFrame keeps track of the time when the last frame was displayed, so we know when the next frame should be drawn.
        long lastFrame = System.nanoTime();
//        now represents the current time at any given point in the loop, fetched with System.nanoTime().
//        System.nanoTime() gives the time in nanoseconds, which provides high precision for timing the frames.
        long now = System.nanoTime();

        int frames = 0;
        long lastCheck = System.nanoTime();

        while(true){

//            Gets the current time in nanoseconds.
            now = System.nanoTime();
//            This condition checks if the difference between the current time (now) and the time of the last frame
//            (lastFrame) is greater than or equal to timePerFrame.
//            If this is true, it means enough time has passed since the last frame, so it’s time to repaint the screen.
            if(now - lastFrame >= timePerFrame){
//              This triggers the component to redraw itself, updating the visuals on the screen.
                gamePanel.repaint();

//              Updates lastFrame to the current time so that the timer is reset for the next frame.
                lastFrame = now;
                frames++;
            }
            //        if one second have passed since the last fps check, we do a new fps check
//        save the newFps check as the lastFps check and repeat

            if(System.currentTimeMillis() - lastCheck >= 1000){
                lastCheck = System.currentTimeMillis();
                System.out.println("fps: " + frames);
                frames = 0;
            }
        }
    }
}
