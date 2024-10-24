package Modal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The GameController class manages the entire game logic by utilizing the Mouse, Cat, and MazeGenerator classes.
 * It sets the starting positions of the mouse, cats, and cheese, and manages their movement within the maze.
 * GameController ensures valid moves for both the mouse and cats by updating their positions and
 * resetting the previously occupied cells to their original state.
 * Additionally, it checks for interactions between the mouse, cats and cheese,
 * tracks the total cheese collected, and determines win or lose conditions based on the game's progress.
 * The class increments the totalCheeseCollected when the mouse eats cheese and includes functionality for cheat codes,
 * allowing players to win more easily.
 * It also tracks the mouse’s visited paths and neighboring cells during each move.
 */
public class GameController {
    private List<List<Integer>> alreadyVisitedPathAndDisclosedNeighbours = new ArrayList<>();
    private int cheeseCollectionRequirementToWin = 5;
    private char[][] maze;
    private Mouse mouse;
    private Cat firstCat;
    private Cat secondCat;
    private Cat thirdCat;
    private Cheese cheese;
    private MazeGenerator mazeGenerator;

    public GameController() {
        mazeGenerator = new MazeGenerator(15, 20);
        maze = MazeGenerator.getMazeByReference();
        mouse = new Mouse('@', 1, 1);
        firstCat = new Cat('!', 1, MazeGenerator.totalColumns - 2);
        secondCat = new Cat('!', MazeGenerator.totalRows - 2, 1);
        thirdCat = new Cat('!', MazeGenerator.totalRows - 2,
                MazeGenerator.totalColumns - 2);
        cheese = new Cheese('$');
    }

    public char getMouseSymbol() {
        return mouse.getMouse();
    }

    public char getCatsSymbol() {
        return firstCat.getCat();
    }

    public char getCheeseSymbol() {
        return cheese.getCheese();
    }

    public boolean displayMouseIfCurrentIndexMatchesMouseIndex(int row, int column) {
        return mouse.getMouseLocationRow() == row && mouse.getMouseLocationColumn() == column;
    }

    public boolean displayCatsIfCurrentIndexMatchesCatsIndex(int row, int column) {
        if (firstCat.getCatLocationRow() == row && firstCat.getCatLocationColumn() == column) {
            return true;
        } else if (secondCat.getCatLocationRow() == row && secondCat.getCatLocationColumn() == column) {
            return true;
        } else if (thirdCat.getCatLocationRow() == row && thirdCat.getCatLocationColumn() == column) {
            return true;
        } else {
            return false;
        }
    }

    public boolean displayCheeseIfCurrentIndexMatchesCheeseIndex(int row, int column) {
        return cheese.getCheeseLocationRow() == row && cheese.getCheeseLocationColumn() == column;
    }


    public void setInitialPositionOfGameComponentsOnMaze() {
        maze[mouse.getMouseLocationRow()][mouse.getMouseLocationColumn()] = mouse.getMouse();
        maze[firstCat.getCatLocationRow()][firstCat.getCatLocationColumn()] = firstCat.getCat();
        maze[secondCat.getCatLocationRow()][secondCat.getCatLocationColumn()] = secondCat.getCat();
        maze[thirdCat.getCatLocationRow()][thirdCat.getCatLocationColumn()] = thirdCat.getCat();
        placeCheeseRandomly();
        maze[cheese.getCheeseLocationRow()][cheese.getCheeseLocationColumn()] = cheese.getCheese();
    }

    public boolean isMouseCellMatchesCheeseCell(Mouse mouse, Cheese cheese) {
        return mouse.getMouseLocationRow() == cheese.getCheeseLocationRow() &&
                mouse.getMouseLocationColumn() == cheese.getCheeseLocationColumn();
    }

    public boolean isMouseCellMatchesCatCell(Mouse mouse, Cat anyCat) {
        return anyCat.getCatLocationRow() == mouse.getMouseLocationRow() &&
                anyCat.getCatLocationColumn() == mouse.getMouseLocationColumn();
    }

    public void placeMouseOverCheeseWhenMouseEatsCheese() {
        if (isMouseCellMatchesCheeseCell(mouse, cheese)) {
            maze[mouse.getMouseLocationRow()][mouse.getMouseLocationColumn()] = mouse.getMouse();
        }
    }

    public void placeCheeseRandomly() {

        Cell randomlyChoosenCheeseCell = cheese.getRandomValidLocationForCheese(mouse);
        cheese.setCheeseLocation(randomlyChoosenCheeseCell.getRow(), randomlyChoosenCheeseCell.getColumn());
        maze[randomlyChoosenCheeseCell.getRow()][randomlyChoosenCheeseCell.getColumn()] = cheese.getCheese();
    }

    public void resetCellsOccupiedByCatsAndRedisplayCheeseIfCatStepOverCheese() {

        maze[firstCat.getCatLocationRow()][firstCat.getCatLocationColumn()] = MazeGenerator.PATH;
        maze[secondCat.getCatLocationRow()][secondCat.getCatLocationColumn()] = MazeGenerator.PATH;
        maze[thirdCat.getCatLocationRow()][thirdCat.getCatLocationColumn()] = MazeGenerator.PATH;
        maze[cheese.getCheeseLocationRow()][cheese.getCheeseLocationColumn()] = cheese.getCheese();
    }

