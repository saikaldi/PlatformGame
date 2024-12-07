package main;


import entities.Player;
import levels.LevelManager;

import java.awt.*;

public class Game implements Runnable{ //The Runnable interface allows the Game class to be executed in a separate thread
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread; //    gameThread: A thread to run the game loop independently of the main application thread.
    private final int FPS_SET = 120; //     Frames Per Second (120). Controls how often the screen is redrawn.
    private final int UPS_SET = 200; //    UPS_SET: Updates Per Second (200). Controls how often the game state is updated.
    private Player player;
    private LevelManager levelManager;


    public final static int TILES_DEFAULT_SIZE = 32;
    public final static float SCALE = 1f;
    public final static int TILES_IN_WIDTH = 26;
    public final static int TILES_IN_HEIGHT = 14;
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    public Game(){
        initClasses();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus(); // When you call gamePanel.requestFocus(), you're asking the gamePanel to gain focus so that, It can start receiving input events like keyboard presses or mouse clicks.
        startGameLoop();

    }

    private void initClasses() {
        levelManager = new LevelManager(this);
        player = new Player(200, 200, (int) (64 * SCALE), (int) (40 * SCALE));
        player.loadLvlData(levelManager.getCurrentOne().getLevelData());
    }

    private void startGameLoop(){

        gameThread = new Thread(this); //        Creates a new thread and associates it with the Game object, which implements Runnable.
        gameThread.start();
    }

    public void update(){
        player.update();
        levelManager.update();
    }

    public void render(Graphics g){
        levelManager.draw(g);
        player.render(g);
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
    void windowFocusLost(){
        player.resetDirBooleans();
    }
    public Player getPlayer() {
        return player;
    }
}
