package View;

import Modal.GameController;
import Modal.MazeGenerator;

import java.util.*;
import java.util.stream.Collectors;

public class Menu {
    List<List<Integer>> alreadyVistedPathAndDisclosedNeighbours = new ArrayList<>();

    Scanner scanner = new Scanner(System.in);
    GameController gameController;

    public Menu(GameController gameController) {
        this.gameController = gameController;
    }

    public void printWelcomeMessage() {
        System.out.println("----------------------------------------\n" +
                "Welcome to Cat and Mouse Maze Adventure!\n" +
                "by Gurshan Singh Aulakh and Prottoy Zaman\n" +
                "----------------------------------------");
    }

    public void printDirectionsMenu() {
        System.out.println("Directions:\n" +
                "         Find " + gameController.getCheeseCollectionRequirementToWin() + " cheese before a cat eats you!");
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
    public void printMaze() {

        System.out.println("Maze:");
        char[][] maze = MazeGenerator.getMazeByReference();

        for (int row = 0; row < MazeGenerator.totalRows; row++) {
            for (int column = 0; column < MazeGenerator.totalColumns; column++) {
//                System.out.print(maze[row][column]+" ");
                alreadyVistedPathAndDisclosedNeighbours = gameController.lookForNeighboursAndAllVisitedAndDisclosedNeighbours();
//            List<List<Integer>>alreadyVisitedPathWithoutDiplicates=alreadyVistedPathAndDisclosedNeighbours.stream().distinct().collect(Collectors.toList());
                boolean isCurrentIndexPrinted = false;
                for (List<Integer> alreadyVistedPathAndDisclosedNeighbour : alreadyVistedPathAndDisclosedNeighbours) {
                    int visitedOrNeighbourRow = alreadyVistedPathAndDisclosedNeighbour.get(0);
                    int visitedOrNeighbourColumn = alreadyVistedPathAndDisclosedNeighbour.get(1);
                    if (visitedOrNeighbourRow == row && visitedOrNeighbourColumn == column) {
                        System.out.print(maze[row][column] == MazeGenerator.PATH ? "  " : maze[row][column] + " ");
                        isCurrentIndexPrinted = true;
                    }
                }

                if (!isCurrentIndexPrinted) {
                    if ((row == 0 && column < MazeGenerator.totalColumns) || (row < MazeGenerator.totalRows && column == 0) ||
                            (row == MazeGenerator.totalRows - 1 && column < MazeGenerator.totalColumns) || (column == MazeGenerator.totalColumns - 1 && row < MazeGenerator.totalRows)) {
                        System.out.print(MazeGenerator.WALL + " ");
                    } else if (gameController.showMouseIfCurrentIndexMatchesMouseIndex(row, column)) {
                        System.out.print(gameController.getMouseSymbol() + " ");
                    } else if (gameController.showCatsIfCurrentIndexMatchesCatsIndex(row, column)) {
                        System.out.print(gameController.getCatsSymbol() + " ");
                    } else if (gameController.showCheeseIfCurrentIndexMatchesCheeseIndex(row, column)) {
                        System.out.print(gameController.getCheeseSymbol() + " ");
                    } else {
                        System.out.print(MazeGenerator.PATH + " ");
                    }
                }
            }
            System.out.println();
        }
        printRecordOfCheeseCollection();
    }

    public void printMazeAndShowAllTheComponents() {
        System.out.println("Maze:");
        char[][] maze = MazeGenerator.getMazeByReference();
        for (int row = 0; row < MazeGenerator.totalRows; row++) {
            for (int column = 0; column < MazeGenerator.totalColumns; column++) {
                System.out.print(maze[row][column] + " ");
            }
            System.out.println();
        }
        printRecordOfCheeseCollection();

    }

    public void printRecordOfCheeseCollection() {
        System.out.println("Cheese collected: " + gameController.getTotalCheeseCollected() + " of " + gameController.getCheeseCollectionRequirementToWin());
    }

    public char getValidUserInput() {
        System.out.print("Enter your move [WASD]: ");
        String inputInString = scanner.nextLine();
        inputInString = inputInString.toLowerCase();
        while (true) {
            if (inputInString.isEmpty()) {
                System.out.println("Invalid move. Please enter just A (left), S (down), D (right), or W (up).");
                System.out.print("Enter your move [WASD]: ");
                inputInString = scanner.nextLine();
            } else if (inputInString.charAt(0) == 'w' || inputInString.charAt(0) == 'd' || inputInString.charAt(0) == 'a' || inputInString.charAt(0) == 's'
                    || inputInString.charAt(0) == 'c' || inputInString.charAt(0) == 'm' || inputInString.charAt(0) == '?') {
                break;
            } else {
                System.out.println("Invalid move. Please enter just A (left), S (down), D (right), or W (up).");
                System.out.print("Enter your move [WASD]: ");
                inputInString = scanner.nextLine();
            }
        }
        return inputInString.charAt(0);
    }

    public boolean ifValidNextLocation(int row, int column) {
        return gameController.checkValidIndex(row, column);
    }

    public char handleTurnBasedOnInput(char input) {
        int[] nextMoveLocation = null;
        boolean didMouseTakeItsValidTurn = false;
        while (!didMouseTakeItsValidTurn) {
            switch (input) {
                case 'w' -> {
                    nextMoveLocation = gameController.getNewLocationToMoveMouseUp();
                    if (!(ifValidNextLocation(nextMoveLocation[0], nextMoveLocation[1]))) {
                        System.out.println("Invalid move: you cannot move through walls!");
                        input = getValidUserInput();
                    } else {

                        gameController.moveMouseAsPerUserInput(nextMoveLocation[0], nextMoveLocation[1]);
                        didMouseTakeItsValidTurn = true;
                    }
                }
                case 'a' -> {
                    nextMoveLocation = gameController.getNextLocationToMoveMouseLeft();
                    if (!(ifValidNextLocation(nextMoveLocation[0], nextMoveLocation[1]))) {
                        System.out.println("Invalid move: you cannot move through walls!");
                        input = getValidUserInput();
                    } else {
                        gameController.moveMouseAsPerUserInput(nextMoveLocation[0], nextMoveLocation[1]);
                        didMouseTakeItsValidTurn = true;
                    }

                }
                case 's' -> {
                    nextMoveLocation = gameController.getNextLocationToMoveMouseDown();
                    if (!(ifValidNextLocation(nextMoveLocation[0], nextMoveLocation[1]))) {
                        System.out.println("Invalid move: you cannot move through walls!");
                        input = getValidUserInput();
                    } else {
                        gameController.moveMouseAsPerUserInput(nextMoveLocation[0], nextMoveLocation[1]);
                        didMouseTakeItsValidTurn = true;
                    }
                }
                case 'd' -> {
                    nextMoveLocation = gameController.getNextLocationToMoveMouseRight();
                    if (!(ifValidNextLocation(nextMoveLocation[0], nextMoveLocation[1]))) {
                        System.out.println("Invalid move: you cannot move through walls!");
                        input = getValidUserInput();
                    } else {
                        gameController.moveMouseAsPerUserInput(nextMoveLocation[0], nextMoveLocation[1]);
                        didMouseTakeItsValidTurn = true;
                    }
                }
                default -> {
                    return input;
                }
            }
        }
        return input;

    }

    public char handleInputsThatAreNotRelatedToTurn(char input) {
        while (true) {
            if (input == '?') {
                printDirectionsMenu();
                input = getValidUserInput();
            } else if (input == 'm') {
                printMazeAndShowAllTheComponents();
                input = getValidUserInput();
            } else if (input == 'c') {
                gameController.changeWinningRequirementToOneCheese();
                input = getValidUserInput();
            } else {
                return input;
            }
        }
    }

    public void printGameLoseMessageWhenMouseLocationMatchesCatLocation() {
        gameController.placeXSymbolOverCatWhenAnyCatEatsMouse();
        printMaze();
        System.out.println("I'm sorry, you have been eaten!");
        printMazeAndShowAllTheComponents();
        System.out.println("GAME OVER; please try again.");
    }
    public void buildMazeAndPrintGreetingAndDirectionsMenu(){
        printWelcomeMessage();
        printDirectionsMenu();
        gameController.buildMaze();
    }

    public void playGame() {
        buildMazeAndPrintGreetingAndDirectionsMenu();
        while (true) {
            printMaze();
            char input = getValidUserInput();
            input = handleInputsThatAreNotRelatedToTurn(input);
            input = handleTurnBasedOnInput(input);
            if (input != 'm' && input != 'c' && input != '?') {
                if (gameController.checkIfAnyCatLocationMatchesMouseLocation()) {
                    printGameLoseMessageWhenMouseLocationMatchesCatLocation();
                    break;
                }
                gameController.moveAllCatsRandomly();  //if mouse enters the block of cat then mouse loses that is why i put checkIfAnyCatLocationMatchesMouseLocation() first
                if (gameController.checkIfAnyCatLocationMatchesMouseLocation()) {
                    printGameLoseMessageWhenMouseLocationMatchesCatLocation();
                    break;
                }
                if (gameController.checkIfMouseLocationMatchesCheeseLocation()) {
                    gameController.placeMouseOverCheeseWhenMouseEatsCheese();
                    gameController.collectCheeseWhenCheeseLocationMatchesMouse();
                    if (gameController.getTotalCheeseCollected() == gameController.getCheeseCollectionRequirementToWin()) {
                        System.out.println("Congratulations! You won!");
                        printMazeAndShowAllTheComponents();
                        break;
                    }
                    gameController.placeCheeseRandomly();
                }
            }
        }
    }
}


//    public void printMaze(){
//
//        System.out.println("Maze:");
//        char [][] maze=MazeGenerator.getMazeByReference();
//
//        for(int row=0;row<MazeGenerator.totalRows;row++){
//            for(int column=0;column<MazeGenerator.totalColumns;column++){
////                System.out.print(maze[row][column]+" ");
//                if(gameController.isMouseNeighbour(row,column)){
//                    alreadyVistedPathAndDisclosedNeighbours.add(Arrays.asList(row,column));
//                    System.out.print(maze[row][column]==MazeGenerator.PATH?"  ":maze[row][column]+" ");
//                }
//                else if((row==0 &&column<MazeGenerator.totalColumns)||(row<MazeGenerator.totalRows&& column==0)||
//                        (row==MazeGenerator.totalRows-1 && column<MazeGenerator.totalColumns)|| (column==MazeGenerator.totalColumns-1 && row<MazeGenerator.totalRows)){
//                    System.out.print(MazeGenerator.WALL+" ");
//                }else if(gameController.showMouseIfCurrentIndexMatchesMouseIndex(row,column)){
//                    System.out.print(gameController.getMouseSymbol()+" ");
//                }else if(gameController.showCatsIfCurrentIndexMatchesMouseIndex(row,column)){
//                    System.out.print(gameController.getCatsSymbol()+" ");
//                }else if(gameController.showCheeseIfCurrentIndexMatchesMouseIndex(row,column)){
//                    System.out.print(gameController.getCheeseSymbol()+" ");
//                }
//                else{
//                    System.out.print(MazeGenerator.PATH+" ");
//                }
//            }
//            System.out.println();
//        }
//
//
//    }