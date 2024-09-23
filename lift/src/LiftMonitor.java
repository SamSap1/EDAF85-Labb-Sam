import lift.LiftView;

public class LiftMonitor {
    private boolean isLiftFull;
    private int currFloor;
    private int direction;
    private int [] priorityEntry;
    private int [] priorityExit;
    private boolean liftMoving;
    private boolean entering;
    private boolean exiting;
    private int maxPassengers;
    private LiftView lv;
    private int pplInLift;


    public LiftMonitor(int floorCount, int maxPassengers){
        currFloor = 0;
        pplInLift = 0;
        direction = 1;
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
        while (pplInLift == 0 || personFloor != currFloor)
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
    
        while (pplInLift > 0 || passengersWaiting()){

            wait();

        }

        liftMoving = true;

        currFloor += direction;
        
        if (currFloor == 0 || currFloor == 6){
            direction *= -1; 

        }

        lv.moveLift(currFloor - direction, currFloor);
        openDoors(currFloor);
        //ksk vänta?
        closeDoors(currFloor);
        liftMoving = false;

        notifyAll();
        

return currFloor;

    }

    public synchronized boolean passengersWaiting() throws InterruptedException{

       if (priorityEntry[currFloor] > 0 || priorityExit[currFloor] > 0){
            return true;


       }

       return false;



    }

    public synchronized void openDoors(int floor){
        lv.openDoors(floor);


    }
  public synchronized void closeDoors(int floor){

    lv.closeDoors();
        
    }


    public synchronized void requestEntry (int floor){

    }
     
        public synchronized void requestExit (int floor){
        
    }



}
