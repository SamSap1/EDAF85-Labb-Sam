import lift.LiftView;

public class LiftMonitor {
    private boolean doorsOpen;
    private int currFloor;
    private int direction;
    private int [] priorityEntry;
    private int [] priorityExit;
    private int toEnter;
    public boolean liftMoving;
    private int maxPassengers;
    private LiftView lv;
    private int pplInLift;

    public LiftMonitor(int floorCount, int maxPassengers, LiftView lv){
        toEnter = 0;
        currFloor = 0;
        pplInLift = 0;
        direction = 1;
        doorsOpen = false;
        liftMoving = true;
        priorityEntry = new int[floorCount];
        priorityExit = new int[floorCount];
        this.maxPassengers = maxPassengers;
        this.lv = lv;
    }

    public synchronized void hasEntered()
    {
        pplInLift++;
        priorityEntry[currFloor]--;
        toEnter--;
        notifyAll();
    }

    public synchronized void hasExited()
    {
        pplInLift--;
        priorityExit[currFloor]--;
        notifyAll();
    }

    public synchronized void setPriority(int currFloor) throws InterruptedException{

        priorityEntry[currFloor]++;
    }

    public synchronized void enterLift(int personFloor, int destinationFloor) throws InterruptedException
    {
        while (pplInLift + toEnter == maxPassengers || personFloor != currFloor || !doorsOpen)
        {
            wait();
        }

        toEnter++;
        priorityExit[destinationFloor]++;
        
        notifyAll();
    }

    public synchronized void exitLift(int personFloor) throws InterruptedException
    {
        while (pplInLift == 0 || personFloor != currFloor || !doorsOpen)
        {
            wait();
        }

        notifyAll();
    }

    public synchronized boolean passCanMove(int personFloor) throws InterruptedException{

        return (!liftMoving && doorsOpen && personFloor == currFloor);
    }

    public synchronized int getCurrentFloor () throws InterruptedException{

        return currFloor;
    }

    public synchronized int moveLift () throws InterruptedException
    {
        
        if (priorityExit[currFloor] > 0 || (priorityEntry[currFloor] > 0 && pplInLift < maxPassengers))
        {
            lv.openDoors(currFloor);
            doorsOpen = true;
            notifyAll();

            while (priorityExit[currFloor] != 0)
            {
                wait();
            }

            notifyAll();
            while (priorityEntry[currFloor] != 0 && pplInLift < maxPassengers)
            {
                liftMoving = false;
                wait();
            }

            doorsOpen = false;
            lv.closeDoors();
            
            liftMoving = true;
            
            lv.showDebugInfo(priorityEntry, priorityExit);
        }
        
        currFloor += direction;
        if (currFloor == 0 || currFloor == 6)
        {
            direction *= -1; 
        }
        return currFloor;
    }
}