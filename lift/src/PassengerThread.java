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
                pass = view.createPassenger();
                pass.begin();
                int currentFloor = pass.getStartFloor();
                int destFloor = pass.getDestinationFloor();
                
                mon.setPriority(currentFloor);

                // while (mon.getCurrentFloor() != currentFloor){

                //     System.out.println("Passenger " + pass.getStartFloor() + " entering lift at floor " + currentFloor);
                //     System.out.println("Passenger " + pass.getDestinationFloor() + " exiting lift at floor " + destFloor);
                // }


                mon.enterLift(currentFloor, destFloor);
                pass.enterLift();
                mon.hasEntered();

                mon.exitLift(destFloor);
                pass.exitLift();
                mon.hasExited();

                pass.end();
            }

        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}