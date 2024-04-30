/*
Darby Lane
CS2100 Section A

This is the Move class, which creates move objects to be applied to the cells on the board.
Each Move object holds a row and column which are 1-based.
*/
public class Move
{
   private int row;
   private int col;
   private char[] rows = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
   
   /*
   This is the first Move class constructor, which sets the row and col fields.
   @param takes an int for the row number
   @param takes an int for the col number
   */
   public Move(int r, int c)
   {
      row = r;
      col = c;
   }
   
   /*
   This is the second Move class constructor, which takes in a String representing a move.
   It converts the first element of the string from a letter to a number.
   @param takes in a String representing the move
   */
   public Move(String move)
   {
      switch (move.charAt(0))
      {
         case 'A' -> row = 1;
         case 'B' -> row = 2;
         case 'C' -> row = 3;
         case 'D' -> row = 4;
         case 'E' -> row = 5;
         case 'F' -> row = 6;
         case 'G' -> row = 7;
         case 'H' -> row = 8;
         case 'I' -> row = 9;
         case 'K' -> row = 10;
      }
      
      col = Integer.parseInt(move.substring(1));
   }
   
   /*
   row returns the row of the move
   @return returns an int for the row
   */
   public int row()
   {
      return row;
   }
   
   /*
   col returns the col of the move
   @return returns an int for the col
   */
   public int col()
   {
      return col;
   }
   
   /*
   toString converts the move to a String that the user can understand, changing the row number to a letter.
   @return returns a formatted String
   */
   @Override
   public String toString()
   {
      return String.format("%s%d", rows[row-1], col);
   }
}