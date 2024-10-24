import Modal.GameController;
import Modal.MazeGenerator;
import View.Menu;

/**
 * Main class sets the game into play mode.
 * Main class initiates the GameController class which contains the core game logic. Then it passes
 * the gameController object to the Menu class which handles the userInput.
 */
public class Main {
    public static void main(String[] args) {
        GameController gameController = new GameController();
        Menu menu = new Menu(gameController);
        menu.playGame();
    }
}