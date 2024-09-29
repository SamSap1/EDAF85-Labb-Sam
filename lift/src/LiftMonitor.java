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

    public LiftMonitor(int floorCount, int maxPassengers, LiftView lv){
        currFloor = 0;
        pplInLift = 0;
        direction = 1;
        doorsOpen = false;
        isLiftFull = false;
        liftMoving = true;
        exiting = false;
        priorityEntry = new int[floorCount];
        priorityExit = new int[floorCount];
        this.maxPassengers = maxPassengers;
        //lv = new LiftView(floorCount, maxPassengers);

        this.lv = lv;
    }

    public synchronized void setPriority(int start) throws InterruptedException{
        priorityEntry[start]++;
       // priorityExit[dest]++;

    }


    public synchronized void enterLift(int personFloor, int destinationFloor) throws InterruptedException
    {
        while (pplInLift == maxPassengers || personFloor != currFloor)
        {
            System.out.println("TEST TEST TEST");

            wait();
        }


        entering = true;
        if (!doorsOpen){
        lv.openDoors(currFloor);
        doorsOpen = true;
        }

        pplInLift++;
        priorityEntry[personFloor]--;
        priorityExit[destinationFloor]++;


        if (pplInLift == maxPassengers)
        {
            isLiftFull = true;
        }
        
        if (priorityEntry[personFloor] == 0 || pplInLift == maxPassengers){
            lv.closeDoors();
            
            doorsOpen = false;
            entering = false;
        }
        
        
        notifyAll(); // notifierar för tidigt
    }

    public synchronized void exitLift(int personFloor) throws InterruptedException
    {
        while (pplInLift == 0 || personFloor != currFloor)
        {
            wait();
        }

        
        exiting = true;
        if (!doorsOpen){
            lv.openDoors(currFloor);
            doorsOpen = true;
        }
        
        pplInLift--;
        priorityExit[personFloor]--;
    


        if (pplInLift < maxPassengers)
        {
            isLiftFull = false;
        }

        if (priorityExit[personFloor] == 0){
            lv.closeDoors();
            doorsOpen = false;
        }
       
        exiting = false;
        notifyAll();
    }

    public synchronized boolean passCanMove(int personFloor) throws InterruptedException{

        return (!liftMoving && doorsOpen && currFloor == personFloor);
    }

    public synchronized int getCurrentFloor () throws InterruptedException{

        return currFloor;


    }


    public synchronized int moveLift () throws InterruptedException{
    
        while (entering || exiting || (priorityEntry[currFloor] > 0 && pplInLift < maxPassengers) || priorityExit[currFloor] > 0 ){

            liftMoving = false;
            
            wait();

        }

        liftMoving = true;

        currFloor += direction;
        
        if (currFloor == 0 || currFloor == 6){
            direction *= -1; 

        }
        System.out.println("Current Floor: " + currFloor + ", Passengers in Lift: " + pplInLift + 
        ", Passengers Waiting to Enter: " + priorityEntry[currFloor] + 
    ", Passengers Waiting to Exit: " + priorityExit[currFloor]);


        notifyAll();

        return currFloor;
 }       




}
