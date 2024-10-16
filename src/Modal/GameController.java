package Modal;

//enum MazeSizes {
//    TOTALROWS, TOTALCOLUMNS
//
//}

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameController {
    List<List<Integer>> alreadyVistedPathAndDisclosedNeighbours=new ArrayList<>();
    private int cheeseCollectionRequirementToWin=2;
    char[][]maze;
    Mouse mouse;
    Cat firstCat;
    Cat secondCat;
    Cat thirdCat;
    Cheese cheese;
    MazeGenerator mazeGenerator;
   public char getMouseSymbol(){
       return mouse.getMouse();
   }
   public char getCatsSymbol(){
       return firstCat.getCat();
   }
   public char getCheeseSymbol(){
       return cheese.getCheese();
   }
   public boolean showMouseIfCurrentIndexMatchesMouseIndex(int row,int column){
       return mouse.getMouseLocationRow()==row && mouse.mouseLocationColumn==column;
   }
   public boolean showCatsIfCurrentIndexMatchesMouseIndex(int row,int column){
       if(firstCat.getCatLocationRow()==row && firstCat.getCatLocationColumn()==column){
           return true;
       }else if(secondCat.getCatLocationRow()==row && secondCat.getCatLocationColumn()==column){
           return true;
       }else if(thirdCat.getCatLocationRow()==row && thirdCat.getCatLocationColumn()==column){
           return true;
       }
       else{
           return false;
       }
   }
   public boolean showCheeseIfCurrentIndexMatchesMouseIndex(int row,int column){
       return cheese.getCheeseLocationRow()==row && cheese.getCheeseLocationColumn()==column;
   }
    public GameController() {
        mazeGenerator = new MazeGenerator(15, 20);
        maze= MazeGenerator.getMazeByReference();
        mouse = new Mouse('@',1,1);
        firstCat = new Cat('!',1,MazeGenerator.totalColumns - 2);
        secondCat = new Cat('!',MazeGenerator.totalRows - 2,1);
        thirdCat = new Cat('!',MazeGenerator.totalRows - 2,MazeGenerator.totalColumns - 2);
        cheese = new Cheese('$');
    }
//    public void showMouseCheeseAndCatsOnMaze(){
//        maze[mouse.getMouseLocationRow()][mouse.getMouseLocationColumn()] = mouse.getMouse();
//        maze[cheese.getCheeseLocationRow()][cheese.getCheeseLocationColumn()] = cheese.getCheese();
//        maze[firstCat.getCatLocationRow()][firstCat.getCatLocationColumn()] = firstCat.getCat();
//        maze[secondCat.getCatLocationRow()][secondCat.getCatLocationColumn()] = secondCat.getCat();
//        maze[thirdCat.getCatLocationRow()][thirdCat.getCatLocationColumn()] = thirdCat.getCat();
//    }
    public void setInitialPositionOfGameComponents() {
        maze[mouse.getMouseLocationRow()][mouse.getMouseLocationColumn()] = mouse.getMouse();

        maze[firstCat.getCatLocationRow()][firstCat.getCatLocationColumn()] = firstCat.getCat();
        maze[secondCat.getCatLocationRow()][secondCat.getCatLocationColumn()] = secondCat.getCat();
        maze[thirdCat.getCatLocationRow()][thirdCat.getCatLocationColumn()] = thirdCat.getCat();
        placeCheeseRandomly();
        maze[cheese.getCheeseLocationRow()][cheese.getCheeseLocationColumn()] = cheese.getCheese();
    }
    public void showMouseWhenMouseEatsCheese(){
        if(mouse.getMouseLocationRow()==cheese.getCheeseLocationRow() && mouse.getMouseLocationColumn()==cheese.getCheeseLocationColumn()){
            maze[mouse.getMouseLocationRow()][mouse.getMouseLocationColumn()] = mouse.getMouse();
        }
    }
    public void placeCheeseRandomly(){
        int [] randomValidLocationForCheese = cheese.findRandomValidLocationForCheese(mouse);
        cheese.setCheeseLocation(randomValidLocationForCheese[0], randomValidLocationForCheese[1]);
        maze[randomValidLocationForCheese[0]][randomValidLocationForCheese[1]] = cheese.getCheese();
    }
    public void resetPreviousCatPositionOfAllCats(){

        maze[firstCat.getCatLocationRow()][firstCat.getCatLocationColumn()]=MazeGenerator.PATH;
        maze[secondCat.getCatLocationRow()][secondCat.getCatLocationColumn()]=MazeGenerator.PATH;
        maze[thirdCat.getCatLocationRow()][thirdCat.getCatLocationColumn()]=MazeGenerator.PATH;
        maze[cheese.getCheeseLocationRow()][cheese.getCheeseLocationColumn()]=cheese.getCheese();
    }

    public void moveAllCatsRandomly(){
        resetPreviousCatPositionOfAllCats();
    int [] firstCatNextMove=firstCat.getCatNextMove();
    firstCat.setCatLocation(firstCatNextMove[0],firstCatNextMove[1]);
        maze[firstCatNextMove[0]][firstCatNextMove[1]] = firstCat.getCat();

    int [] secondCatNextMove=secondCat.getCatNextMove();
        secondCat.setCatLocation(secondCatNextMove[0],secondCatNextMove[1]);
        maze[secondCatNextMove[0]][secondCatNextMove[1]] = secondCat.getCat();

    int [] thirdCatNextMove=thirdCat.getCatNextMove();
        thirdCat.setCatLocation(thirdCatNextMove[0],thirdCatNextMove[1]);
        maze[thirdCatNextMove[0]][thirdCatNextMove[1]] = thirdCat.getCat();
    }


public void resetPreviousMousePosition(){
       int currentMouseIndexRow= mouse.getMouseLocationRow();
       int currentMouseIndexColumn= mouse.getMouseLocationColumn();
       maze[currentMouseIndexRow][currentMouseIndexColumn] = MazeGenerator.PATH;
}
    public boolean checkValidIndex(int LocationIndexRow, int LocationIndexColumn){
        if(LocationIndexRow<=0 || LocationIndexRow>=MazeGenerator.totalRows-1 || LocationIndexColumn<=0 ||
                LocationIndexColumn==MazeGenerator.totalColumns-1 || maze[LocationIndexRow][LocationIndexColumn]==MazeGenerator.WALL){
            return false;
        }
        return true;
    }
  public void moveMouseUp(){
      int updatedMouseLocationRow= mouse.getMouseLocationRow()-1;
      int updatedMouseLocationColumn= mouse.getMouseLocationColumn();
      if(checkValidIndex(updatedMouseLocationRow, updatedMouseLocationColumn)){
          resetPreviousMousePosition();
          mouse.setMouseLocation(updatedMouseLocationRow, updatedMouseLocationColumn);
          maze[mouse.getMouseLocationRow()][mouse.getMouseLocationColumn()] = mouse.getMouse();
      }

  }
  public void moveMouseDown(){
      int updatedMouseLocationRow= mouse.getMouseLocationRow()+1;
      int updatedMouseLocationColumn= mouse.getMouseLocationColumn();
      if(checkValidIndex(updatedMouseLocationRow, updatedMouseLocationColumn)){
          resetPreviousMousePosition();
          mouse.setMouseLocation(updatedMouseLocationRow,updatedMouseLocationColumn);
          maze[mouse.getMouseLocationRow()][mouse.getMouseLocationColumn()] = mouse.getMouse();
      }
  }
  public void moveMouseLeft(){
      int updatedMouseLocationRow= mouse.getMouseLocationRow();
      int updatedMouseLocationColumn= mouse.getMouseLocationColumn()-1;
      if(checkValidIndex(updatedMouseLocationRow, updatedMouseLocationColumn)){
          resetPreviousMousePosition();
          mouse.setMouseLocation(updatedMouseLocationRow, updatedMouseLocationColumn);
          maze[mouse.getMouseLocationRow()][mouse.getMouseLocationColumn()] = mouse.getMouse();
      }
  }
  public void moveMouseRight(){
      int updatedMouseLocationRow= mouse.getMouseLocationRow();
      int updatedMouseLocationColumn= mouse.getMouseLocationColumn()+1;
      if(checkValidIndex(updatedMouseLocationRow, updatedMouseLocationColumn)){
          resetPreviousMousePosition();
          mouse.setMouseLocation(updatedMouseLocationRow,updatedMouseLocationColumn);
          maze[mouse.getMouseLocationRow()][mouse.getMouseLocationColumn()] = mouse.getMouse();
      }
  }

    public boolean checkIfMouseLocationMatchesCheeseLocation(){
        return ((mouse.getMouseLocationRow()==cheese.getCheeseLocationRow())&& (mouse.getMouseLocationColumn()==cheese.getCheeseLocationColumn()));
    }
    public boolean checkIfAnyCatLocationMatchesMouseLocation(){
       if(firstCat.getCatLocationRow()==mouse.getMouseLocationRow() && firstCat.getCatLocationColumn()==mouse.getMouseLocationColumn()){
           maze[mouse.getMouseLocationRow()][mouse.getMouseLocationColumn()] = 'X';
           return true;
       }
       else if(secondCat.getCatLocationRow()==mouse.getMouseLocationRow() && secondCat.getCatLocationColumn()==mouse.getMouseLocationColumn()){
           maze[mouse.getMouseLocationRow()][mouse.getMouseLocationColumn()] = 'X';
           return true;
       }
       else if(thirdCat.getCatLocationRow()==mouse.getMouseLocationRow() && thirdCat.getCatLocationColumn()==mouse.getMouseLocationColumn()){
           maze[mouse.getMouseLocationRow()][mouse.getMouseLocationColumn()] = 'X';
           return true;
       }else{
           return false;
       }
    }
    public int getCheeseCollectionRequirementToWin(){
        return cheeseCollectionRequirementToWin;
    }
   public void changeWinningRequirementToOneCheese(){
       cheeseCollectionRequirementToWin=1;
   }
   public int getTotalCheeseCollected(){
        return cheese.getTotalCheeseCollected();
   }
   public void collectCheeseWhenCheeseLocationMatchesMouse(){
   cheese.addOneToTotalCheeseCollected();
   }



//    public boolean isMouseNeighbour(int currentRow, int currentColumn) {
//        int[][] allEightNeighbours = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {1, -1}, {-1, 1}, {1, 1}};
//        for (int[] neighbour : allEightNeighbours) {
//            int mouseNeighbourRow = mouse.getMouseLocationRow() + neighbour[0];
//            int mouseNeighbourColumn = mouse.getMouseLocationColumn() + neighbour[1];
//            if (currentRow == mouseNeighbourRow && currentColumn == mouseNeighbourColumn) {
//                alreadyVistedPathAndDisclosedNeighbours.add(Arrays.asList(currentRow,currentColumn));
//                return true;
//            }
//        }
//        return false;
//    }
public List<List<Integer>> lookForNeighboursAndAllVisitedAndDisclosedNeighbours() {
    int[][] allEightNeighbours = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {1, -1}, {-1, 1}, {1, 1}};
    for (int[] neighbour : allEightNeighbours) {
        int mouseNeighbourRow = mouse.getMouseLocationRow() + neighbour[0];
        int mouseNeighbourColumn = mouse.getMouseLocationColumn() + neighbour[1];
        if ((mouseNeighbourRow>0&& mouseNeighbourRow<MazeGenerator.totalRows-1)&&(mouseNeighbourColumn>0&& mouseNeighbourColumn<MazeGenerator.totalColumns-1)){
            List<Integer> currentIndex = Arrays.asList(mouseNeighbourRow, mouseNeighbourColumn);
            if (!alreadyVistedPathAndDisclosedNeighbours.contains(currentIndex)) {
                alreadyVistedPathAndDisclosedNeighbours.add(currentIndex);
            }
        }
    }
    return alreadyVistedPathAndDisclosedNeighbours;
}
    public void buildMaze() {
        mazeGenerator.generateMaze();
        setInitialPositionOfGameComponents();
    }
}
//(row == mouseNeighbourRow && column == mouseNeighbourColumn)&&