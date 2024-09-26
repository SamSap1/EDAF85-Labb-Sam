import lift.LiftView;
import lift.Passenger;

public class PassengerThread extends Thread
{
    private PassengerGenerator pass;
    private LiftMonitor mon;
    private LiftView lView;

    public PassengerThread(LiftMonitor monitor, int floorCount)
    {
        this.mon = monitor;
        this.pass = new PassengerGenerator(lView, floorCount);
    }

    @Override
    public void run()
    {
        try 
        {
            while(true)
            {
                pass.begin();
                int currentFloor = pass.getStartFloor();
                int destFloor = pass.getDestinationFloor();

                mon.enterLift(currentFloor);
                pass.enterLift();

                // vänta på att liften har rört på sig

                mon.exitLift(destFloor);
                pass.exitLift();
                pass.end();
            }

        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

}