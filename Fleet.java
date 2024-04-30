/*
Darby Lane
CS2100 Section A

This is the Fleet class, which holds instances of all ships in the fleet.
It keeps track of hits on ships and whether they have sunk, and also 
contains a method which checks if all ships have sunk.
*/
public class Fleet
{
   private Ship battleship;
   private Ship aircraftCarrier;
   private Ship cruiser;
   private Ship sub;
   private Ship destroyer;
   
   /*
   This is the constructor for the Fleet class, it initializes all five ships.
   */
   public Fleet()
   {
      battleship = new Battleship();
      aircraftCarrier = new AircraftCarrier();
      cruiser = new Cruiser();
      sub = new Sub();
      destroyer = new Destroyer();
   }
   
   /*
   updateFleet informs the correct ship that it has been hit and returns true if this sank the ship, false otherwise
   @param takes in a ShipType object
   @return returns true if the hit sunk the ship, false if not
   */
   public boolean updateFleet(ShipType ship)
   {
      //create ship object, check for ship type and then assign ship object to correct ship, then do ship.hit() after switch
      boolean shipSunk = false;
      switch (ship)
      {
         case ST_AIRCRAFT_CARRIER -> 
         {
            aircraftCarrier.hit();
            shipSunk = aircraftCarrier.getSunk();
         }
         case ST_BATTLESHIP ->
         {
            battleship.hit();
            shipSunk = battleship.getSunk();
         }
         case ST_CRUISER ->
         {
            cruiser.hit();
            shipSunk = cruiser.getSunk();
         }
         case ST_SUB ->
         {
            sub.hit();
            shipSunk = sub.getSunk();
         }
         case ST_DESTROYER ->
         {
            destroyer.hit();
            shipSunk = destroyer.getSunk();
         }
      }
      return shipSunk;
   }
   
   /*
   gameOver returns true if every ship in the fleet has sunk, false otherwise
   @return returns true if every ship sank, false otherwise
   */
   public boolean gameOver()
   {
      return (battleship.getSunk() 
              && aircraftCarrier.getSunk()
              && cruiser.getSunk()
              && sub.getSunk()
              && destroyer.getSunk());
   }
}