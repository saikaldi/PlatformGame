package main;

//The Runnable interface allows the Game class to be executed in a separate thread
public class Game implements Runnable{
    private GameWindow gameWindow;
    private GamePanel gamePanel;
//    gameThread: A thread to run the game loop independently of the main application thread.
    private Thread gameThread;
//     Frames Per Second (120). Controls how often the screen is redrawn.
    private final int FPS_SET = 120;
//    UPS_SET: Updates Per Second (200). Controls how often the game state is updated.
    private final int UPS_SET = 200;

    public Game(){
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);
//        When you call gamePanel.requestFocus(), you're asking the gamePanel to gain focus so that
//        It can start receiving input events like keyboard presses or mouse clicks.
        gamePanel.requestFocus();
        startGameLoop();
    }

    private void startGameLoop(){
//        Creates a new thread and associates it with the Game object, which implements Runnable.
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update(){
//        Calls a method in GamePanel to update the game state, such as moving objects, checking collisions, etc.
        gamePanel.updateGame();
    }
//The run method must be overridden to define what the thread does
//    The game loop is responsible for updating the game state and rendering graphics at the specified FPS and UPS rates.
    @Override
    public void run() {
//      TimePerFrame calculates how much time (in nanoseconds) each frame should take to achieve that FPS.
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

//        The time (in nanoseconds) when the loop started.
        long previousTime = System.nanoTime();

//        frames and updates: Counters to track frames rendered and updates performed.
        int frames = 0;
        int updates = 0;
        long lastCheck = System.nanoTime();

//        Track how much time has passed since the last update and frame render.
        double deltaU = 0;
        double deltaF = 0;

        while (true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

//            If enough time has passed for an update (deltaU >= 1), the game state is updated, and the updates counter is incremented.
            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }
//            If enough time has passed for a frame render (deltaF >= 1), the GamePanel is repainted, and the frames counter is incremented.
            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;

            }
        }
    }
}