    public void moveAllCatsRandomly() {
        resetCellsOccupiedByCatsAndRedisplayCheeseIfCatStepOverCheese();
        Cell firstCatNextMoveCell = firstCat.getCatNextMove();
        firstCat.setCatLocation(firstCatNextMoveCell.getRow(), firstCatNextMoveCell.getColumn());
        maze[firstCatNextMoveCell.getRow()][firstCatNextMoveCell.getColumn()] = firstCat.getCat();

        Cell secondCatNextMoveCell = secondCat.getCatNextMove();
        secondCat.setCatLocation(secondCatNextMoveCell.getRow(), secondCatNextMoveCell.getColumn());
        maze[secondCatNextMoveCell.getRow()][secondCatNextMoveCell.getColumn()] = secondCat.getCat();

        Cell thirdCatNextMoveCell = thirdCat.getCatNextMove();
        thirdCat.setCatLocation(thirdCatNextMoveCell.getRow(), thirdCatNextMoveCell.getColumn());
        maze[thirdCatNextMoveCell.getRow()][thirdCatNextMoveCell.getColumn()] = thirdCat.getCat();
    }


    public void resetCellsOccupiedByMouse() {
        int currentMouseIndexRow = mouse.getMouseLocationRow();
        int currentMouseIndexColumn = mouse.getMouseLocationColumn();
        maze[currentMouseIndexRow][currentMouseIndexColumn] = MazeGenerator.PATH;
    }

    public boolean checkValidIndex(int LocationIndexRow, int LocationIndexColumn) {
        if (LocationIndexRow <= 0 || LocationIndexRow >= MazeGenerator.totalRows - 1 || LocationIndexColumn <= 0 ||
                LocationIndexColumn == MazeGenerator.totalColumns - 1 ||
                maze[LocationIndexRow][LocationIndexColumn] == MazeGenerator.WALL) {
            return false;
        }
        return true;
    }

    public void moveMouseAsPerUserInput(int updatedMouseLocationRow, int updatedMouseLocationColumn) {
        resetCellsOccupiedByMouse();
        mouse.setMouseLocation(updatedMouseLocationRow, updatedMouseLocationColumn);
        maze[mouse.getMouseLocationRow()][mouse.getMouseLocationColumn()] = mouse.getMouse();
    }

    public Cell getNewLocationToMoveMouseUp() {
        return new Cell(mouse.getMouseLocationRow() - 1, mouse.getMouseLocationColumn());
    }

    public Cell getNextLocationToMoveMouseDown() {
        return new Cell(mouse.getMouseLocationRow() + 1, mouse.getMouseLocationColumn());
    }

    public Cell getNextLocationToMoveMouseRight() {
        return new Cell(mouse.getMouseLocationRow(), mouse.getMouseLocationColumn() + 1);
    }

    public Cell getNextLocationToMoveMouseLeft() {
        return new Cell(mouse.getMouseLocationRow(), mouse.getMouseLocationColumn() - 1);
    }

    public boolean checkIfMouseLocationMatchesCheeseLocation() {
        return ((mouse.getMouseLocationRow() == cheese.getCheeseLocationRow()) && (mouse.getMouseLocationColumn()
                == cheese.getCheeseLocationColumn()));
    }

    public void placeXSymbolOverCatWhenAnyCatEatsMouse() {
        maze[mouse.getMouseLocationRow()][mouse.getMouseLocationColumn()] = 'X';
    }

    public boolean checkIfAnyCatLocationMatchesMouseLocation() {
        return isMouseCellMatchesCatCell(mouse, firstCat) || isMouseCellMatchesCatCell(mouse, secondCat) ||
                isMouseCellMatchesCatCell(mouse, thirdCat);
    }

    public int getCheeseCollectionRequirementToWin() {
        return cheeseCollectionRequirementToWin;
    }

    public void changeWinningRequirementToOneCheese() {
        cheeseCollectionRequirementToWin = 1;
    }

    public int getTotalCheeseCollected() {
        return cheese.getTotalCheeseCollected();
    }

    public void collectCheeseWhenCheeseLocationMatchesMouse() {
        cheese.addOneToTotalCheeseCollected();
    }

    public List<List<Integer>> lookForCurrentNeighboursAndAllVisitedAndDisclosedNeighbours() {
        int[][] allEightNeighbours = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {1, -1}, {-1, 1}, {1, 1}};
        for (int[] neighbour : allEightNeighbours) {
            int mouseNeighbourRow = mouse.getMouseLocationRow() + neighbour[0];
            int mouseNeighbourColumn = mouse.getMouseLocationColumn() + neighbour[1];
            if ((mouseNeighbourRow > 0 && mouseNeighbourRow < MazeGenerator.totalRows - 1) &&
                    (mouseNeighbourColumn > 0 && mouseNeighbourColumn < MazeGenerator.totalColumns - 1)) {
                List<Integer> currentIndex = Arrays.asList(mouseNeighbourRow, mouseNeighbourColumn);
                if (!alreadyVisitedPathAndDisclosedNeighbours.contains(currentIndex)) {
                    alreadyVisitedPathAndDisclosedNeighbours.add(currentIndex);
                }
            }
        }
        return alreadyVisitedPathAndDisclosedNeighbours;
    }

    public void buildMaze() {
        mazeGenerator.generateMaze();
        setInitialPositionOfGameComponentsOnMaze();
    }
}
