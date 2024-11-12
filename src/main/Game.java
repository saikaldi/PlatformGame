package main;

public class Game {
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    public Game(){
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);
//        When you call gamePanel.requestFocus(), you're asking the gamePanel to gain focus so that
//        it can start receiving input events like keyboard presses or mouse clicks.
        gamePanel.requestFocus();


    }
}
