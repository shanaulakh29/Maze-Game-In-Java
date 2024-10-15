package View;

import Modal.GameController;
import Modal.MazeGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    List<String> menuArray=new ArrayList<String>();

    Scanner scanner=new Scanner(System.in);
   GameController gameController;

  public Menu(GameController gameController) {
        this.gameController=gameController;
    }

    public void printWelcomeMessage(){
        System.out.println("----------------------------------------\n" +
                "Welcome to Cat and Mouse Maze Adventure!\n" +
                "by Gurshan Singh Aulakh and Prottoy Zaman\n" +
                "----------------------------------------");
    }
    public void printDirectionsMenu(){
        System.out.println("Directions:\n" +
                "         Find 5 cheese before a cat eats you!");
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
//    public void showOnlyNeighbours(int i,int j, int mouseLocationRow,int mouseLocationCol){
//        int [][] allEightNeighbours={{-1,0},{1,0},{0,-1},{0,1},{-1,-1},{1,-1},{-1,1},{1,1}};
//
//    }

    public void printMaze(){

        System.out.println("Maze:");
        char [][] maze=MazeGenerator.getMazeByReference();

        for(int row=0;row<MazeGenerator.totalRows;row++){
            for(int column=0;column<MazeGenerator.totalColumns;column++){
                System.out.print(maze[row][column]+" ");
//                if(gameController.isMouseNeighbour(row,column)){
//                    System.out.print(maze[row][column]+" ");
//                }
//                else{
//                    System.out.print(MazeGenerator.PATH+" ");
//                }

            }
            System.out.println();
        }
//        gameController.showMouseCheeseAndCatsOnMaze();
    }
    public void printRecordOfCheeseCollection(){
      System.out.println("Cheese collected: "+gameController.getTotalCheeseCollected()+" of "+gameController.getCheeseCollectionRequirementToWin());
    }
    public char getValidUserInput(){
        System.out.print("Enter your move [WASD]: ");
      String inputInString=scanner.nextLine();
      while(true){
          if(inputInString.isEmpty()){
              inputInString=scanner.nextLine();
          } else if (inputInString.charAt(0)=='w'|| inputInString.charAt(0)=='d'|| inputInString.charAt(0)=='a'|| inputInString.charAt(0)=='s'
          || inputInString.charAt(0)=='c') {
              break;
          }else{
              inputInString=scanner.nextLine();
          }
      }
   return inputInString.charAt(0);


    }
    public void handleTurnBasedOnInput(char input){
      switch(input){
          case 'w'->gameController.moveMouseUp();
          case 'a'->gameController.moveMouseLeft();
          case 's'->gameController.moveMouseDown();
          case 'd'->gameController.moveMouseRight();
          case 'c'-> gameController.changeWinningRequirementToOneCheese();
      }
    }

    public void playGame(){
      printWelcomeMessage();
      printDirectionsMenu();
      gameController.buildMaze();
      while(true){
          printMaze();
          printRecordOfCheeseCollection();
          char input=getValidUserInput();
          handleTurnBasedOnInput(input);
          if(gameController.checkIfAnyCatLocationMatchesMouseLocation()){
              printMaze();
              System.out.println("I'm sorry, you have been eaten!");
              break;
          }
          gameController.moveAllCatsRandomly();  //if mouse enters the block of cat then mouse loses thats why i put checkIfAnyCatLocationMatchesMouseLocation() first
          if(gameController.checkIfAnyCatLocationMatchesMouseLocation()){
              printMaze();
              System.out.println("I'm sorry, you have been eaten!");
              break;
          }
          if(gameController.checkIfMouseLocationMatchesCheeseLocation()){
              gameController.placeCheeseRandomly();
              printMaze();
              gameController.collectCheeseWhenCheeseLocationMatchesMouse();
              if(gameController.getTotalCheeseCollected()==gameController.getCheeseCollectionRequirementToWin()) {
                  System.out.println("You WIN");
                  break;
              }
          }

      }
    }
}
