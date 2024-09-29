import lift.LiftView;
import lift.Passenger;

public class PassengerThread extends Thread
{
    private Passenger pass;
    private LiftMonitor mon;
    private LiftView view;

    public PassengerThread(LiftMonitor monitor, LiftView view)
    {
        this.mon = monitor;
        this.view = view;
    }

    @Override
    public void run()
    {
        try 
        {
            while(true)
            {
                this.pass = view.createPassenger();

                pass.begin();
                int currentFloor = pass.getStartFloor();
                int destFloor = pass.getDestinationFloor();


                if (mon.passCanMove() && mon.getCurrentFloor() == currentFloor){

                    mon.enterLift(currentFloor, destFloor);
                    pass.enterLift();
                }

                if (mon.passCanMove() && mon.getCurrentFloor() == destFloor){
                    mon.exitLift(destFloor);
                    pass.exitLift();
                    
                }
                pass.end();
                
            }

        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

}