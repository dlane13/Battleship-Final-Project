/*
Darby Lane
CS2100 Section A

This is the Game class, which implements the methods in ComputerBoard and UserBoard neatly.
It also prints the two boards together in a user-friendly way.
*/
public class Game
{
   private ComputerBoard computer;
   private UserBoard player;
   
   /*
   This is the constructor for the Game class, which creates two new ComputerBoard and
   UserBoard objects and passes into each the names of the files to be used for the ship layouts.
   */
   public Game()
   {
      computer = new ComputerBoard("compFleet.txt");
      player = new UserBoard("userFleet.txt");
   }
   
   /*
   makeComputerMove calls the makeComputerMove method from UserBoard and returns the value returned by the method.
   @return returns an array of Strings that makeComputerMove returns
   */
   public String[] makeComputerMove()
   {
      return player.makeComputerMove();
      
   }
   
   /*
   makePlayerMove takes in a move as a String and converts it to a Move object, then
   calls the makePlayerMove method from the ComputerBoard class.
   @param a String for the user's move
   @return returns a String that may say that a ship has been sunk if a ship was sunk.
   */
   public String makePlayerMove(String s)
   {
      Move m = new Move(s);
      return computer.makePlayerMove(m);
   }
   
   /*
   computerDefeated calls the gameOver method in the Fleet class to check if all of the computer's ships
   have been sunk, if so returns true otherwise false
   @return returns true if all of computer's ships have been sunk, false otherwise
   */
   public boolean computerDefeated()
   {
      return computer.getFleet().gameOver();
   }
   
   /*
   userDefeated calls the gameOver method in the Fleet class to check if all of the user's ships
   have been sunk, if so returns true otherwise false
   @return returns true if all of user's ships have been sunk, false otherwise
   */
   public boolean userDefeated()
   {
      return player.getFleet().gameOver();
   }
   
   /*
   toString prints both the computer and user's boards in a user-friendly way
   @return returns a formatted String
   */
   @Override
   public String toString()
   {
      String computerBoard = "COMPUTER\n   1  2  3  4  5  6  7  8  9  10\n";
      String userBoard = "USER\n   1  2  3  4  5  6  7  8  9  10\n";
      computerBoard += computer.toString();
      userBoard += player.toString();
      return computerBoard + "\n" + userBoard;
   }
}