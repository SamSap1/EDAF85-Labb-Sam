import lift.LiftView;

public class LiftMonitor {
    private boolean isLiftFull;
    private boolean doorsOpen;
   // private boolean doorsClosed;
    private int currFloor;
    private int direction;
    private int [] priorityEntry;
    private int [] priorityExit;
    public boolean liftMoving;
    private boolean entering;
    private boolean exiting;
    private int maxPassengers;
    private LiftView lv;
    private int pplInLift;


    public LiftMonitor(int floorCount, int maxPassengers){
        currFloor = 0;
        pplInLift = 0;
        direction = 1;
        doorsOpen = false;
        //doorsClosed = true;
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
        while (pplInLift == maxPassengers || personFloor != currFloor)
        {
            wait();
        }

        lv.openDoors(personFloor);
        pplInLift++;
        priorityEntry[personFloor]--;
        entering = true;

        if (pplInLift == maxPassengers)
        {
            isLiftFull = true;
        }
        lv.closeDoors();
        notifyAll();
    }

    public synchronized void exitLift(int personFloor) throws InterruptedException
    {
        while (pplInLift == 0 || personFloor != currFloor)
        {
            wait();
        }

        lv.openDoors(currFloor);
        pplInLift--;
        priorityExit[personFloor]--;

        if (pplInLift < maxPassengers)
        {
            isLiftFull = false;
        }
        exiting = true;
        lv.closeDoors();
        notifyAll();
    }

    public synchronized boolean passCanMove() throws InterruptedException{

        if (!liftMoving){
            return true;

        }

        return false;

    }



    public synchronized int moveLift () throws InterruptedException{
    
        while (entering || exiting || priorityEntry[currFloor] > 0 || priorityExit[currFloor] > 0){

            liftMoving = false;
            wait();

        }

        liftMoving = true;

        currFloor += direction;
        
        if (currFloor == 0 || currFloor == 6){
            direction *= -1; 

        }

        notifyAll();

        return currFloor;
 }       




    //public synchronized void requestEntry (int floor){

    //}
     
      //  public synchronized void requestExit (int floor){
        
    //}

    public synchronized boolean canMove(){
            if (doorsOpen){
                return false;
            }
             return true;

    }


}
