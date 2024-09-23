import lift.LiftView;

public class LiftMonitor {
    private boolean isLiftFull;
    private int liftFloor;
    private int destFloor;
    private int [] priorityEntry;
    private int [] priorityExit;
    private boolean liftMoving;
    private boolean entering;
    private boolean exiting;
    private int maxPassengers;
    private LiftView lv;
    private int pplInLift;


    public LiftMonitor(int floorCount, int maxPassengers){
        liftFloor = 0;
        pplInLift = 0;
        isLiftFull = false;
        liftMoving = true;
        exiting = false;
        priorityEntry = new int[floorCount];
        priorityExit = new int[floorCount];
        this.maxPassengers = maxPassengers;
        lv = new LiftView(floorCount, maxPassengers);
    }

    public synchronized void enterLift(int personFloor) throws InterruptedException
    {
        while (pplInLift == maxPassengers || personFloor != liftFloor)
        {
            wait();
        }
        pplInLift++;
        priorityEntry[personFloor]--;
        entering = true;

        if (pplInLift == maxPassengers)
        {
            isLiftFull = true;
        }
        notifyAll();
    }

    public synchronized void exitLift(int personFloor) throws InterruptedException
    {
        while (pplInLift == 0 || personFloor != liftFloor)
        {
            wait();
        }
        pplInLift--;
        priorityExit[personFloor]--;

        if (pplInLift < maxPassengers)
        {
            isLiftFull = false;
        }
        exiting = true;
        notifyAll();
    }


    public synchronized int moveLift () throws InterruptedException{
    
return 0;

    }

    public synchronized boolean passengersWaiting() throws InterruptedException{

       if (priorityEntry[liftFloor] > 0 || priorityExit[liftFloor] > 0){
            return true;


       }

       return false;



    }

    public synchronized void openDoors(int floor){



    }
  public synchronized void closeDoors(int floor){


        
    }


    public synchronized void requestEntry (int floor){

    }
     
        public synchronized void requestExit (int floor){
        
    }



}
