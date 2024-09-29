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
        this.pass = view.createPassenger();

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
                
                mon.setPriority(currentFloor);

                while (mon.getCurrentFloor() != currentFloor){

                    System.out.println("Passenger " + pass.getStartFloor() + " entering lift at floor " + currentFloor);
                    System.out.println("Passenger " + pass.getDestinationFloor() + " exiting lift at floor " + destFloor);
                }

                boolean hasEntered = false;
                while (!hasEntered)
                {
                    mon.enterLift(currentFloor, destFloor);
                    if (mon.passCanMove(destFloor))
                    {
                        pass.enterLift();
                        hasEntered = true;
                    }
                }

                boolean hasExited = false;
                while (!hasExited)
                {
                    mon.exitLift(destFloor);
                    if (mon.passCanMove(destFloor))
                    {
                        pass.exitLift();
                        hasExited = true;
                    }
                }
                pass.end();

              
                
            }

        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

}