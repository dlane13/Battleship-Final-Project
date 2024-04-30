/*
Darby Lane
CS2100 Section A

This is the ComputerBoard class, which is a subclass of the Board abstract class. It makes the user's moves on the computer's board.
*/
import java.util.ArrayList;
public class ComputerBoard extends Board
{
   private ArrayList<ArrayList<String>> hitCells = new ArrayList<>();
   
   /*
   This is the constructor for the ComputerBoard class. It calls the superclass constructor.
   @param takes in a string for the file name to pass into the superclass constructor
   */
   public ComputerBoard(String s)
   {
      super(s);
   }
   
   /*
   makePlayerMove takes in a Move object and applies it to the computer's board. If it hits a ship, it will perform
   different actions based on the type of the ship.
   @param takes in a Move object representing the move made by the user
   @return returns a String that may print a statement about a ship being hit if a ship was hit
   */
   public String makePlayerMove(Move m)
   {
      String print = "";
      boolean sunk = false;
      //takes the players move and makes it against the computer's board
      CellStatus originalCell = applyMoveToLayout(m);
      //get status of original cell, if it contains a ship, update fleet, if ship has sunk print statement else do nothing.
      //checks if original cell contains a ship, ie if second character is not "o"
      String string = originalCell.toString().substring(1);
      if (!originalCell.toString().substring(1).equals("o"))
      {
         ArrayList<String> hitCell = new ArrayList<>();
         String hitShipRow = String.valueOf(m.row());
         String hitShipCol = String.valueOf(m.col());
         String cellType = originalCell.toString();
         hitCell.add(cellType);
         hitCell.add(hitShipRow);
         hitCell.add(hitShipCol);
         hitCells.add(hitCell);
         int rowIndex = m.row() - 1;
         int colIndex = m.col() - 1;
         
         switch (originalCell)
         {
            case AIRCRAFT_CARRIER -> 
            {
               sunk = getFleet().updateFleet(ShipType.ST_AIRCRAFT_CARRIER);
               if (sunk)
               {
                  for (ArrayList<String> oneHitCell:hitCells)
                  {
                     if (oneHitCell.get(0).equals("oA"))
                        {
                           int oneHitCellRowIndex = Integer.parseInt(oneHitCell.get(1))-1;
                           int oneHitCellColIndex = Integer.parseInt(oneHitCell.get(2))-1;
                           //sets the cells in Layout from hit to sunk
                           getLayout().get(oneHitCellRowIndex).set(oneHitCellColIndex, CellStatus.AIRCRAFT_CARRIER_SUNK);
                        }
                  }
                  print = "You sunk my Aircraft Carrier!";
               }
            }
            case BATTLESHIP ->
            {
               sunk = getFleet().updateFleet(ShipType.ST_BATTLESHIP);
               if (sunk)
               {
                  for (ArrayList<String> oneHitCell:hitCells)
                  {
                     if (oneHitCell.get(0).equals("oB"))
                        {
                           int oneHitCellRowIndex = Integer.parseInt(oneHitCell.get(1))-1;
                           int oneHitCellColIndex = Integer.parseInt(oneHitCell.get(2))-1;
                           //sets the cells in Layout from hit to sunk
                           getLayout().get(oneHitCellRowIndex).set(oneHitCellColIndex, CellStatus.BATTLESHIP_SUNK);
                        }
                  }
                  print = "You sank my Battleship!";
               }
            }
            case CRUISER ->
            {
               sunk = getFleet().updateFleet(ShipType.ST_CRUISER);
               if (sunk)
               {
                  for (ArrayList<String> oneHitCell:hitCells)
                  {
                     if (oneHitCell.get(0).equals("oC"))
                        {
                           int oneHitCellRowIndex = Integer.parseInt(oneHitCell.get(1))-1;
                           int oneHitCellColIndex = Integer.parseInt(oneHitCell.get(2))-1;
                           //sets the cells in Layout from hit to sunk
                           getLayout().get(oneHitCellRowIndex).set(oneHitCellColIndex, CellStatus.CRUISER_SUNK);
                        }
                  }
                  print = "You sank my Cruiser!";
               }
            }
            case DESTROYER ->
            {
               sunk = getFleet().updateFleet(ShipType.ST_DESTROYER);
               if (sunk)
               {
                  for (ArrayList<String> oneHitCell:hitCells)
                  {
                     if (oneHitCell.get(0).equals("oD"))
                        {
                           int oneHitCellRowIndex = Integer.parseInt(oneHitCell.get(1))-1;
                           int oneHitCellColIndex = Integer.parseInt(oneHitCell.get(2))-1;
                           //sets the cells in Layout from hit to sunk
                           getLayout().get(oneHitCellRowIndex).set(oneHitCellColIndex, CellStatus.DESTROYER_SUNK);
                        }
                  }
                  print = "You sank my Destroyer!";
               }
            }
            case SUB ->
            {
               sunk = getFleet().updateFleet(ShipType.ST_SUB);
               if (sunk)
               {
                   for (ArrayList<String> oneHitCell:hitCells)
                  {
                     if (oneHitCell.get(0).equals("oS"))
                        {
                           int oneHitCellRowIndex = Integer.parseInt(oneHitCell.get(1))-1;
                           int oneHitCellColIndex = Integer.parseInt(oneHitCell.get(2))-1;
                           //sets the cells in Layout from hit to sunk
                           getLayout().get(oneHitCellRowIndex).set(oneHitCellColIndex, CellStatus.SUB_SUNK);
                        }
                  }
                  print = "You sank my Sub!";
               }
            }
            default -> System.out.println("");
         }
      }
      return print;
   }
   
   /*
   toString prints the computer's board as a formatted String in a user-friendly way
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
            print += String.format("%s  ", cell.toString().charAt(0));
         }
         print += ("\n");
         rowIndex++;
      }
      return print;
   }
}