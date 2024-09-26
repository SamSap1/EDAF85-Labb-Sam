import java.util.Random;
import lift.LiftView;
import lift.Passenger;
import lift.view.passenger.PassengerView;

public class PassengerGenerator implements Passenger
{
    private int startFloor;
    private int destFloor;
    private int floorCount;
    private LiftView lView;
    private Passenger pass;
    private Random rng;

    public PassengerGenerator(LiftView lView, int floorCount)
    {
        this.lView = lView;
        this.rng = new Random();
        this.pass = lView.createPassenger();

        this.floorCount = floorCount;
        this.startFloor = rng.nextInt(floorCount);
        do
        {
            this.destFloor = rng.nextInt(floorCount);
        } while (destFloor == startFloor);
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
        try
        {
            Thread.sleep(rng.nextInt(45000));
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    /** Animate the passenger's walk from the entry floor into the lift. */
    public void enterLift()
    {
        //pView.enter bro hur fan ska vi göra dessa AAAAAAHHH
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