/*
Darby Lane
CS2100 Section A

UserBoard is a subclass of the abstract class Board. This is the class where I implement the intelligence factor.
It makes moves by the computer on the user's board, and also has a toString that prints the user's board in a user-friendly way.
*/
import java.util.Random;
import java.util.ArrayList;
public class UserBoard extends Board
{
   private ArrayList<Move> moves = new ArrayList<>();
   private Random rand;
   //for intelligence
   private ArrayList<Move> successfulMoves = new ArrayList<>();
   
   /*
   This is the constructor for the UserBoard class, it calls the superclass constructor to create the board layout,
   as well as creates an ArrayList of all possible moves that the computer can make on the user's board.
   @param takes in a String for the file name to pass into the superclass constructor
   */
   public UserBoard(String s)
   {
      super(s);
      for (int r = 1;r<=10;r++)
      {
         for (int c = 1;c<=10;c++)
         {
            Move m = new Move(r, c);
            moves.add(m);
         }
      }
      rand = new Random();
   }
   
   /*
   makeComputerMove makes the computer's moves on the user's board. This is the method where intelligence is implemented.
   The computer makes random moves until it gets a hit, then it tests the surrounding cells of that hit on the following moves.
   @return returns an array of Strings: the first element is the move the computer made, and the second element may contain a message
   about the type of ship that was sunk if the move sunk a ship, otherwise that element is null.
   */
   public String[] makeComputerMove()
   {
      //chooses a random move from arrayList of moves, then removes it
      int moveIndex;
      Move move = null;
      //add intelligence! Keep track of successful moves, once successful moves have been made, pick randomly from surrounding cells
      //until ship is sunk. also make sure to remove move from moves arraylist each time so that computer still can't make same move twice.
      //once ship is sunk, remove all moves from arraylist that correspond with that ship.
      boolean moveValid = false;
      if (successfulMoves.size() > 0)
      {
         int currentMoveIndex = -1;
         while (moveValid == false)
         {
            moveIndex = rand.nextInt(successfulMoves.size());
            Move originalMove = successfulMoves.get(moveIndex);
            //pick a random row or number next to the originalMove
            int moveRow = 0;
            if (originalMove.row() == 10)
               moveRow = rand.nextInt(9, 11);
            else if (originalMove.row() == 1)
               moveRow = rand.nextInt(1, 3);
            else
               moveRow = rand.nextInt(originalMove.row() - 1, originalMove.row() + 2);
                  
            int moveCol = 0;
            if (originalMove.col() == 10)
               moveCol = rand.nextInt(9, 11);
            else if (originalMove.col() == 1)
               moveCol = rand.nextInt(1, 3);
            else
               moveCol = rand.nextInt(originalMove.col() - 1, originalMove.col() + 2);
               
            //now that we have the new move's column and row number, we create a new move with those values
            move = new Move(moveRow, moveCol);
            //check if this move was already made, and if so find a new move
            for (int i = 0; i < moves.size(); i++)
            {
               if (moves.get(i).toString().equals(move.toString()))
               {
                  currentMoveIndex = i;
                  moveValid = true;
               }
            }
            //check if the new move is diagonal to the original move, if so pick a new move
            //because ships are not placed diagonally
            if (move.row() != originalMove.row() && move.col() != originalMove.col())
            {
               moveValid = false;
            }
         }
         moves.remove(currentMoveIndex);
      }
      else
      {
         moveIndex = rand.nextInt(moves.size() - 1);
         move = moves.get(moveIndex);
         moves.remove(moveIndex);
      }
      //applies the move to the layout
      CellStatus originalCell = applyMoveToLayout(move);
      //if move hit a ship, add to successful moves array
      boolean sunk = false;
      ShipType shipType = null;
      if (!originalCell.toString().substring(1).equals("o"))
      {
         successfulMoves.add(move);
         switch (originalCell)
         {
            case AIRCRAFT_CARRIER -> shipType = ShipType.ST_AIRCRAFT_CARRIER;
            case BATTLESHIP -> shipType = ShipType.ST_BATTLESHIP;
            case CRUISER -> shipType = ShipType.ST_CRUISER;
            case DESTROYER -> shipType = ShipType.ST_DESTROYER;
            case SUB -> shipType = ShipType.ST_SUB;
            default -> System.out.println("None of the above");
         }
         //check if hit sunk a ship
         sunk = getFleet().updateFleet(shipType);
      }
      //creates the array that will be returned by method
      String[] moveResult = new String[2];
      //adds the move by the computer to the array
      moveResult[0] = move.toString();
      //accesses the cell that was just hit
      CellStatus currentCell = getLayout().get(move.row() - 1).get(move.col() - 1);
      //checks if the ship hit was sunk, if so adds statement to moves array to be returned
      ArrayList<Move> successfulMovesRemove = new ArrayList<>();
      if (sunk)
      {
         //should print a different ship based on which was sunk
         switch (shipType)
         {
            case ST_AIRCRAFT_CARRIER -> moveResult[1] = "The computer sunk your Aircraft Carrier!";
            case ST_BATTLESHIP -> moveResult[1] = "The computer sunk your Battleship!";
            case ST_CRUISER -> moveResult[1] = "The computer sunk your Cruiser!";
            case ST_DESTROYER -> moveResult[1] = "The computer sunk your Destroyer!";
            case ST_SUB -> moveResult[1] = "The computer sunk your Sub!";
            default -> moveResult[1] = "None of the above.";
         }
         //removes all moves in successfulMoves that correspond with this ship 
         for (int i = 0; i < successfulMoves.size(); i++)
         {
            //current successful move during iteration
            Move successfulMove = successfulMoves.get(i);
            //cellstatus of current move
            CellStatus currentCellStatus = getLayout().get(move.row()-1).get(move.col()-1);
            //cellstatus of current successful move
            CellStatus currentSuccessfulCellStatus = getLayout().get(successfulMove.row()-1).get(successfulMove.col()-1);
            //check if changing the conditional in the loop is allowed here
            if (currentCellStatus == currentSuccessfulCellStatus)
            {
               successfulMoves.remove(i);
               //i had to edit the counter within the for loop in order to account for the changing
               //size of the arraylist as i removed elements, otherwise certain elements would get skipped
               i--;
            }
         }
      }
      return moveResult;
   }
   
   /*
   toString returns the user's board as a formatted String printed in a user-friendly way
   @return returns a formatted String
   */
   @Override
   public String toString()
   {
      String[] rows = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
      int rowIndex = 0;
      String print = "";
      for (ArrayList<CellStatus> row:getLayout())
      {
         print += rows[rowIndex] + "  ";
         for (CellStatus cell:row)
         {
            print += String.format("%s  ", cell.toString().charAt(1));
         }
         print += ("\n");
         rowIndex++;
      }
      return print;
   }
}