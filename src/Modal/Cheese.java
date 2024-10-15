package Modal;

public class Cheese {
    private char cheese;
    private int cheeseLocationRow;
    private int cheeseLocationColumn;
    public Cheese(char cheese) {
        this.cheese = cheese;
    }
    public char getCheese() {
        return cheese;
    }
   public int getCheeseLocationRow(){
        return cheeseLocationRow;
   }
   public int getCheeseLocationColumn(){
        return cheeseLocationColumn;
   }
   public void setCheeseLocation(int cheeseLocationRow, int cheeseLocationColumn){
        this.cheeseLocationRow = cheeseLocationRow;
        this.cheeseLocationColumn = cheeseLocationColumn;
   }
    public int[] findRandomValidLocationForCheese(Mouse mouse){
        char[][]maze=MazeGenerator.getMazeByReference();
        int row = (int) (Math.random() * (MazeGenerator.totalRows - 2) + 1);
        int column = (int) (Math.random() * (MazeGenerator.totalColumns - 2) + 1);

        while (true) {
            if (MazeGenerator.WALL == maze[row][column] || (mouse.getMouseLocationRow()==row && mouse.getMouseLocationColumn()==column)) {
                row = (int) (Math.random() * (MazeGenerator.totalRows - 2) + 1);
                column = (int) (Math.random() * (MazeGenerator.totalColumns - 2) + 1);
            } else {
                break;
            }
        }
        setCheeseLocation(row, column);
        return new int[]{row,column};
    }

}
