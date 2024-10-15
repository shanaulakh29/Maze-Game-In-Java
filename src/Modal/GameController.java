package Modal;

//enum MazeSizes {
//    TOTALROWS, TOTALCOLUMNS
//
//}

public class GameController {
    char[][]maze;
    Mouse mouse;
    Cat firstCat;
    Cat secondCat;
    Cat thirdCat;
    Cheese cheese;
    MazeGenerator mazeGenerator;

    public GameController() {
        mazeGenerator = new MazeGenerator(15, 20);
        maze= MazeGenerator.getMazeByReference();
        mouse = new Mouse('@',1,1);
        firstCat = new Cat('!',1,MazeGenerator.totalColumns - 2);
        secondCat = new Cat('!',MazeGenerator.totalRows - 2,1);
        thirdCat = new Cat('!',MazeGenerator.totalRows - 2,MazeGenerator.totalColumns - 2);
        cheese = new Cheese('$');

    }

    public void setInitialPositionOfGameComponents() {
        maze[mouse.getMouseLocationRow()][mouse.getMouseLocationColumn()] = mouse.getMouse();
        maze[firstCat.getCatLocationRow()][firstCat.getCatLocationColumn()]=firstCat.getCat();
        maze[secondCat.getCatLocationRow()][secondCat.getCatLocationColumn()]=secondCat.getCat();
        maze[thirdCat.getCatLocationRow()][thirdCat.getCatLocationColumn()]=thirdCat.getCat();
        int [] randomValidLocationForCheese = cheese.findRandomValidLocationForCheese(mouse);
        maze[randomValidLocationForCheese[0]][randomValidLocationForCheese[1]] = cheese.getCheese();
    }
    public void resetPreviousCatPositionOfAllCats(){
        maze[firstCat.getCatLocationRow()][firstCat.getCatLocationColumn()]=MazeGenerator.PATH;
        maze[secondCat.getCatLocationRow()][secondCat.getCatLocationColumn()]=MazeGenerator.PATH;
        maze[thirdCat.getCatLocationRow()][thirdCat.getCatLocationColumn()]=MazeGenerator.PATH;
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
           return true;
       }
       else if(secondCat.getCatLocationRow()==mouse.getMouseLocationRow() && secondCat.getCatLocationColumn()==mouse.getMouseLocationColumn()){
           return true;
       }
       else if(thirdCat.getCatLocationRow()==mouse.getMouseLocationRow() && thirdCat.getCatLocationColumn()==mouse.getMouseLocationColumn()){
           return true;
       }else{
           return false;
       }
    }
    public void buildMaze() {
        mazeGenerator.generateMaze();
        setInitialPositionOfGameComponents();
    }
}
