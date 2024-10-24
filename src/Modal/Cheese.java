package Modal;

public class Cheese {
    private int totalCheeseCollected;
    private char cheese;
    private int cheeseLocationRow;
    private int cheeseLocationColumn;
    public Cheese(char cheese) {
        this.cheese = cheese;
        totalCheeseCollected = 0;
    }
    public int getTotalCheeseCollected() {
        return totalCheeseCollected;
    }
    public void addOneToTotalCheeseCollected() {
        totalCheeseCollected++;
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
//I added extra checks in the if statement because i do not want the cheese to be placed in the same or next column.
// Doing this makes a game little complicated.
        while (true) {
            if (MazeGenerator.WALL == maze[row][column] || (mouse.getMouseLocationRow()==row && mouse.getMouseLocationColumn()==column)||
            mouse.getMouseLocationRow()==row || mouse.getMouseLocationColumn()==column || mouse.getMouseLocationColumn()+1==column||
            mouse.getMouseLocationColumn()-1==column) {
                row = (int) (Math.random() * (MazeGenerator.totalRows - 2) + 1);
                column = (int) (Math.random() * (MazeGenerator.totalColumns - 2) + 1);
            } else {
                break;
            }
        }
        return new int[]{row,column};
    }

}
