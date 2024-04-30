/*
Darby Lane
CS2100 Section A

This is the Board class, which is an abstract class holding relevent methods for editing and updating the boards.
It creates the board layout from the two files, and updates the board with each move.
*/
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
public abstract class Board
{
   private ArrayList<ArrayList<CellStatus>> layout = new ArrayList<>();
   private Fleet fleet;
   public final int SIZE = 10;
   private ArrayList<Character> rows = new ArrayList<>();
   
   /*
   This is the constructor for the Board class. It first creates an empty two-dimensional ArrayList of CellStatus.NOTHING objects,
   Then adds the ship layout based on the information in the file passed into the constructor.
   @param it takes in a String for the name of the file to base the ship layout on
   */
   public Board(String s)
   {
      Scanner file = null;
      try
      {
         file = new Scanner(new File(s));
      }
      catch (IOException e)
      {
         System.out.println("File not found. Exiting program.");
         System.exit(1);
      }
      //create layout
      for (int r = 1;r<=SIZE;r++)
      {
         ArrayList<CellStatus> row = new ArrayList<>();
         layout.add(row);
         for (int c = 1;c<=SIZE;c++)
         {
            row.add(CellStatus.NOTHING);
         }
      }
      //iterate back through layout and put ships on board
      rows.add('A');
      rows.add('B');
      rows.add('C');
      rows.add('D');
      rows.add('E');
      rows.add('F');
      rows.add('G');
      rows.add('H');
      rows.add('I');
      rows.add('J');
      
      int endCol;
      while (file.hasNext())
      {
         String line = file.nextLine();
         char typeOfShip = line.charAt(0);
         char startRow = line.charAt(2);
         char endRow = line.charAt(5);
         int startCol = Integer.parseInt(Character.toString(line.charAt(3)));
         if (line.strip().length() == 7)
            endCol = Integer.parseInt(Character.toString(line.charAt(6)));
         else
            endCol = Integer.parseInt(line.substring(6));
         
         int currentRow = 0;
         for (ArrayList<CellStatus> row:layout)
         {
            //get the index of 
            int startRowNumber = rows.indexOf(startRow) + 1;
            currentRow++;
            int endRowNumber = rows.indexOf(endRow) + 1;
            //This checks if the current row being looked at is a row that the ship from the line in the file is in
            if (startRowNumber <= currentRow && endRowNumber >= currentRow)
            {
               int currentCell = 0;
               for (CellStatus cell:row)
               {
                  currentCell++;
                  //This checks if the current cell is in a column that the ship from the line in the file is in
                  if (currentCell >= startCol && currentCell <= endCol)
                  {
                     switch (typeOfShip)
                     {
                        case 'A' -> layout.get(currentRow-1).set(currentCell-1, CellStatus.AIRCRAFT_CARRIER);
                        case 'B' -> layout.get(currentRow-1).set(currentCell-1, CellStatus.BATTLESHIP);
                        case 'C' -> layout.get(currentRow-1).set(currentCell-1, CellStatus.CRUISER);
                        case 'D' -> layout.get(currentRow-1).set(currentCell-1, CellStatus.DESTROYER);
                        case 'S' -> layout.get(currentRow-1).set(currentCell-1, CellStatus.SUB);
                        default -> System.out.println("None of the above");
                     }
                  }
               }
            }
         }
         //initialize fleet
         fleet = new Fleet();
      }
   }
   
   /*
   applyMoveToLayout applies the move passed in as the param to the layout, updating the CellStatus if the move hit a ship.
   It also returns the original CellStatus of the cell that was hit.
   @param takes in a Move object to apply to the layout
   @return returns the status of the cell before the move was applied
   */
   public CellStatus applyMoveToLayout(Move m)
   {
      CellStatus originalCell = CellStatus.NOTHING;
      int currentRow = 0;
      for (ArrayList<CellStatus> row:layout)
      {
         currentRow++;
         int currentCell = 0;
         if (m.row() == currentRow)
         {
            for (CellStatus cell:row)
            {
               currentCell++;
               if (m.col() == currentCell)
               {
                  originalCell = layout.get(currentRow - 1).get(currentCell - 1);
                  if (!cell.toString().equals("oo"))
                  {
                     switch (cell)
                     {
                        case AIRCRAFT_CARRIER -> layout.get(currentRow-1).set(currentCell-1, CellStatus.AIRCRAFT_CARRIER_HIT);
                        case BATTLESHIP -> layout.get(currentRow-1).set(currentCell-1, CellStatus.BATTLESHIP_HIT);
                        case CRUISER -> layout.get(currentRow-1).set(currentCell-1, CellStatus.CRUISER_HIT);
                        case DESTROYER -> layout.get(currentRow-1).set(currentCell-1, CellStatus.DESTROYER_HIT);
                        case SUB -> layout.get(currentRow-1).set(currentCell-1, CellStatus.SUB_HIT);
                        default -> System.out.println("This cell has already been hit, move ignored");
                     }
                  }
                  else
                  {
                     layout.get(currentRow - 1).set(currentCell - 1, CellStatus.NOTHING_HIT);
                  }
               }
            }
         }
      }
      return originalCell;
   }
   
   /*
   moveAvailable returns true if the move taken in as an arg will not hit a cell that has already been hit or sunk,
   false otherwise
   @param takes in a Move object to check cellStatus of
   @return returns true if the move is available, false otherwise
   */
   public boolean moveAvailable(Move m)
   {
      boolean moveAvailable = false;
      int currentRowIndex = 0;
      for (ArrayList<CellStatus> row:layout)
      {
         currentRowIndex++;
         int currentCellIndex = 0;
         if (m.row() == currentRowIndex)
         {
            for (CellStatus cell:row)
            {
               currentCellIndex++;
               if (m.col() == currentCellIndex)
               {
                  String cellValue = cell.toString();
                  if (cellValue.charAt(0) == 'o') //use double equals
                  {
                     moveAvailable = true;
                     System.out.println("found it");
                     return moveAvailable;
                  }   
                  else
                  {
                     moveAvailable = false;
                  }          
               }
            }
         }
      }
      return moveAvailable;
   }
   
   /*
   getLayout returns the layout of the board
   @return returns a two dimensional ArrayList of CellStatus objects that represents the layout
   */
   public ArrayList<ArrayList<CellStatus>> getLayout()
   {
      return layout;
   }
   
   /*
   getFleet returns the Fleet object for the class
   @return returns a Fleet Object
   */
   public Fleet getFleet()
   {
      return fleet;
   }
   
   /*
   gameOver calls the gameOver method from the Fleet class, returns true if all ships sunk
   and false otherwise
   @return returns a boolean if all ships sunk, false otherwise
   */
   public boolean gameOver()
   {
      return fleet.gameOver();
   }
}