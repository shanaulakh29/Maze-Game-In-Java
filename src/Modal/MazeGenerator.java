package Modal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class MazeGenerator {
    private static char[][] maze;
    private final int[][] DIRECTIONS = {{-2, 0}, {2, 0}, {0, -2}, {0, 2}};
    private final int Number_OF_RANDOMLY_SELECTED_WALLS_REMOVED = 100;
    public static final char WALL = '#';
    public static final char PATH = '.';
    public static int totalRows;
    public static int totalColumns;

    public MazeGenerator(int totalRows, int totalColumns) {
        this.maze = new char[totalRows][totalColumns];
        this.totalRows = totalRows;
        this.totalColumns = totalColumns;
    }

    public static char[][] getMazeByReference() {
        return maze;
    }

    public void fillMazeWithWalls() {
        for (int i = 0; i < totalRows; i++) {
            for (int j = 0; j < totalColumns; j++) {
                maze[i][j] = WALL;
            }
        }
    }

    public boolean isValidCell(int row, int column) {
        return (row > 0 && row < totalRows - 1) && (column > 0 && column < totalColumns - 1);
    }

    public void performDepthFirstSearchOnMaze() {
        Stack<Cell> stack = new Stack<>();
        Cell startCell = new Cell(1, 1);
        maze[startCell.row][startCell.column] = PATH;
        stack.push(startCell);
        while (!stack.isEmpty()) {
            Cell currentCell = stack.peek();
            List<Cell> neighbours = new ArrayList<>();
            for (int[] direction : DIRECTIONS) {
                int newRow = currentCell.row + direction[0];
                int newColumn = currentCell.column + direction[1];
                if (isValidCell(newRow, newColumn) && maze[newRow][newColumn] == WALL) {
                    Cell neighbourCell = new Cell(newRow, newColumn);
                    neighbours.add(neighbourCell);
                }
            }
            if (!neighbours.isEmpty()) {
                Collections.shuffle(neighbours);
//                for(Cell neighbourCell : neighbours){
//                    stack.push(neighbourCell);
//                }
                Cell anyNeighbourCell = neighbours.get(0);
//                Cell anyNeighbourCell=neighbours.get((int)(Math.random()*neighbours.size()));
                stack.push(anyNeighbourCell);
//                Cell anyNeighbourCell=neighbours.get(neighbours.size()-1);
                maze[anyNeighbourCell.row][anyNeighbourCell.column] = PATH;
                removeWallBetweenCells(currentCell, anyNeighbourCell);
            } else {
                stack.pop();
            }
        }
    }

    public void removeWallBetweenCells(Cell currentCell, Cell neighbourCell) {
        int row = (currentCell.row + neighbourCell.row) / 2;
        int column = (currentCell.column + neighbourCell.column) / 2;
        maze[row][column] = PATH;

    }

    public void removeRandomWallsToAddManyPaths() {

        for (int i = 0; i < Number_OF_RANDOMLY_SELECTED_WALLS_REMOVED; i++) {
            Cell randomCell = getRandomCellToMakePath();
            while (true) {

                int row = randomCell.row;
                int column = randomCell.column;
                if (maze[row][column] == PATH) {
                    randomCell = getRandomCellToMakePath();
                } else {
                    break;
                }
            }
            removeWallsIfConstraintsMet(randomCell);
        }
    }

    public Cell getRandomCellToMakePath() {
        int randomRow = ((int) (Math.random() * (totalRows - 2)) + 1);
        int randomColumn = ((int) (Math.random() * (totalColumns - 2)) + 1);
        return new Cell(randomRow, randomColumn);
    }

    public void removeWallsIfConstraintsMet(Cell cell) {
        int totalUndiscoveredPathsAroundSpecificCell = 0;
        if (maze[cell.row - 1][cell.column] == PATH) {
            totalUndiscoveredPathsAroundSpecificCell++;
        }
        if (maze[cell.row + 1][cell.column] == PATH) {
            totalUndiscoveredPathsAroundSpecificCell++;
        }
        if (maze[cell.row][cell.column - 1] == PATH) {
            totalUndiscoveredPathsAroundSpecificCell++;
        }
        if (maze[cell.row][cell.column + 1] == PATH) {
            totalUndiscoveredPathsAroundSpecificCell++;
        }

        if (totalUndiscoveredPathsAroundSpecificCell <= 1) {
            maze[cell.row][cell.column] = PATH;
        }
    }


    public void generateMaze() {
        fillMazeWithWalls();

        performDepthFirstSearchOnMaze();
        removeRandomWallsToAddManyPaths();
    }

}
