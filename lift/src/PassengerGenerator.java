import lift.Passenger;

public class PassengerGenerator implements Passenger
{
    private int startFloor;
    private int destFloor;
    private int floorCount;

    public PassengerGenerator(int floorCount)
    {

    }

    /** @return the floor the passenger starts at */
    public int getStartFloor()
    {
        return startFloor;
    }

    /** @return the floor the passenger is going to */
    public int getDestinationFloor()
    {
        return destFloor;
    }

    /** First, delay for 0..45 seconds. Then animate the passenger's walk, on the entry floor, to the lift. */
    public void begin()
    {

    }

    /** Animate the passenger's walk from the entry floor into the lift. */
    public void enterLift()
    {

    }

    /** Animate the passenger's walk out of the lift, to the exit floor. */
    public void exitLift()
    {

    }

    /** Animate the passenger's walk, on the exit floor, out of view. */
    public void end()
    {

    }
}