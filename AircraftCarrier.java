/*
Darby Lane
CS2100 Section A

This is the AircraftCarrier class, which is a subclass of the Ship class.
This class has a constructor which creates an AircraftCarrier object using the superclass constructor.
*/
public class AircraftCarrier extends Ship
{
   /*
   This is the constructor for this class, which calls the Ship superclass constructor and passes in an int, 5,
   for the size of the ship.
   */
   public AircraftCarrier()
   {
      super(5);
   }
}