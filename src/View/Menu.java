package View;

import Modal.Cell;
import Modal.GameController;
import Modal.MazeGenerator;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Menu class displays the directions to interact with the maze game. Menu class is responsible for getting input
 * from the user and ensuring that the input is valid so that the GameController class can process that input
 * This class uses the code written inside the GameController class to actually do the checks for if mouse location
 * matches with the cheese or mouse location matches with cat. This class also print the win or lose message based
 * on the game state.
 */
public class Menu {
    private final char MOVE_UP_CHARACTER = 'w';
    private final char MOVE_DOWN_CHARACTER = 's';
    private final char MOVE_LEFT_CHARACTER = 'a';
    private final char MOVE_RIGHT_CHARACTER = 'd';
    private final char Maze_Map_CHARACTER = 'm';
    private final char CHEAT_CODE_CHARACTER = 'c';
    private final char HELP_CHARACTER = '?';
    List<List<Integer>> alreadyVisitedPathAndDisclosedNeighbours = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);
    GameController gameController;

    public Menu(GameController gameController) {
        this.gameController = gameController;
    }

    private void printWelcomeMessage() {
        System.out.println("----------------------------------------\n" +
                "Welcome to Cat and Mouse Maze Adventure!\n" +
                "by Gurshan Singh Aulakh and Prottoy Zaman\n" +
                "----------------------------------------");
    }

    private void printDirectionsMenu() {
        System.out.println("Directions:\n" +
                "         Find " + gameController.getCheeseCollectionRequirementToWin() +
                " cheese before a cat eats you!");
        System.out.println("LEGEND:\n" +
                "         #: Wall\n" +
                "         @: You (a mouse)\n" +
                "         !: Cat\n" +
                "         $: Cheese\n" +
                "         .: Unexplored space");
        System.out.println("Moves:\n" +
                "         Use W (up), A (left), S (down) and D (right) to move.\n" +
                "         (You must press enter after each move).");

    }

    //wrote the following method by myself but i was getting duplicates when i printed the maze.
    //So i got idea from chatgpt that i can use a boolean flag.
    private void printMazeCellConditionally(int row, int column, char[][] maze) {
        alreadyVisitedPathAndDisclosedNeighbours =
                gameController.lookForCurrentNeighboursAndAllVisitedAndDisclosedNeighbours();
        boolean isCurrentIndexPrinted = false;
        for (List<Integer> alreadyVistedPathAndDisclosedNeighbour : alreadyVisitedPathAndDisclosedNeighbours) {
            int visitedOrNeighbourRow = alreadyVistedPathAndDisclosedNeighbour.get(0);
            int visitedOrNeighbourColumn = alreadyVistedPathAndDisclosedNeighbour.get(1);
            if (visitedOrNeighbourRow == row && visitedOrNeighbourColumn == column) {
                System.out.print(maze[row][column] == MazeGenerator.PATH ? "  " : maze[row][column] + " ");
                isCurrentIndexPrinted = true;
            }
        }
        if (!isCurrentIndexPrinted) {
            if ((row == 0 && column < MazeGenerator.totalColumns) || (row < MazeGenerator.totalRows && column == 0) ||
                    (row == MazeGenerator.totalRows - 1 && column < MazeGenerator.totalColumns) ||
                    (column == MazeGenerator.totalColumns - 1 && row < MazeGenerator.totalRows)) {
                System.out.print(MazeGenerator.WALL + " ");
            } else if (gameController.displayMouseIfCurrentIndexMatchesMouseIndex(row, column)) {
                System.out.print(gameController.getMouseSymbol() + " ");
            } else if (gameController.displayCatsIfCurrentIndexMatchesCatsIndex(row, column)) {
                System.out.print(gameController.getCatsSymbol() + " ");
            } else if (gameController.displayCheeseIfCurrentIndexMatchesCheeseIndex(row, column)) {
                System.out.print(gameController.getCheeseSymbol() + " ");
            } else {
                System.out.print(MazeGenerator.PATH + " ");
            }
        }
    }

    private void printMazeByOnlyShowingPathsVisited() {

        System.out.println("Maze:");
        char[][] maze = MazeGenerator.getMazeByReference();

        IntStream.range(0, MazeGenerator.totalRows)
                .forEach(row -> {
                    IntStream.range(0, MazeGenerator.totalColumns)
                            .forEach(column -> printMazeCellConditionally(row, column, maze));
                    System.out.println();
                });

        printRecordOfCheeseCollection();
    }

    private void printMazeAndShowAllTheComponents() {
        System.out.println("Maze:");
        char[][] maze = MazeGenerator.getMazeByReference();
        IntStream.range(0, MazeGenerator.totalRows).
                forEach(row -> {
                    IntStream.range(0, MazeGenerator.totalColumns).
                            forEach(column -> System.out.print(maze[row][column] + " "));
                    System.out.println();
                });
        printRecordOfCheeseCollection();

    }

    private void printRecordOfCheeseCollection() {
        System.out.println("Cheese collected: " + gameController.getTotalCheeseCollected() + " of "
                + gameController.getCheeseCollectionRequirementToWin());
    }

    private char getValidUserInput() {
        System.out.print("Enter your move [WASD]: ");
        String inputInString = scanner.nextLine();
        inputInString = inputInString.toLowerCase();
        while (true) {
            if (inputInString.isEmpty()) {
                System.out.println("Invalid move. Please enter just A (left), S (down), D (right), or W (up).");
                System.out.print("Enter your move [WASD]: ");
                inputInString = scanner.nextLine();
            } else if (inputInString.charAt(0) == MOVE_UP_CHARACTER || inputInString.charAt(0) == MOVE_DOWN_CHARACTER
                    || inputInString.charAt(0) == MOVE_LEFT_CHARACTER || inputInString.charAt(0) == MOVE_RIGHT_CHARACTER
                    || inputInString.charAt(0) == CHEAT_CODE_CHARACTER || inputInString.charAt(0) == Maze_Map_CHARACTER
                    || inputInString.charAt(0) == HELP_CHARACTER) {
                break;
            } else {
                System.out.println("Invalid move. Please enter just A (left), S (down), D (right), or W (up).");
                System.out.print("Enter your move [WASD]: ");
                inputInString = scanner.nextLine();
            }
        }
        return inputInString.charAt(0);
    }

    private boolean isValidNextCell(int row, int column) {
        return gameController.checkValidIndex(row, column);
    }

    private Cell getNextMoveLocation(char input) {
        return switch (input) {
            case MOVE_UP_CHARACTER -> gameController.getNewLocationToMoveMouseUp();
            case MOVE_LEFT_CHARACTER -> gameController.getNextLocationToMoveMouseLeft();
            case MOVE_DOWN_CHARACTER -> gameController.getNextLocationToMoveMouseDown();
            case MOVE_RIGHT_CHARACTER -> gameController.getNextLocationToMoveMouseRight();
            default -> null;
        };
    }

    private char handleTurnBasedOnInput(char input) {
        boolean didMouseTakeItsValidTurn = false;

        while (!didMouseTakeItsValidTurn) {
            Cell nextMoveCell = getNextMoveLocation(input);

            if (nextMoveCell == null || !(isValidNextCell(nextMoveCell.getRow(), nextMoveCell.getColumn()))) {
                System.out.println("Invalid move: you cannot move through walls!");
                input = getValidUserInput();
                if (input == Maze_Map_CHARACTER || input == CHEAT_CODE_CHARACTER || input == HELP_CHARACTER) {
                    return input;
                }
            } else {
                gameController.moveMouseAsPerUserInput(nextMoveCell.getRow(), nextMoveCell.getColumn());
                didMouseTakeItsValidTurn = true;
            }
        }
        return input;
    }


    private char handleInputsThatAreNotRelatedToTurn(char input) {
        while (true) {
            if (input == HELP_CHARACTER) {
                printDirectionsMenu();
                input = getValidUserInput();
            } else if (input == Maze_Map_CHARACTER) {
                printMazeAndShowAllTheComponents();
                input = getValidUserInput();
            } else if (input == CHEAT_CODE_CHARACTER) {
                gameController.changeWinningRequirementToOneCheese();
                input = getValidUserInput();
            } else {
                return input;
            }
        }
    }

    private void printGameLoseMessageWhenMouseLocationMatchesCatLocation() {
        gameController.placeXSymbolOverCatWhenAnyCatEatsMouse();
        printMazeByOnlyShowingPathsVisited();
        System.out.println("I'm sorry, you have been eaten!");
        printMazeAndShowAllTheComponents();
        System.out.println("GAME OVER; please try again.");
    }

    private void buildMazeAndPrintGreetingAndDirectionsMenu() {
        printWelcomeMessage();
        printDirectionsMenu();
        gameController.buildMaze();
    }

    private boolean MoveCatsAndCheckIfCatCatchesMouse(boolean isCatCatchesMouse) {
        if (gameController.checkIfAnyCatLocationMatchesMouseLocation()) {
            printGameLoseMessageWhenMouseLocationMatchesCatLocation();
            isCatCatchesMouse = true;
            return isCatCatchesMouse;
        }
        gameController.moveAllCatsRandomly();

        if (gameController.checkIfAnyCatLocationMatchesMouseLocation()) {
            printGameLoseMessageWhenMouseLocationMatchesCatLocation();
            isCatCatchesMouse = true;
        }
        return isCatCatchesMouse;
    }

    private boolean checkIfMouseEatRequiredCheese(boolean isMouseEatRequiredCheese) {
        if (gameController.checkIfMouseLocationMatchesCheeseLocation()) {
            gameController.placeMouseOverCheeseWhenMouseEatsCheese();
            gameController.collectCheeseWhenCheeseLocationMatchesMouse();
            if (gameController.getTotalCheeseCollected() == gameController.getCheeseCollectionRequirementToWin()) {
                System.out.println("Congratulations! You won!");
                printMazeAndShowAllTheComponents();
                isMouseEatRequiredCheese = true;
                return isMouseEatRequiredCheese;
            }
            gameController.placeCheeseRandomly();
        }
        return isMouseEatRequiredCheese;
    }

    public void playGame() {
        boolean isCatCatchesMouse = false;
        boolean isMouseEatRequiredCheese = false;
        buildMazeAndPrintGreetingAndDirectionsMenu();
        printMazeByOnlyShowingPathsVisited();
        char input = getValidUserInput();
        while (true) {
            input = handleInputsThatAreNotRelatedToTurn(input);
            input = handleTurnBasedOnInput(input);

            if (input != Maze_Map_CHARACTER && input != CHEAT_CODE_CHARACTER && input != HELP_CHARACTER) {
                isCatCatchesMouse = MoveCatsAndCheckIfCatCatchesMouse(isCatCatchesMouse);
                isMouseEatRequiredCheese = checkIfMouseEatRequiredCheese(isMouseEatRequiredCheese);
                if (isMouseEatRequiredCheese || isCatCatchesMouse) {
                    break;
                }
                printMazeByOnlyShowingPathsVisited();
                input = getValidUserInput();
            }
        }
    }
}
