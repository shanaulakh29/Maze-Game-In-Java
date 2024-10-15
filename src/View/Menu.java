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
                "by Gurshan Singh Aulakh and \n" +
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
    public void printMaze(){
        System.out.println("Maze:");
        char [][] maze=MazeGenerator.getMazeByReference();
        for(int i=0;i<MazeGenerator.totalRows;i++){
            for(int j=0;j<MazeGenerator.totalColumns;j++){
                System.out.print(maze[i][j]+" ");
            }
            System.out.println();
        }
    }
    public void printRecordOfCheeseCollection(){
      System.out.println("Cheese collected: 0 of 5");
    }
    public char getValidUserInput(){
        System.out.println("Enter your move [WASD]");
      String inputInString=scanner.nextLine();
      while(true){
          if(inputInString.isEmpty()){
              inputInString=scanner.nextLine();
          } else if (inputInString.charAt(0)=='w'|| inputInString.charAt(0)=='d'|| inputInString.charAt(0)=='a'|| inputInString.charAt(0)=='s') {
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
          gameController.moveAllCatsRandomly();
          if(gameController.checkIfMouseLocationMatchesCheeseLocation()){
              printMaze();
              System.out.println("You WIN");
              break;
          }
          if(gameController.checkIfAnyCatLocationMatchesMouseLocation()){
              printMaze();
              System.out.println("You Lose");
             break;
          }
      }
    }
}
