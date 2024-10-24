import Modal.GameController;
import Modal.MazeGenerator;
import View.Menu;

public class Main {
    public static void main(String[] args) {
        GameController gameController = new GameController();
        Menu menu = new Menu(gameController);
        menu.playGame();
    }
}