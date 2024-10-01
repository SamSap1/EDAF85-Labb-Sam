import lift.LiftView;

public class LiftThread extends Thread{
    private LiftMonitor monitor;
    private LiftView lv;
    private int currentFloor;
    private int nextFloor;

    public LiftThread(LiftView view, LiftMonitor monitor) throws InterruptedException{
        this.lv = view;
        this.monitor = monitor;
        currentFloor = monitor.getCurrentFloor();
    }


    @Override 
    public void run(){
        try {
                while (true){
                
                    nextFloor = monitor.moveLift();
                    lv.moveLift(currentFloor, nextFloor);
                    currentFloor = nextFloor;
                }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}