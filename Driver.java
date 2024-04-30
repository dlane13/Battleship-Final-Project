/*
Darby Lane
CS2100 Section A

This is the driver for my Battleship project.
*/
import java.util.Random;
import java.util.Scanner;
public class Driver
{
   public static void main(String[] args)
   {
      //create the game
      Game game = new Game();
      
      //print board before anything happens
      System.out.println("Welcome to Battleship!\n");
      System.out.println(game);
      
      //Decide who goes first, "flip a coin"
      Random rand = new Random();
      int coin = rand.nextInt(2);
      
      if (coin == 0)
      {
         System.out.println("You won the coin toss and get to go first.");
      }
      else
      {
         System.out.println("The computer won the coin toss and will now start the game.");
      }
      
      //alternate turns between player and computer
      int turn = coin;
      Scanner keyboard = new Scanner(System.in);
      //player goes
      while (turn >= 0)
      {
         if (turn % 2 == 0)
         {
            boolean inputValid = false;
            boolean exceptionCaught = false;
            String userMoveOutput = "";
            int columnValue = 0;
            String rowValue = "";
            while (inputValid == false)
            {
               exceptionCaught = false;
               System.out.print("Your turn: ");
               String move = "";
               try
               {
                  move = keyboard.nextLine().toUpperCase();
                  userMoveOutput = game.makePlayerMove(move);
               }
               //enters random stuff
               catch (NumberFormatException e)
               {
                  System.out.println("That is not a valid move. Please try again.");
                  exceptionCaught = true;
               }
               //hits enter
               catch (StringIndexOutOfBoundsException e)
               {
                  System.out.println("That is not a valid move. Please try again.");
                  exceptionCaught = true;
               }
               if (!exceptionCaught)
               {
                  columnValue = Integer.parseInt(move.substring(1));
                  rowValue = move.substring(0,1);
                  inputValid = true;
               }
               //validates column value
               if (inputValid && (columnValue < 1 || columnValue > 10))
               {
                  inputValid = false;
                  System.out.println("That is not a valid move. Please enter a valid column.");
               }
               //validates row value
               if (rowValue.compareTo("J") > 0)
               {
                  inputValid = false;
                  System.out.println("That is not a valid move. Please enter a valid row.");
               }
            }
            if (!userMoveOutput.equals(""))
            {
               System.out.printf("Computer Says: %s\n", userMoveOutput);
            }
            if (game.computerDefeated())
            {
               turn = -2;
            }
            turn++;
         }
         //computer goes
         else
         {
            System.out.println("Computer's turn. Hit any key + enter or enter when you are ready to continue.");
            String keepGoing = keyboard.nextLine();
            String[] computerMoveOutput = game.makeComputerMove();
            //prints the computer's move
            System.out.printf("Computer chose: %s\n", computerMoveOutput[0]);
            if (computerMoveOutput[1] != null)
            {
               System.out.println(computerMoveOutput[1]);
            }
            if (game.userDefeated())
            {
               turn = -3;
            }
            turn++;
         }
         System.out.println(game);
      }
      if (turn == -1)
      {
         System.out.println("Game over! You won!");
      }
      else
      {
         System.out.println("Game over! The computer won!");
      }
   }
}