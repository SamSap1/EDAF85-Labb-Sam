import lift.Passenger;

public class PassengerThread extends Thread
{
    private PassengerGenerator p;
    private LiftMonitor monitor;

    public PassengerThread(LiftMonitor monitor, int floorCount)
    {
        this.monitor = monitor;
        this.p = new PassengerGenerator(floorCount);
    }

    @Override
    public void run()
    {
        try 
        {
            while(true)
            {
                p.begin();
                int currentFloor = p.getStartFloor();
                int nextFloor = p.getDestinationFloor();

                monitor.enterLift(currentFloor);
                p.enterLift();
            }

        } catch (Exception e) 
        {
            
        }
    }

}